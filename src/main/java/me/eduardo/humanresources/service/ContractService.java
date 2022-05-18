package me.eduardo.humanresources.service;

import me.eduardo.humanresources.domain.Contract;
import me.eduardo.humanresources.domain.Employee;
import me.eduardo.humanresources.exception.ContractNotFoundException;
import me.eduardo.humanresources.exception.EmployeeNotFoundException;
import me.eduardo.humanresources.repository.ContractRepository;
import me.eduardo.humanresources.repository.EmployeeRepository;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.Instant;
import java.util.Optional;
import java.util.Set;

@Service
public class ContractService {

    private final ContractRepository CONTRACT_REPOSITORY;
    private final EmployeeRepository EMPLOYEE_REPOSITORY;

    public ContractService(ContractRepository contractRepository, EmployeeRepository employeeRepository) {
        this.CONTRACT_REPOSITORY = contractRepository;
        this.EMPLOYEE_REPOSITORY = employeeRepository;
    }

    public Contract createContract(Contract contract) {
        // EXISTING CONTRACT CHECK
        getActiveContract(contract.getEmployee()).ifPresent(c -> c.setDateTo(Date.from(Instant.now())));

        return CONTRACT_REPOSITORY.save(contract);
    }

    public void deactivateContractByEmployeeId(Integer employeeId) {
        Employee employee = EMPLOYEE_REPOSITORY.findById(employeeId).orElseThrow(() -> new EmployeeNotFoundException(employeeId));
        // EXISTING ACTIVE CONTRACT CHECK
        Optional<Contract> currentContract = getActiveContract(employee);
        Set<Contract> contracts = CONTRACT_REPOSITORY.findByEmployee(employee);
        if (!containsByEmployeeId(employeeId) || !currentContract.isPresent() || contracts.isEmpty())
            throw new ContractNotFoundException("The given employee has no active contract!");

        currentContract.get().setDateTo(Date.from(Instant.now()));
    }

    public Optional<Contract> getActiveContract(Employee employee) {
        return CONTRACT_REPOSITORY.findByEmployee(employee).stream()
                .filter(c -> c.getDateTo().before(Date.from(Instant.now()))).findFirst();
    }

    public boolean containsByEmployeeId(Integer id) {
        return CONTRACT_REPOSITORY.existsByEmployeeId(id);
    }

}
