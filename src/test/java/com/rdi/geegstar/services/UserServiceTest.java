package com.rdi.geegstar.services;

import com.rdi.geegstar.data.models.Portfolio;
import com.rdi.geegstar.dto.requests.*;
import com.rdi.geegstar.dto.response.GetAllTalentsResponse;
import com.rdi.geegstar.dto.response.GetUserResponse;
import com.rdi.geegstar.dto.response.RegistrationResponse;
import com.rdi.geegstar.enums.Role;
import com.rdi.geegstar.enums.TalentCategory;
import com.rdi.geegstar.exceptions.EmailConfirmationFailedException;
import com.rdi.geegstar.exceptions.GeegStarException;
import com.rdi.geegstar.exceptions.UserNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class UserServiceTest {

    @Autowired
    private UserService userService;


    @Test
    public void testTalentRegistration() {
        // Given
        TalentRegistrationRequest talentRegistrationRequest = new TalentRegistrationRequest();
        talentRegistrationRequest.setFirstName("Retnaa");
        talentRegistrationRequest.setLastName("Dayok");
        talentRegistrationRequest.setEmail("dayokr@gmail.com");
        talentRegistrationRequest.setPassword("password");
        talentRegistrationRequest.setPhoneNumber("07031005737");
        talentRegistrationRequest.setTalentCategory(TalentCategory.ARTISTE);
        talentRegistrationRequest.setBio("A young vibrant talented afro musician, singer of the hit song Banger."
                + " An award winning star");
        talentRegistrationRequest.setDisplayName("Jay Benjis");
        PortfolioRequest portfolioRequest = new PortfolioRequest();
        portfolioRequest.setFirstLink("https://www.youtube.com/watch?v=1qw5ITr3k9E&t=780s");
        talentRegistrationRequest.setPortfolioRequest(portfolioRequest);

        // When
        RegistrationResponse registrationResponse = userService.registerUser(talentRegistrationRequest);

        // Assert
        assertThat(registrationResponse).isNotNull();
    }

    @Test
    public void testPlannerRegistration() {
        // Given
        PlannerRegistrationRequest plannerRegistrationRequest = new PlannerRegistrationRequest();
        plannerRegistrationRequest.setFirstName("Retnaa");
        plannerRegistrationRequest.setLastName("Dayok");
        plannerRegistrationRequest.setEmail("dayokr@gmail.com");
        plannerRegistrationRequest.setPassword("password");
        plannerRegistrationRequest.setPhoneNumber("07031005737");
        plannerRegistrationRequest.setEventPlanningCompanyName("StarEvents Inc");

        // When
        RegistrationResponse registrationResponse = userService.registerUser(plannerRegistrationRequest);
        assertThat(registrationResponse).isNotNull();
    }

    @Test
    public void testRequestConfirmationCode() throws GeegStarException {
        // Given
        String userEmail = "max_ret@yahoo.com";

        // When
        var response = userService.requestEmailConfirmationCode(userEmail);

        // Assert
        assertNotNull(response);
    }


    @Test
    @Sql("/db/insert.sql")
    public void testConfirmingEmail() throws EmailConfirmationFailedException {
        // Given
        String userEmail = "john499@qianhost.com";
        String code = "4532";

        // When
        var response = userService.confirmEmail(userEmail, code);

        // Assert
        assertTrue(response);
    }

    @Test
    public void testGetUserThatIsATalent() throws UserNotFoundException {
        // Given
        TalentRegistrationRequest talentRegistrationRequest = new TalentRegistrationRequest();
        talentRegistrationRequest.setFirstName("Retnaa");
        talentRegistrationRequest.setLastName("Dayok");
        talentRegistrationRequest.setEmail("dayokr@gmail.com");
        talentRegistrationRequest.setPassword("password");
        talentRegistrationRequest.setPhoneNumber("07031005737");
        talentRegistrationRequest.setTalentCategory(TalentCategory.ARTISTE);
        talentRegistrationRequest.setBio("A young vibrant talented afro musician, singer of the hit song Banger."
                + " An award winning star");
        talentRegistrationRequest.setDisplayName("Jay Benjis");
        PortfolioRequest portfolioRequest = new PortfolioRequest();
        portfolioRequest.setFirstLink("https://www.youtube.com/watch?v=1qw5ITr3k9E&t=780s");
        talentRegistrationRequest.setPortfolioRequest(portfolioRequest);
        RegistrationResponse registrationResponse = userService.registerUser(talentRegistrationRequest);

        // When
        GetUserResponse getUserResponse = userService.getUserById(registrationResponse.getId());

        // Assert
        assertThat(getUserResponse).isNotNull();
    }

    @Test
    public void testGetUserThatIsAPlanner() throws UserNotFoundException {
        // Given
        PlannerRegistrationRequest plannerRegistrationRequest = new PlannerRegistrationRequest();
        plannerRegistrationRequest.setFirstName("Retnaa");
        plannerRegistrationRequest.setLastName("Dayok");
        plannerRegistrationRequest.setEmail("dayokr@gmail.com");
        plannerRegistrationRequest.setPassword("password");
        plannerRegistrationRequest.setPhoneNumber("07031005737");
        plannerRegistrationRequest.setEventPlanningCompanyName("StarEvents Inc");
        RegistrationResponse registrationResponse = userService.registerUser(plannerRegistrationRequest);

        // When
        GetUserResponse getUserResponse = userService.getUserById(registrationResponse.getId());

        // Assert
        assertThat(getUserResponse).isNotNull();
    }


    @Test
    public void testGetAllTalents() {
        // Given
        TalentRegistrationRequest talentRegistrationRequest = new TalentRegistrationRequest();
        talentRegistrationRequest.setFirstName("Retnaa");
        talentRegistrationRequest.setLastName("Dayok");
        talentRegistrationRequest.setEmail("dayokr@gmail.com");
        talentRegistrationRequest.setPassword("password");
        talentRegistrationRequest.setPhoneNumber("07031005737");
        talentRegistrationRequest.setTalentCategory(TalentCategory.ARTISTE);
        talentRegistrationRequest.setBio("A young vibrant talented afro musician, singer of the hit song Banger."
                + " An award winning star");
        talentRegistrationRequest.setDisplayName("Jay Benjis");
        PortfolioRequest portfolioRequest = new PortfolioRequest();
        portfolioRequest.setFirstLink("https://www.youtube.com/watch?v=1qw5ITr3k9E&t=780s");
        talentRegistrationRequest.setPortfolioRequest(portfolioRequest);

        for (int index = 0; index <= 2; index++) {
            userService.registerUser(talentRegistrationRequest);
        }

        // When
        GetAllTalentsRequest getAllTalentRequest = new GetAllTalentsRequest();
        int pageNumber = 1;
        int pageSize = 1;
        getAllTalentRequest.setPageNumber(pageNumber);
        getAllTalentRequest.setPageSize(pageSize);
        List<GetAllTalentsResponse> talentsResponseList = userService.getAllTalents(getAllTalentRequest);
        System.out.println(talentsResponseList);

        // Assert
        assertThat(talentsResponseList).isNotNull();
    }

}
