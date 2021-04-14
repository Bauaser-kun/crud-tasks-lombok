package com.crud.tasks.service;

import com.crud.tasks.config.AdminConfig;
import com.crud.tasks.config.CompanyConfig;
import com.crud.tasks.shedulder.EmailScheduler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.util.ArrayList;
import java.util.List;

@Service
public class MailCreatorService {
    @Autowired
    private CompanyConfig companyConfig;

    @Autowired
    private AdminConfig adminConfig;

    @Autowired
    @Qualifier("templateEngine")
    private TemplateEngine templateEngine;

    public String buildTrelloCardEmail(String message) {
        List<String> functionality = new ArrayList<>();
        functionality.add("You can manage your tasks");
        functionality.add("Provides connection with Trello Account");
        functionality.add("Application allows sending tasks to Trello");

        Context context = new Context();
        context.setVariable("message", message);
        context.setVariable("tasks_url", "https://bauaser-kun.github.io/");
        context.setVariable("button", "Visit website");
        context.setVariable("admin_name", adminConfig.getAdminName());
        context.setVariable("preview", previewMessage(message));
        context.setVariable("goodbye", "Sincerely. App developer team.");
        context.setVariable("company", companyConfig.getCompanyName() + "/n" + companyConfig.getCompanyMail() + "/n" + companyConfig.getCompanyPhone());
        context.setVariable("show_button", false);
        context.setVariable("is_friend", true);
        context.setVariable("admin_config", adminConfig);
        context.setVariable("application_functionality", functionality);

        return  templateEngine.process("mail/created-trello-card-mail", context);
    }

    private String previewMessage(String message) {
        String[] words = message.split(" ");
        return words[0] + " " + words[1] + " " + words[2] + words[3] + words[4] +"(...)";
    }

    public String buildDailyUpdateMail(String message) {
        ArrayList<String> schedulerFunctionality = new ArrayList<>();
        schedulerFunctionality.add("Sending update once a day");
        schedulerFunctionality.add("Provides number of active tasks");


        Context context = new Context();
        context.setVariable("message", message);
        context.setVariable("task_url", "https://bauaser-kun.github.io/");
        context.setVariable("button", "Check Your Crud App");
        context.setVariable("admin_name", adminConfig.getAdminName());
        context.setVariable("goodbye", "See you tomorrow");
        context.setVariable("company", companyConfig.getCompanyName() + "/n" + companyConfig.getCompanyMail() + "/n" + companyConfig.getCompanyPhone());
        context.setVariable("scheduler_functionality", schedulerFunctionality);

        return templateEngine.process("mail/scheduled-email", context);
    }
}
