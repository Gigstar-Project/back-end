package com.rdi.geegstar.services;

import com.rdi.geegstar.data.models.Address;
import com.rdi.geegstar.dto.requests.AddressRequest;

public interface AddressService {
    Address createAddress(AddressRequest addressRequest);
}
