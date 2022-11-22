package com.flab.bigtrader.common.kafka.serializer;

import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.serialization.Serializer;

import com.fasterxml.jackson.databind.ObjectMapper;

public class CustomObjectSerializer implements Serializer<Object> {
	private static final ObjectMapper CACHED_MAPPER = new ObjectMapper();

	@Override
	public byte[] serialize(String topic, Object event) {
		try {
			if (event == null) {
				return null;
			}

			return CACHED_MAPPER.writeValueAsBytes(event);
		} catch (Exception e) {
			throw new SerializationException("Error when serializing event to byte[]");
		}
	}
}
