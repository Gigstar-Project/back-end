package com.rdi.geegstar.services;

import com.rdi.geegstar.data.models.Address;
import com.rdi.geegstar.dto.requests.AddressRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static com.rdi.geegstar.services.BookTalentServiceTest.getAddressRequest;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
public class AddressServiceTest {

    @Autowired
    private AddressService addressService;

    @Test
    public void testCreateAddress() {
        AddressRequest addressRequest = getAddressRequest();

        Address address = addressService.createAddress(addressRequest);

        assertThat(address).isNotNull();
    }
}
