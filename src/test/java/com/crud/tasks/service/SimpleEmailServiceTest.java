package com.crud.tasks.service;

import com.crud.tasks.config.AdminConfig;
import com.crud.tasks.domain.appObjects.Mail;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class SimpleEmailServiceTest {
    @InjectMocks
    private SimpleEmailService service;

    @Mock
    private JavaMailSender mailSender;

    @Mock
    AdminConfig config;

    @Test
    public void shouldSendEmail(){
    //Given
    Mail mail = new Mail("test@tes.com", "Test", "Test Message", "TestCC");

    SimpleMailMessage mailMessage = new SimpleMailMessage();
    mailMessage.setTo(mail.getMailTo());
    mailMessage.setSubject(mail.getSubject());
    mailMessage.setText(mail.getMessage());
    mailMessage.setCc(mail.getToCc());

    //When
    service.send(mail);

    //Then
    verify(mailSender, times(1)).send(mailMessage);
    }

    @Test
    public void shouldSendEmailWithoutCc(){
        //Given
        Mail mail = new Mail("test@tes.com", "Test", "Test Message", null);

        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(mail.getMailTo());
        mailMessage.setSubject(mail.getSubject());
        mailMessage.setText(mail.getMessage());

        //When
        service.send(mail);

        //Then
        verify(mailSender, times(1)).send(mailMessage);
        assertEquals(mailMessage.getFrom(), config.getAdminMail());
    }
}