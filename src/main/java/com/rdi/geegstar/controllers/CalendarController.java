package com.rdi.geegstar.controllers;

import com.rdi.geegstar.data.models.Calendar;
import com.rdi.geegstar.exceptions.UserNotFoundException;
import com.rdi.geegstar.services.CalendarService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatusCode;
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
    public ResponseEntity<?> getTalentCalendars(@PathVariable Long talentId) {
        try {
            return ResponseEntity.ok().body(calendarService.getTalentCalendars(talentId));
        } catch (UserNotFoundException exception) {
            return ResponseEntity.badRequest().body(exception);
        }
    }
}
