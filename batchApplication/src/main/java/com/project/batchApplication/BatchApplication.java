package com.project.batchApplication;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class BatchApplication {

	public static void main(String[] args) {
		System.exit(SpringApplication.exit(SpringApplication.run(BatchApplication.class, args)));
	}

	@Bean
	public ApplicationRunner runner(JobLauncher jobLauncher, Job job) {
		return new ApplicationRunner() {
			@Override
			public void run(ApplicationArguments args) throws Exception {
				jobLauncher.run(job,
						new JobParametersBuilder()
						.addString("pathToFile", "data.csv")
						.addLong("chunkSize", 10000L)
						.addLong("currentTimeInMillis", System.currentTimeMillis())
						.toJobParameters());
			}
		};
	}
}
