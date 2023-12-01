package com.rdi.geegstar.services;

import com.rdi.geegstar.dto.requests.BookTalentRequest;
import com.rdi.geegstar.dto.response.BookCreativeTalentResponse;
import com.rdi.geegstar.exceptions.UserNotFoundException;

public interface BookTalentService {

    BookCreativeTalentResponse bookTalent(BookTalentRequest bookCreativeTalentRequest) throws UserNotFoundException;
}