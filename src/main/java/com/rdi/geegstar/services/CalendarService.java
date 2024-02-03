package com.rdi.geegstar.services;

import com.rdi.geegstar.dto.response.GetTalentCalendarsResponse;
import com.rdi.geegstar.exceptions.UserNotFoundException;

import java.util.List;

public interface CalendarService {
    List<GetTalentCalendarsResponse> getTalentCalendars(Long talentId) throws UserNotFoundException;
}
