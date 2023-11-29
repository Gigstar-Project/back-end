package com.rdi.gigstar.services;


import com.rdi.gigstar.dto.requests.EmailRequest;
import com.rdi.gigstar.dto.response.EmailResponse;

public interface MailService {
    EmailResponse sendMail(EmailRequest emailRequest);
}
