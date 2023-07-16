package com.example.DisasterInformationCollectionServer.config;

import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class ByteArrayRedisSerializer implements RedisSerializer<byte[]> {
    @Override
    public byte[] serialize(byte[] bytes) throws SerializationException {
        return bytes;
    }

    @Override
    public byte[] deserialize(byte[] bytes) throws SerializationException {
        return bytes;
    }

    public byte[] serialize(String s) throws SerializationException {
        return s.getBytes(StandardCharsets.UTF_8);
    }

    public String deserialize(byte[] bytes, Charset charset) throws SerializationException {
        return new String(bytes, charset);
    }
}
