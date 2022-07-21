package com.adidas.subscription.config;

import java.util.concurrent.Executor;

import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.AsyncTaskExecutor;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.task.DelegatingSecurityContextAsyncTaskExecutor;

@Configuration
@EnableAsync
@EnableScheduling
public class ConfigAsync implements AsyncConfigurer {

	@Override
	public Executor getAsyncExecutor() {
		return new DelegatingSecurityContextAsyncTaskExecutor((AsyncTaskExecutor) getTaskExecutor("my-async-executor", 500));
	}

	@Override
	public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
		return null;
	}
	
	private TaskExecutor getTaskExecutor(String threadGroupName, Integer concurrencyLimit) {
		SimpleAsyncTaskExecutor executor = new SimpleAsyncTaskExecutor();
		executor.setConcurrencyLimit(concurrencyLimit);
		executor.setThreadGroupName(threadGroupName);

		return executor;
	}

}
