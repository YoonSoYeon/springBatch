package com.project.batchApplication.listener;

import java.util.List;

import org.springframework.batch.core.ItemWriteListener;

import com.project.batchApplication.model.Restaurant;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class WriterListener implements ItemWriteListener<Restaurant> {
	
	public void beforeWrite(List<? extends Restaurant> items) {
		
	}
	
	public void afterWrite(List<? extends Restaurant> items) {
		log.info("Thread :" + Thread.currentThread().getName() + ", write item : " + items.size());
	}
	
	public void onWriteError(Exception exception, List<? extends Restaurant> items) {
		
	}

}
