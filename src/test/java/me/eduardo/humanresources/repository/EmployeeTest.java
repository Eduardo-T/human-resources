package me.eduardo.humanresources.repository;

import me.eduardo.humanresources.domain.Employee;
import me.eduardo.humanresources.exception.EmployeeAlreadyExists;
import me.eduardo.humanresources.exception.EmployeeNotFoundException;
import me.eduardo.humanresources.service.EmployeeService;
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
class EmployeeTest {

    @Autowired
    private EmployeeRepository employeeRepository;
    private EmployeeService employeeService;

    @BeforeEach
    void setUp() {
         employeeService = new EmployeeService(employeeRepository);
    }

    @AfterEach
    void tearDown() throws Exception {
        employeeRepository.deleteAll();
    }

    @Test
    void existsByTaxIdNumber() {
        //given
        Employee employee = new Employee(
                true, Date.from(Instant.now()),
                "RATE0112273G7", "Eduardo",
                "Tort", Date.from(Instant.now()),
                "tortedu@gmail.com", "8116068251");
        employeeRepository.save(employee);

        //when
        boolean exists = employeeRepository.existsByTaxIdNumber("RATE0112273G7");

        //then
        assertThat(exists).isTrue();
    }

    @Test
    void notExistsByTaxIdNumber() {
        boolean exists = employeeRepository.existsByTaxIdNumber("RATE0112273G7");

        //then
        assertThat(exists).isFalse();
    }


    @Test
    void findAllByActive_ReturnsAllActive() {
        //given
        Employee employee = new Employee(
                true, Date.from(Instant.now()),
                "RATE0112273G7", "Eduardo",
                "Tort", Date.from(Instant.now()),
                "tortedu@gmail.com", "8116068251");
        employeeService.saveEmployee(employee);

        //when
        Iterable<Employee> activeEmployees = employeeService.getAllActive();

        //then
        activeEmployees.forEach(em -> assertThat(em).hasFieldOrPropertyWithValue("isActive", true));
    }

    @Test
    void dontAllowDuplicateTaxIdNumbers() {
        //given
        Employee employee = new Employee(
                true, Date.from(Instant.now()),
                "RATE0112273G7", "Eduardo",
                "Tort", Date.from(Instant.now()),
                "tortedu@gmail.com", "8116068251");
        Employee employee2 = new Employee(
                true, Date.from(Instant.now()),
                "RATE0112273G7", "Samia",
                "Jacobo", Date.from(Instant.now()),
                "cossy94@gmail.com", "1234567890");

        employeeService.saveEmployee(employee);

        //then
        assertThrows(EmployeeAlreadyExists.class, () -> employeeService.saveEmployee(employee2));
    }

    @Test
    void dontAllowUpdateToNonExistingEmployee() {
        //given
        Employee employee = new Employee(
                true, Date.from(Instant.now()),
                "RATE0112273G7", "Eduardo",
                "Tort", Date.from(Instant.now()),
                "tortedu@gmail.com", "8116068251");

        //then
        assertThrows(EmployeeNotFoundException.class, () -> employeeService.updateEmployeeById(5, employee));
    }

    @Test
    void dontAllowUpdateToInvalidTaxId() {
        //given
        Employee employee = new Employee(
                true, Date.from(Instant.now()),
                "RATE0112273G7", "Eduardo",
                "Tort", Date.from(Instant.now()),
                "tortedu@gmail.com", "8116068251");
        employeeService.saveEmployee(employee);

        Employee newEmployee = new Employee(
                true, Date.from(Instant.now()),
                "this is definitely not a tax id", "Eduardo",
                "Tort", Date.from(Instant.now()),
                "tortedu@gmail.com", "8116068251");

        //then
        assertThrows(IllegalArgumentException.class, () -> employeeService.updateEmployeeById(employee.getId(), newEmployee));
    }



}