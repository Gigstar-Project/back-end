package com.rdi.geegstar.services;

import com.rdi.geegstar.data.models.Address;
import com.rdi.geegstar.data.models.EventDetails;
import com.rdi.geegstar.data.repositories.EventDetailsRepository;
import com.rdi.geegstar.dto.requests.AddressRequest;
import com.rdi.geegstar.dto.requests.EventDetailsRequest;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class GeegStarEventDetailsService implements EventDetailsService{

    private final AddressService addressService;
    private final ModelMapper modelMapper;
    private final EventDetailsRepository eventDetailsRepository;

    @Override
    public EventDetails create(EventDetailsRequest eventDetailsRequest) {
        AddressRequest addressRequest = eventDetailsRequest.getEventAddress();
        Address savedAddress = addressService.createAddress(addressRequest);
        EventDetails eventDetails = modelMapper.map(eventDetailsRequest, EventDetails.class);
        eventDetails.setEventAddress(savedAddress);
        return eventDetailsRepository.save(eventDetails);
    }
}
