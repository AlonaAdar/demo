package com.example.demo;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.time.format.DateTimeFormatter;

public class TimestampPairSerializer extends JsonSerializer<TimestampPair> {
    @Override
    public void serialize(TimestampPair timestampPair, JsonGenerator jsonGenerator,
            SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeStartArray();
        jsonGenerator
                .writeString(timestampPair.getEntranceTime().format(DateTimeFormatter.ofPattern("dd-MM-yyyy-HH:mm")));
        if (timestampPair.getExitTime() != null) {
            jsonGenerator
                    .writeString(timestampPair.getExitTime().format(DateTimeFormatter.ofPattern("dd-MM-yyyy-HH:mm")));
        }
        jsonGenerator.writeEndArray();
    }
}
