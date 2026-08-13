package com.agroPredict.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.stereotype.Service;

@Service
public class ReminderScheduler {
    @Autowired
    private TaskScheduler taskScheduler;
    @Autowired
    private EmailService emailService;

    public void scheduleEveryOneMinute(String email) {



        taskScheduler.scheduleAtFixedRate(
                () -> emailService.sendReminder(email),
                60000
        );
    }
}

