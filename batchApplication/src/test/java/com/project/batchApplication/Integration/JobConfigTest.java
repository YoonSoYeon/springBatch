package com.project.batchApplication.Integration;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.batch.test.context.SpringBatchTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.project.batchApplication.config.RestaurantDBBatch;
import com.project.batchApplication.repository.RestaurantRepository;

@SpringBatchTest
@SpringBootTest(classes = {RestaurantDBBatch.class})
public class JobConfigTest {

	@Autowired
	private JobLauncherTestUtils jobLauncherTestUtils;

	@Autowired
	private RestaurantRepository restaurantRepository;

	@Test
	public void simpleJob_test() throws Exception {
		// given
		JobParameters jobParameters = new JobParametersBuilder().addString("message", "test seohae batch job")
				.toJobParameters();

		// when
		JobExecution jobExecution = jobLauncherTestUtils.launchJob(jobParameters);

		// then
		assertThat(jobExecution.getStatus()).isEqualTo(BatchStatus.COMPLETED);
	}

	@Test
	public void simpleStep_test() throws Exception {
		// given
		JobParameters jobParameters = new JobParametersBuilder().addString("message", "test seohae batch job step")
				.toJobParameters();

		/** step 1개만 테스트 */
		JobExecution jobExecution2 = jobLauncherTestUtils.launchStep("jobScopeStepScopeStep2");

		// stepExecution 꺼내기
		StepExecution stepExecution = (StepExecution) ((List) jobExecution2.getStepExecutions()).get(0);

		//Assert.assertEquals(stepExecution.getStatus(), BatchStatus.COMPLETED);
		//Assert.assertEquals(stepExecution.getExitStatus(), ExitStatus.COMPLETED);
	}
}
