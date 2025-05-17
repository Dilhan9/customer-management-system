package com.assignment.customermanagement.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

@Configuration
@EnableAsync
public class AsyncConfig {

    @Bean(name = "taskExecutor")
    public Executor taskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();

        // Core number of threads
        executor.setCorePoolSize(5);

        // Maximum allowed threads
        executor.setMaxPoolSize(10);

        // Queue capacity for tasks before new threads are created
        executor.setQueueCapacity(500);

        // Thread name prefix for easier debugging
        executor.setThreadNamePrefix("AsyncExecutor-");

        // Initialize the executor
        executor.initialize();

        return executor;
    }
}
