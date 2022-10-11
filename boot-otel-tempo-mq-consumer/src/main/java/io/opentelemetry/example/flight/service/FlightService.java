package io.opentelemetry.example.flight.service;

import java.time.Duration;

import io.opentelemetry.api.trace.Span;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import io.opentelemetry.example.flight.model.Flight;
import io.opentelemetry.extension.annotations.WithSpan;

@Service
public class FlightService {

	private static final Logger LOGGER = LoggerFactory.getLogger(FlightService.class);

	private StringRedisTemplate stringRedisTemplate;

	public FlightService(StringRedisTemplate stringRedisTemplate) {
		this.stringRedisTemplate = stringRedisTemplate;
	}

	@WithSpan
	public void process(Flight flight) {
		LOGGER.info("Processing : {}", flight);
		Span span = Span.current();
		span.setAttribute("attribute.process", "I would like to write redis");

		valExpire("valExpireKey");
	}

	@WithSpan
	private void valExpire(String key) {
		LOGGER.info("Redis set key : {}", key);
		Span span = Span.current();
		span.setAttribute("attribute.key", key);
		stringRedisTemplate.opsForValue().set(key, "SomeValue", Duration.ofSeconds(1));
	}
}
