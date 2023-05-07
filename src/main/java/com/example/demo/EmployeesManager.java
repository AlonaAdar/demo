package com.example.demo;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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
            if (employee.getId() == id) {
                return employee;
            }
        }
        return null;
    }

    public void setExitTime(LocalDateTime exitTime, int id) {
        Employee employee = getEmployee(id);
        if (employee != null) {
            TimestampPair lastPair = employee.getLastTimestampPair();
            if (lastPair != null && lastPair.getExitTime() == null) {
                lastPair.setExitTime(exitTime);
            } else {
                TimestampPair newPair = new TimestampPair(null);
                newPair.setExitTime(exitTime);
                employee.getTimestampPairs().add(newPair);
            }
        }
    }

    public void setEntranceTime(LocalDateTime entranceTime, int id) {
        Employee employee = getEmployee(id);
        if (employee == null) {
            employee = setEmployee(id);
        }
        TimestampPair newPair = new TimestampPair(entranceTime);
        employee.getTimestampPairs().add(newPair);
    }

    public Employee getEmployeeInfo(int id) {
        return getEmployee(id);
    }

    public List<Employee> getEmployeesInfo() {
        return allEmployees;
    }
}
