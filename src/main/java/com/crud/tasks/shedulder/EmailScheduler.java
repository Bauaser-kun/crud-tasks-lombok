package com.crud.tasks.shedulder;

import com.crud.tasks.config.AdminConfig;
import com.crud.tasks.domain.appObjects.Mail;
import com.crud.tasks.repository.TaskRepository;
import com.crud.tasks.service.SimpleEmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EmailScheduler {
private final SimpleEmailService simpleEmailService;
private final TaskRepository taskRepository;
private final AdminConfig adminConfig;
public static final String SUBJECT = "Tasks: Once a day email";

    @Scheduled(cron = "0 0 10 * * *")
    public void sendInformationEmail() {
    long size = taskRepository.count();
    String message = "Currently in database you got: " + size + " task.";
    if(size > 1) {
        message = "Currently in database you got: " + size + " tasks.";
    }
    simpleEmailService.send(
            new Mail(
                    adminConfig.getAdminMail(),
                    SUBJECT,
                    message,
                    null
            )
    );
    }
}
