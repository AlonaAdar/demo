package com.example.demo;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import org.json.JSONArray;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

class Employee {
    int id;
    List<List<LocalDateTime>> allDates;

    public Employee(int id) {
        this.id = id;
        this.allDates = new ArrayList<>();
    }
}

class EmployeesManager {
    private List<Employee> allEmployees;

    public EmployeesManager() {
        allEmployees = new ArrayList<>();
    }

    public Employee setEmployee(int id) {
        Employee newEmployee = new Employee(id);
        allEmployees.add(newEmployee);
        return newEmployee;
    }

    public Employee getEmployee(int id) {
        for (Employee employee : allEmployees) {
            if (employee.id == id) {
                return employee;
            }
        }
        return null;
    }

    public List<LocalDateTime> setDates() {
        return new ArrayList<>();
    }

    public void setExitTime(LocalDateTime exitTime, int id) {
        Employee employee = getEmployee(id);
        List<LocalDateTime> dates = setDates();
        dates.add(exitTime);
        employee.allDates.add(dates);
    }

    public void setEntranceTime(LocalDateTime entranceTime, int id) {
        Employee employee = getEmployee(id);
        if (employee == null) {
            employee = setEmployee(id);
        }
        List<LocalDateTime> newDate = setDates();
        newDate.add(entranceTime);
        employee.allDates.add(newDate);
    }

    public String processEmployeeInfo(List<LocalDateTime> date, Employee employee) {
        String employeeString = objectToString(employee);
        String invalidList = date.toString();
        String updatedJson = setNotice(invalidList, employeeString);
        return updatedJson;
    }

    public String objectToString(Employee employee) {
        Gson gson = new Gson();
        return gson.toJson(employee);
    }

    public String setNotice(String invalidList, String jsonString) {
        String notice = "# Only enter time was received";
        int indexOfList = jsonString.indexOf(invalidList);
        String modifiedJsonString = jsonString.substring(0, indexOfList) +
                notice + jsonString.substring(indexOfList);
        return modifiedJsonString;
    }

    public String getEmployeeInfo(int id) {
        Employee employee = getEmployee(id);
        if (employee == null) {
            return null;
        }
        boolean conditionMet = false;
        String employeeInfo = "";

        int loopCounter = 0;
        for (List<LocalDateTime> date : employee.allDates) {
            if (date.size() == 1) {
                employeeInfo = processEmployeeInfo(date, employee);
                conditionMet = true;
            }
            loopCounter++;

            if (loopCounter == employee.allDates.size() && !conditionMet) {
                employeeInfo = objectToString(employee);
            }
        }
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonResult = "";
        try {
            jsonResult = objectMapper.writeValueAsString(employeeInfo);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return jsonResult;
    }

    public String getEmployeesInfo() {
        JSONArray jsonArray = new JSONArray();
        for (Employee employee : allEmployees) {
            String eachEmployee = getEmployeeInfo(employee.id);
            jsonArray.put(eachEmployee);
        }
        return jsonArray.toString();
    }
}
