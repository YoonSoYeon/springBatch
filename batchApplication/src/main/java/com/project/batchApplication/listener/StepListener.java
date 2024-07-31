package com.project.batchApplication.listener;

import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class StepListener implements StepExecutionListener {

	@Override
	public void beforeStep(StepExecution stepExecution) {
		if (stepExecution.getStatus() == BatchStatus.STARTED)
			log.info("Step Start");
	}

	@Override
	public ExitStatus afterStep(StepExecution stepExecution) {
		if (stepExecution.getReadCount() > 0) {
			return stepExecution.getExitStatus();
		} else {
			return ExitStatus.FAILED;
		}
	}
}
