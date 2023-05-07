package com.example.demo;
import java.time.LocalDateTime;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class controller{
    private EmployeesManager employeeManager;

    public controller() {
        employeeManager = new EmployeesManager();
    }

    public ResponseEntity<String> getEmployee(@PathVariable("id") int id) {
        String employeeInfo = employeeManager.getEmployeeInfo(id);

        if (employeeInfo != null && !employeeInfo.isEmpty()) {
            return new ResponseEntity<>(employeeInfo, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Employee was not found", HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/info")
    public ResponseEntity<String> getAllEmployees() {
        String allEmployeesInfo = employeeManager.getEmployeesInfo();

        if (allEmployeesInfo != null && !allEmployeesInfo.isEmpty()) {
            return new ResponseEntity<>(allEmployeesInfo, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Employees are not exist", HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/entrance{id}")
    public ResponseEntity<Void> updateEntranceTime(@PathVariable("id") int id, @RequestBody LocalDateTime entranceTime) {
    Employee employee = employeeManager.getEmployee(id);

    if (employee != null) {
        employeeManager.setEntranceTime(entranceTime, id);
        return new ResponseEntity<>(HttpStatus.OK);
    } else {
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}

    @PutMapping("/exit{id}")
    public ResponseEntity<Void> updateExitTime(@PathVariable("id") int id, @RequestBody LocalDateTime exitTime) {
        Employee employee = employeeManager.getEmployee(id);

        if (employee != null) {
            employeeManager.setExitTime(exitTime, id);
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}

