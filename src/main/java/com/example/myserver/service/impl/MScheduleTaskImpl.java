package com.example.myserver.service.impl;


import com.example.myserver.service.MScheduleTask;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Set;

/**
 * @author by Jiang Xiaomin
 * @desrc:
 */
@Component
// 开启定时任务
@EnableScheduling
// 开启多线程
@EnableAsync
@Slf4j
public class MScheduleTaskImpl implements MScheduleTask {
    @Async
    @Scheduled(cron = "${my.cron}")
    public synchronized void pushData() {
        log.info("定时任务执行中", Instant.now());
    }
}
