package com.crud.tasks.service;

import com.crud.tasks.config.AdminConfig;
import com.crud.tasks.domain.appObjects.Mail;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SimpleEmailServiceTest {
    @InjectMocks
    private SimpleEmailService simpleEmailService;

    @Mock
    private MailCreatorService mailCreatorService;

    @Mock
    private JavaMailSender mailSender;

    @Mock
    AdminConfig config;

    @Captor
    private ArgumentCaptor<MimeMessagePreparator> captureMessage;

    @Captor
    private ArgumentCaptor<MimeMessagePreparator> captureMessageWithoutCC;

    @Test
    public void shouldSendEmail(){
    //Given
        Mail mail = new Mail("test@tes.com", "Test", "Test Message", "TestCC");

        MimeMessagePreparator mailMessage = mimeMessage -> {
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
            messageHelper.setTo(mail.getMailTo());
            messageHelper.setSubject(mail.getSubject());
            messageHelper.setText(mail.getMessage(), true);
            messageHelper.setCc(mail.getToCc());
        };

    //When
    simpleEmailService.send(mail);

    //Then
    verify(mailSender, times(1)).send(captureMessage.capture());
    }

    @Test
    public void shouldSendEmailWithoutCc(){
        //Given
        Mail mail = new Mail("test@tes.com", "Test", "Test Message", null);

        MimeMessagePreparator mailMessage = mimeMessage -> {
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
            messageHelper.setTo(mail.getMailTo());
            messageHelper.setSubject(mail.getSubject());
            messageHelper.setText(mail.getMessage(), true);
        };

        //When
        simpleEmailService.send(mail);

        //Then
        verify(mailSender, times(1)).send(captureMessageWithoutCC.capture());
    }
}