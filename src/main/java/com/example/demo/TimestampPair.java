package com.example.demo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import java.time.LocalDateTime;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonPropertyOrder({ "entranceTime", "exitTime" })
@JsonSerialize(using = TimestampPairSerializer.class)
public class TimestampPair {
    @JsonFormat(pattern = "dd-MM-yyyy-HH:mm")
    private LocalDateTime entranceTime;

    @JsonFormat(pattern = "dd-MM-yyyy-HH:mm")
    private LocalDateTime exitTime;

    public TimestampPair(LocalDateTime entranceTime) {
        this.entranceTime = entranceTime;
    }

    public LocalDateTime getEntranceTime() {
        return entranceTime;
    }

    public void setEntranceTime(LocalDateTime entranceTime) {
        this.entranceTime = entranceTime;
    }

    public LocalDateTime getExitTime() {
        return exitTime;
    }

    public void setExitTime(LocalDateTime exitTime) {
        this.exitTime = exitTime;
    }
}

