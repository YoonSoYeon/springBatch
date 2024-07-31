package com.project.batchApplication.unit;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.batch.test.JobRepositoryTestUtils;
import org.springframework.batch.test.context.SpringBatchTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.project.batchApplication.config.RestaurantDBBatch;
import com.project.batchApplication.repository.RestaurantRepository;

@ExtendWith(SpringExtension.class)
@SpringBatchTest
@SpringBootTest(classes = {TestConfiguration.class, RestaurantDBBatch.class, RestaurantRepository.class})
public class RestaurantDBBatchTest {

	@Autowired
	private JobLauncherTestUtils jobLauncherTestUtils;

	@Autowired
	private JobRepositoryTestUtils jobRepositoryTestUtils;

	@Autowired
	private JdbcTemplate jdbcTemplate;

	
	@BeforeEach
	public void setup(@Autowired Job jobUnderTest) {
		this.jobLauncherTestUtils.setJob(jobUnderTest); // this is optional if the job is unique
		this.jobRepositoryTestUtils.removeJobExecutions();
	}

	@Test
	public void successfully_when_no_error() throws Exception {
		// given
		this.jdbcTemplate.update("delete from restaurant");
		JobParameters jobParameters = this.jobLauncherTestUtils.getUniqueJobParameters();

		// when
		JobExecution jobExecution = this.jobLauncherTestUtils.launchJob(jobParameters);

		// then
		assertThat(BatchStatus.COMPLETED.toString()).isEqualTo(jobExecution.getExitStatus().getExitCode());
	}
	
	@Test
	public void run_status_is_failed() throws Exception {
		// given
		JobParameters jobParameters = this.jobLauncherTestUtils.getUniqueJobParameters();

		// when
		JobExecution jobExecution = this.jobLauncherTestUtils.launchJob(jobParameters);

		// then
		assertThat(BatchStatus.FAILED.toString()).isEqualTo(jobExecution.getExitStatus().getExitCode());
	}
}