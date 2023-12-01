package com.rdi.geegstar.services;

import com.rdi.geegstar.dto.requests.BookCreativeTalentRequest;
import com.rdi.geegstar.dto.response.BookCreativeTalentResponse;
import com.rdi.geegstar.exceptions.UserNotFoundException;

public interface BookTalentService {

    BookCreativeTalentResponse bookCreativeTalent(BookCreativeTalentRequest bookCreativeTalentRequest) throws UserNotFoundException;
}