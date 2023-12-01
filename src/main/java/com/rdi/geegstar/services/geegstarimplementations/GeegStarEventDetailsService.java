package com.rdi.geegstar.services.geegstarimplementations;

import com.rdi.geegstar.data.models.Address;
import com.rdi.geegstar.data.models.EventDetail;
import com.rdi.geegstar.data.repositories.EventDetailRepository;
import com.rdi.geegstar.dto.requests.AddressRequest;
import com.rdi.geegstar.dto.requests.EventDetailRequest;
import com.rdi.geegstar.services.EventDetailsService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class GeegStarEventDetailsService implements EventDetailsService {

    private final ModelMapper modelMapper;
    private final EventDetailRepository eventDetailsRepository;

    @Override
    public EventDetail create(EventDetailRequest eventDetailsRequest) {
        AddressRequest addressRequest = eventDetailsRequest.getEventAddress();
        Address address = modelMapper.map(addressRequest, Address.class);
        EventDetail eventDetails = modelMapper.map(eventDetailsRequest, EventDetail.class);
        eventDetails.setEventAddress(address);
        return eventDetailsRepository.save(eventDetails);
    }
}
