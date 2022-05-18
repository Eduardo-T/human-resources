package me.eduardo.humanresources.repository;

import me.eduardo.humanresources.domain.Employee;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository extends CrudRepository<Employee, Integer> {

    Iterable<Employee> findAllByIsActive_IsTrue();

    boolean existsByTaxIdNumber(String taxIdNumber);

}
