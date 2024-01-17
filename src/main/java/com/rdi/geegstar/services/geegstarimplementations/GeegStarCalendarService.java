package com.rdi.geegstar.services.geegstarimplementations;

import com.rdi.geegstar.data.models.Calendar;
import com.rdi.geegstar.data.repositories.CalenderRepository;
import com.rdi.geegstar.services.CalendarService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class GeegStarCalendarService implements CalendarService {

    private final CalenderRepository calenderRepository;
    @Override
    public Calendar create(Calendar calendar) {
        return calenderRepository.save(calendar);
    }
}
