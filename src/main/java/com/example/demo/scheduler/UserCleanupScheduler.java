package com.example.demo.scheduler;

import com.example.demo.service.NameService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Component
public class UserCleanupScheduler {

    private final NameService nameService;

    @Scheduled(cron = "${app.cleanup-cron}")
    public void cleanup() {
        nameService.cleanShortNames();
    }
}