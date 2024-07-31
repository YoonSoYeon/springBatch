package com.project.batchApplication.listener;

import org.springframework.batch.core.ItemReadListener;
import org.springframework.batch.item.file.FlatFileParseException;

import com.project.batchApplication.model.Restaurant;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ReaderListener implements ItemReadListener<Restaurant> {

	@Override
	public void beforeRead() {

	}

	@Override
	public void afterRead(Restaurant item) {
		log.info("Thread : " + Thread.currentThread().getName() + ", read item :" + item.getRestaurantId());
	}

	@Override
	public void onReadError(Exception ex) {
		if (ex instanceof FlatFileParseException) {
			FlatFileParseException ffpe = (FlatFileParseException) ex;
			StringBuilder errorMessage = new StringBuilder();
			errorMessage.append("An error occured while processing the " + ffpe.getLineNumber()
					+ " line of the file.Below was the faulty " + "input.\n");
			errorMessage.append(ffpe.getInput() + "\n");

			log.error(errorMessage.toString(), ffpe);
		} else {
			log.error("An error has occurred", ex);
		}
	}
}
