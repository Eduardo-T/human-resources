package me.eduardo.humanresources.repository;

import me.eduardo.humanresources.domain.Contract;
import me.eduardo.humanresources.domain.Employee;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@Repository
public interface ContractRepository extends CrudRepository<Contract, Integer> {

    Set<Contract> findByEmployee(Employee employee);

    Optional<Contract> findByEmployeeId(Integer id);

    boolean existsByEmployeeId(Integer id);

}
