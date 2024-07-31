package com.project.batchApplication.config;

import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;

import com.project.batchApplication.model.Restaurant;
import com.project.batchApplication.repository.RestaurantRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class RestaurantWriter implements ItemWriter<Restaurant>{
	
	@Autowired
	private RestaurantRepository restaurantRepository;
	
	@Override
	public void write(Chunk<? extends Restaurant> chunk) throws Exception {
		restaurantRepository.saveAll(chunk.getItems());
	}

}
