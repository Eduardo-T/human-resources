package me.eduardo.humanresources.controller;

import me.eduardo.humanresources.domain.Contract;
import me.eduardo.humanresources.domain.Employee;
import me.eduardo.humanresources.service.ContractService;
import me.eduardo.humanresources.service.EmployeeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@RestController
public class EmployeeController {

    private final EmployeeService EMPLOYEE_SERVICE;
    private final ContractService CONTRACT_SERVICE;

    public EmployeeController(EmployeeService employeeService, ContractService contractService) {
        this.EMPLOYEE_SERVICE = employeeService;
        this.CONTRACT_SERVICE = contractService;
    }

    @GetMapping("/employees")
    @ResponseBody
    public Iterable<Map<String, Object>> getActiveEmployees() {
        return StreamSupport.stream(EMPLOYEE_SERVICE.getAllActive().spliterator(), false).map(this::apply).collect(Collectors.toSet());
    }

    @PostMapping("/employee")
    @ResponseBody
    public ResponseEntity<Employee> newEmployee(@RequestBody Employee employee) throws URISyntaxException {
        Employee newEmployee = EMPLOYEE_SERVICE.saveEmployee(employee);
        URI location = URI.create("/employee/" + newEmployee.getId());
        return ResponseEntity.created(location).body(newEmployee);
    }

    @PutMapping("/employee/{id}")
    @ResponseBody
    public ResponseEntity<Employee> updateEmployee(@PathVariable Integer id, @RequestBody Employee employee) {
        Employee newEmployee = EMPLOYEE_SERVICE.updateEmployeeById(id, employee);
        return ResponseEntity.created(URI.create("/employee/" + newEmployee.getId())).body(newEmployee);
    }

    private Map<String, Object> apply(Employee e) {
        Map<String, Object> node = new HashMap<>();
        node.put("name", e.getName() + " " + e.getLastName());
        node.put("taxIdNumber", e.getTaxIdNumber());
        node.put("email", e.getEmail());

        Optional<Contract> contract = CONTRACT_SERVICE.getActiveContract(e);
        if (contract.isPresent()) {
            node.put("contractName", contract.get().getContractType().getName());
            node.put("contractDateFrom", contract.get().getDateFrom());
            node.put("contractDateTo", contract.get().getDateTo());
            node.put("contractSalaryPerDay", contract.get().getSalaryPerDay());
        } else {
            node.put("contractName", null);
            node.put("contractDateFrom", null);
            node.put("contractDateTo", null);
            node.put("contractSalaryPerDay", null);
        }
        node.put("isActive", e.isActive());
        node.put("dateCreated", e.getDateCreated());

        return node;
    }
}
