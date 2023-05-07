package com.example.demo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.ArrayList;
import java.util.List;

public class Employee {
    @JsonProperty("employee_id")
    private int id;

    @JsonProperty("dates")
    private List<TimestampPair> timestampPairs;

    public Employee(int id) {
        this.id = id;
        this.timestampPairs = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<TimestampPair> getTimestampPairs() {
        return timestampPairs;
    }

    public void setTimestampPairs(List<TimestampPair> timestampPairs) {
        this.timestampPairs = timestampPairs;
    }

    @JsonIgnore
    public TimestampPair getLastTimestampPair() {
        if (!timestampPairs.isEmpty()) {
            return timestampPairs.get(timestampPairs.size() - 1);
        }
        return null;
    }
    
}
