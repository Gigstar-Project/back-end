package com.rdi.geegstar.services;

import com.rdi.geegstar.data.models.Address;
import com.rdi.geegstar.data.repositories.AddressRepository;
import com.rdi.geegstar.dto.requests.AddressRequest;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class GeegStarAddressService implements AddressService{

    private final AddressRepository addressRepository;
    private final ModelMapper modelMapper;
    @Override
    public Address createAddress(AddressRequest addressRequest) {
        Address address = modelMapper.map(addressRequest, Address.class);
        return addressRepository.save(address);
    }

}
