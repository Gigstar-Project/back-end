package com.rdi.geegstar.controllers;

import com.rdi.geegstar.dto.response.GetTalentCalendarsResponse;
import com.rdi.geegstar.exceptions.UserNotFoundException;
import com.rdi.geegstar.services.CalendarService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/calendar")
@RequiredArgsConstructor
public class CalendarController {

    final private CalendarService calendarService;

    @GetMapping("{talentId}")
    public ResponseEntity<List<GetTalentCalendarsResponse>> getTalentCalendars(@PathVariable Long talentId)
            throws UserNotFoundException {
        return ResponseEntity.ok().body(calendarService.getTalentCalendars(talentId));
    }
}
