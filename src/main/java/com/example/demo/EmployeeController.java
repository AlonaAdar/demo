package com.example.demo;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@RequestMapping("/api")
public class EmployeeController {

    private final EmployeesManager employeesManager;
    private final ObjectMapper objectMapper;

    public EmployeeController() {
        this.employeesManager = new EmployeesManager();
        this.objectMapper = new ObjectMapper();
    }

    @PostMapping("/enter")
    public ResponseEntity<String> enter(@RequestParam("id") int id) {
        employeesManager.setEntranceTime(LocalDateTime.now(), id);
        return new ResponseEntity<>("Entrance time logged", HttpStatus.OK);
    }

    @PostMapping("/exit")
    public ResponseEntity<String> exit(@RequestParam("id") int id) {
        employeesManager.setExitTime(LocalDateTime.now(), id);
        return new ResponseEntity<>("Exit time logged", HttpStatus.OK);
    }

    private String modifyResponseForSingleEmployee(String response, Employee employee) {
        if (employee.getTimestampPairs().size() == 1 && employee.getTimestampPairs().get(0).getExitTime() == null) {
            int lastIndex = response.lastIndexOf(']');
            return response.substring(0, lastIndex) + "\"# Only enter time was received\""
                    + response.substring(lastIndex);
        }
        return response;
    }

    private String modifyResponseForEmployeesList(String response, List<Employee> employees) {
        StringBuilder modifiedResponse = new StringBuilder("[");
        for (int i = 0; i < employees.size(); i++) {
            try {
                String employeeInfo = objectMapper.writeValueAsString(employees.get(i));
                modifiedResponse.append(modifyResponseForSingleEmployee(employeeInfo, employees.get(i)));
                if (i < employees.size() - 1) {
                    modifiedResponse.append(",");
                }
            } catch (JsonProcessingException e) {
                e.printStackTrace();
                return null;
            }
        }
        modifiedResponse.append("]");
        return modifiedResponse.toString();
    }

    @GetMapping("/info")
    public ResponseEntity<String> info(@RequestParam(value = "id", required = false) Integer id) {
        if (id != null) {
            Employee employee = employeesManager.getEmployeeInfo(id);
            if (employee == null) {
                return new ResponseEntity<>("Employee not found", HttpStatus.NOT_FOUND);
            }
            try {
                String employeeInfo = objectMapper.writeValueAsString(employee);
                String modifiedResponse = modifyResponseForSingleEmployee(employeeInfo, employee);
                return new ResponseEntity<>(modifiedResponse, HttpStatus.OK);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
                return new ResponseEntity<>("Error processing JSON", HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } else {
            List<Employee> employeesInfo = employeesManager.getEmployeesInfo();
            String modifiedResponse = modifyResponseForEmployeesList("", employeesInfo);
            if (modifiedResponse != null) {
                return new ResponseEntity<>(modifiedResponse, HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Error processing JSON", HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
    }

}