package com.project.batchApplication.unit;


import static org.assertj.core.api.Assertions.assertThat;

import java.util.Date;

import org.junit.jupiter.api.Test;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.batch.test.context.SpringBatchTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.project.batchApplication.config.RestaurantDBBatch;
import com.project.batchApplication.config.RestaurantWriter;

@SpringBatchTest
@SpringBootTest(classes = { RestaurantDBBatch.class, TestConfiguration.class })
public class RestaurantDBBatchTest {

	@Autowired
	private JobLauncherTestUtils jobLauncherTestUtils;

	@Test
	public void JDBC_READER_TEST() throws Exception {
		final JobParameters jobparameters = new JobParametersBuilder()
				.addLong("age", 15L)
				.addLong("date", new Date().getTime())
				.toJobParameters();
		
		final JobExecution jobExecution = jobLauncherTestUtils.launchJob(jobparameters);
		
		assertThat(jobExecution.getStatus()).isEqualTo(BatchStatus.COMPLETED);
		assertThat(jobExecution.getExitStatus()).isEqualTo(BatchStatus.COMPLETED);
	}
}
