package com.rdi.geegstar.services;

import com.rdi.geegstar.data.models.Calendar;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
public class CalendarServiceTest {

    @Autowired
    private CalendarService calendarService;
    @Test
    public void testCreateCalender() {
        Calendar calendar = new Calendar();
        Calendar savedCalendar = calendarService.create(calendar);
        assertThat(savedCalendar).isNotNull();
    }
}
