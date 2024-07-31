package com.project.batchApplication.config;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.core.task.TaskExecutor;
import org.springframework.transaction.PlatformTransactionManager;

import com.project.batchApplication.listener.ChunkLisener;
import com.project.batchApplication.listener.JobListener;
import com.project.batchApplication.listener.ReaderListener;
import com.project.batchApplication.listener.StepListener;
import com.project.batchApplication.listener.WriterListener;
import com.project.batchApplication.model.Restaurant;

import lombok.AllArgsConstructor;

@Configuration
@AllArgsConstructor
public class RestaurantDBBatch {

	@Bean
	public FlatFileItemReader<Restaurant> reader() throws Exception {
		FlatFileItemReader<Restaurant> itemReader = new FlatFileItemReader<>();

		itemReader.setResource(new ClassPathResource("data2.csv"));
		itemReader.setName("csvReader");
		itemReader.setLinesToSkip(1);
		//itemReader.setEncoding("euc-kr");
		itemReader.setEncoding("utf-8");
		itemReader.setLineMapper(lineMapper());

		return itemReader;
	}

	private LineMapper<Restaurant> lineMapper() {
		DefaultLineMapper<Restaurant> lineMapper = new DefaultLineMapper<>();
		DelimitedLineTokenizer lineTokenizer = new DelimitedLineTokenizer();

		lineTokenizer.setDelimiter(",");
		lineTokenizer.setStrict(false);
		lineTokenizer.setNames(Restaurant.getFieldNames().toArray(String[]::new));

		lineMapper.setLineTokenizer(lineTokenizer);

		BeanWrapperFieldSetMapper<Restaurant> fieldSetMapper = new BeanWrapperFieldSetMapper<>();
		fieldSetMapper.setTargetType(Restaurant.class);

		lineMapper.setFieldSetMapper(fieldSetMapper);
		return lineMapper;

	}

	@Bean
	public ItemWriter<Restaurant> writer() {
		return new RestaurantWriter();
	}

	@Bean
	public Job importRestaurantJob(JobRepository jobRepository, Step restaurantLoadStep) {
		return new JobBuilder("importRestaurant", jobRepository)
				.incrementer(new RunIdIncrementer())
				.start(restaurantLoadStep)
				.listener(new JobListener()) //job listener
				.build();
	}

	@Bean
	public Step restaurantLoadStep(JobRepository jobRepository, PlatformTransactionManager transactionManager,
			FlatFileItemReader<Restaurant> fileReader)
			throws Exception {
		return new StepBuilder("restaurantLoadStep", jobRepository)
				.<Restaurant, Restaurant>chunk(1000, transactionManager)
				.reader(fileReader)
				.listener(new ReaderListener()) //read listener
				.writer(writer())
				.listener(new WriterListener()) //write listener
				.taskExecutor(taskExecutor())
				.faultTolerant()
				.skip(RuntimeException.class)
				.noSkip(IllegalArgumentException.class)
				.skipLimit(10)
				.listener(new StepListener()) //step listener
				.listener(new ChunkLisener()) //chunk listener
				.build();
	}

	@Bean
	public TaskExecutor taskExecutor() {
		SimpleAsyncTaskExecutor taskExecutor = new SimpleAsyncTaskExecutor("spring-bach-task-executor");
		taskExecutor.setConcurrencyLimit(10);
		
		return taskExecutor;
	}
}