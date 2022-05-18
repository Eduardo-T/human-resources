package me.eduardo.humanresources.service;

import me.eduardo.humanresources.domain.Employee;
import me.eduardo.humanresources.exception.EmployeeAlreadyExists;
import me.eduardo.humanresources.exception.EmployeeNotFoundException;
import me.eduardo.humanresources.repository.EmployeeRepository;
import org.springframework.stereotype.Service;

import java.util.regex.Pattern;

@Service
public class EmployeeService {

    private final static Pattern PATTERN = Pattern.compile("^([A-ZÑ\\x26]{3,4}([0-9]{2})(0[1-9]|1[0-2])(0[1-9]|1[0-9]|2[0-9]|3[0-1]))((-)?([A-Z\\d]{3}))?$");

    private final EmployeeRepository EMPLOYEE_REPOSITORY;

    public EmployeeService(EmployeeRepository employeeRepository) {
        this.EMPLOYEE_REPOSITORY = employeeRepository;
    }

    public Iterable<Employee> getAllActive() {
        return EMPLOYEE_REPOSITORY.findAllByIsActive_IsTrue();
    }

    public Employee saveEmployee(Employee employee) {
        checkRfc(employee.getTaxIdNumber());

        return EMPLOYEE_REPOSITORY.save(employee);
    }

    public Employee updateEmployeeById(Integer id, Employee newEmployee) throws EmployeeNotFoundException {
        Employee previous = EMPLOYEE_REPOSITORY.findById(id).orElseThrow(() -> new EmployeeNotFoundException(id));

        if (!newEmployee.getTaxIdNumber().equals(previous.getTaxIdNumber()))
            checkRfc(newEmployee.getTaxIdNumber());

        previous.setName(newEmployee.getName());
        previous.setLastName(newEmployee.getLastName());
        previous.setBirthDate(newEmployee.getBirthDate());
        previous.setEmail(newEmployee.getEmail());
        previous.setCellPhone(newEmployee.getCellPhone());
        previous.setTaxIdNumber(newEmployee.getTaxIdNumber());

        return EMPLOYEE_REPOSITORY.save(previous);
    }

    public void checkRfc(String taxIdNumber) throws IllegalArgumentException, IllegalStateException {
        if (taxIdNumber == null)
            throw new NullPointerException("The taxIdNumber is null");

        if (!PATTERN.matcher(taxIdNumber.trim().toUpperCase()).matches())
            throw new IllegalArgumentException(taxIdNumber + " is not a valid RFC");

        if (EMPLOYEE_REPOSITORY.existsByTaxIdNumber(taxIdNumber))
            throw new EmployeeAlreadyExists("An employee with the given taxIdNumber already exists!");
    }

}
