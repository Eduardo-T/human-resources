package me.eduardo.humanresources.repository;

import me.eduardo.humanresources.domain.Employee;
import me.eduardo.humanresources.exception.ContractNotFoundException;
import me.eduardo.humanresources.exception.EmployeeNotFoundException;
import me.eduardo.humanresources.service.ContractService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.Instant;
import java.util.Date;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;

@DataJpaTest
class ContractTest {

    @Autowired
    private ContractRepository contractRepository;
    @Autowired
    private EmployeeRepository employeeRepository;
    private ContractService contractService;

    @BeforeEach
    void setUp() {
        contractService = new ContractService(contractRepository, employeeRepository);
    }

    @AfterEach
    void tearDown() throws Exception {
        contractRepository.deleteAll();
    }

    @Test
    void cancelContractDeactivationIfEmployeeDoesNotExist() {
        assertThrows(EmployeeNotFoundException.class, () -> contractService.deactivateContractByEmployeeId(Integer.MAX_VALUE));
    }

    @Test
    void cancelContractDeactivationIfEmployeeHasNoContract() {
        //given
        Employee employee = new Employee(
                true, Date.from(Instant.now()),
                "RATE0112273G7", "Eduardo",
                "Tort", Date.from(Instant.now()),
                "tortedu@gmail.com", "8116068251");
        employeeRepository.save(employee);

        //then
        assertThrows(ContractNotFoundException.class, () -> contractService.deactivateContractByEmployeeId(employee.getId()));
    }



}