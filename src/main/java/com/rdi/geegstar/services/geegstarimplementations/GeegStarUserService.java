package com.rdi.geegstar.services.geegstarimplementations;

import com.rdi.geegstar.data.models.*;
import com.rdi.geegstar.data.repositories.TalentRepository;
import com.rdi.geegstar.data.repositories.UserRepository;
import com.rdi.geegstar.dto.requests.*;
import com.rdi.geegstar.dto.response.*;
import com.rdi.geegstar.enums.Role;
import com.rdi.geegstar.exceptions.*;
import com.rdi.geegstar.services.MailService;
import com.rdi.geegstar.services.TokenService;
import com.rdi.geegstar.services.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

import static com.rdi.geegstar.enums.Role.PLANNER;
import static com.rdi.geegstar.enums.Role.TALENT;

@Service
@AllArgsConstructor
@Slf4j
public class GeegStarUserService implements UserService {

    private final ModelMapper modelMapper;
    private final TalentRepository talentRepository;
    private final UserRepository userRepository;
    private final TokenService tokenService;
    private final MailService mailService;


    @Override
    public RegistrationResponse registerUser(PlannerRegistrationRequest plannerRegistrationRequest) {
        Planner planner = modelMapper.map(plannerRegistrationRequest, Planner.class);
        planner.setRole(PLANNER);
        Planner savedPlanner = userRepository.save(planner);
        return modelMapper.map(savedPlanner, RegistrationResponse.class);
    }

    @Override
    public RegistrationResponse registerUser(TalentRegistrationRequest talentRegistrationRequest) {
        Talent talent = modelMapper.map(talentRegistrationRequest, Talent.class);
        PortfolioRequest portfolioRequest = talentRegistrationRequest.getPortfolioRequest();
        Portfolio portfolio = null;
        if (null != talentRegistrationRequest.getPortfolioRequest()) {
            portfolio = modelMapper.map(portfolioRequest, Portfolio.class);
        }
        talent.setPortfolio(portfolio);
        talent.setRole(TALENT);
        Talent savedTalent = userRepository.save(talent);
        return modelMapper.map(savedTalent, RegistrationResponse.class);
    }

    @Override
    public String requestEmailConfirmationCode(String userEmail) throws InValidEmailException, EmailIsTakenException {
        boolean isEmailAvailable = isEmailAvailable(userEmail);
        Token generatedToken = tokenService.generateToken(userEmail);
        String tokenCode = generatedToken.getTokenCode();
        if(isEmailAvailable) emailConfirmationCodeTo(userEmail, tokenCode);
        return "Successful";
    }

    @Override
    public Boolean confirmEmail(String userEmail, String tokenCode) throws EmailConfirmationFailedException {
        return tokenService.confirmEmail(userEmail, tokenCode);
    }

    @Override
    public User findUserById(Long userId) throws UserNotFoundException {
        return userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("User was not found in our system"));
    }

    @Override
    public GetUserResponse getUserById(Long userId) throws UserNotFoundException {
        Optional<User> userOptional = userRepository.findById(userId);
        if(!userOptional.isPresent())
            throw new UserNotFoundException(String.format("The user with id %s is not found in our system", userId));
        Role role = userOptional.get().getRole();
        if (TALENT.equals(role)) {
            return getTalentResponse(userOptional);
        } else return getPlannerResponse(userOptional);
    }

    private GetUserResponse getPlannerResponse(Optional<User> userOptional) {
        Planner planner = modelMapper.map(userOptional, Planner.class);
        return modelMapper.map(planner, GetPlannerResponse.class);
    }

    private GetUserResponse getTalentResponse(Optional<User> userOptional) {
        Talent talent = modelMapper.map(userOptional, Talent.class);
        GetTalentResponse getTalentResponse = modelMapper.map(talent, GetTalentResponse.class);
        Portfolio portfolio = talent.getPortfolio();
        PortfolioResponse portfolioResponse = modelMapper.map(portfolio, PortfolioResponse.class);
        getTalentResponse.setPortfolioResponse(portfolioResponse);
        return getTalentResponse;
    }

    @Override
    public List<GetAllTalentsResponse> getAllTalents(GetAllTalentsRequest getAllTalentRequest) {
        int pageNumber = getAllTalentRequest.getPageNumber() - 1;
        int pageSize = getAllTalentRequest.getPageSize();
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<Talent> talentPage = talentRepository.findAll(pageable);
        List<Talent> talents = talentPage.getContent();
        return talents.stream()
                .map(talent -> modelMapper.map(talent, GetAllTalentsResponse.class))
                .toList();
    }

    private void emailConfirmationCodeTo(String userEmail, String tokenCode) {
        EmailRequest emailRequest = new EmailRequest();
        emailRequest.setSubject("Email confirmation on GeegStar service");
        emailRequest.setRecipients(List.of(new Recipient(userEmail, "Prospective client")));
        emailRequest.setHtmlContent(
                String.format(
                        "<p>Welcome to GeegStar services. \n Here is your confirmation code %s",
                        tokenCode
                ));
        mailService.sendMail(emailRequest);
    }

    private boolean isEmailAvailable(String userEmail) throws InValidEmailException, EmailIsTakenException {
        String regexPattern = "[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?";
        boolean isNotValidEmail = !Pattern.compile(regexPattern).matcher(userEmail).matches();
        if(isNotValidEmail) throw new InValidEmailException(String.format("The email %s is not valid", userEmail));
        boolean isEmailTaken = userRepository.findByEmail(userEmail).isPresent();
        if (isEmailTaken) throw new EmailIsTakenException(String.format("The email %s is already registered", userEmail));
        return true;
    }
}
