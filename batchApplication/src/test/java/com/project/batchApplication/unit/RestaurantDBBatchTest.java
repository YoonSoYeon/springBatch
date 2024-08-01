package com.project.batchApplication.unit;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.test.JobLauncherTestUtils;
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
	private JdbcTemplate jdbcTemplate;

	@Test
	public void successfully_when_no_error() throws Exception {
		// given
		this.jdbcTemplate.update("delete from restaurant");

		// when
		JobExecution jobExecution = this.jobLauncherTestUtils.launchJob(buildJobParameters("data.csv"));

		// then
		assertThat(BatchStatus.COMPLETED.toString()).isEqualTo(jobExecution.getExitStatus().getExitCode());
	}
	
	@Test
	public void run_status_is_failed_notFoundFile() throws Exception {
		// given
		// when
		JobExecution jobExecution = this.jobLauncherTestUtils.launchJob(buildJobParameters("no_data.csv"));

		// then
		assertThat(BatchStatus.FAILED.toString()).isEqualTo(jobExecution.getExitStatus().getExitCode());
	}
	
	@Test
	public void step_test() throws Exception {
		// given
		Long chunkSize = 10000L;
		this.jdbcTemplate.update("delete from restaurant");
		JobExecution jobExecution = this.jobLauncherTestUtils.launchJob(buildJobParameters("data.csv", chunkSize));
		
		//when
		StepExecution stepExecution =  ((List<StepExecution>) jobExecution.getStepExecutions()).get(0);
		
		//then
		assertThat(stepExecution.getReadCount()).isEqualTo(2152084);
		assertThat(stepExecution.getWriteCount()).isEqualTo(2152084);
	}

	private JobParameters buildJobParameters(String filePath) {
		return buildJobParameters(filePath, 5000L);
	}
	
	private JobParameters buildJobParameters(String filePath, Long chunkSize) {
		return new JobParametersBuilder()
				.addString("pathToFile", filePath)
				.addLong("chunkSize", chunkSize)
				.addLong("currentTimeInMillis", System.currentTimeMillis())
				.toJobParameters();
	}
}