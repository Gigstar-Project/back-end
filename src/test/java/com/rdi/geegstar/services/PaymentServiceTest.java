package com.rdi.geegstar.services;

import com.rdi.geegstar.data.models.Payment;
import com.rdi.geegstar.dto.requests.PaymentRequest;
import com.rdi.geegstar.dto.requests.PlannerRegistrationRequest;
import com.rdi.geegstar.dto.requests.PortfolioRequest;
import com.rdi.geegstar.dto.requests.TalentRegistrationRequest;
import com.rdi.geegstar.dto.response.RegistrationResponse;
import com.rdi.geegstar.enums.TalentCategory;
import com.rdi.geegstar.exceptions.UserNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import java.math.BigDecimal;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
public class PaymentServiceTest {

    @Autowired
    private PaymentService paymentService;
    @Autowired
    private UserService userService;

    @Test
    @Sql("/db/insertUsers.sql")
    public void testPayment() throws UserNotFoundException {
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
        RegistrationResponse talentRegistrationResponse = userService.registerUser(talentRegistrationRequest);
        PlannerRegistrationRequest plannerRegistrationRequest = new PlannerRegistrationRequest();
        plannerRegistrationRequest.setFirstName("Retnaa");
        plannerRegistrationRequest.setLastName("Dayok");
        plannerRegistrationRequest.setEmail("dayokr@gmail.com");
        plannerRegistrationRequest.setPassword("password");
        plannerRegistrationRequest.setPhoneNumber("07031005737");
        plannerRegistrationRequest.setEventPlanningCompanyName("StarEvents Inc");
        RegistrationResponse plannerRegistrationResponse = userService.registerUser(plannerRegistrationRequest);
        PaymentRequest paymentRequest = new PaymentRequest();
        paymentRequest.setSender(plannerRegistrationResponse.getId());
        paymentRequest.setReceiver(talentRegistrationResponse.getId());
        paymentRequest.setAmount(BigDecimal.valueOf(200_000));

        // When
        Payment payment = paymentService.pay(paymentRequest);

        // Assert
        assertThat(payment).isNotNull();
    }
}
