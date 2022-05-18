package me.eduardo.humanresources.controller;

import me.eduardo.humanresources.domain.Contract;
import me.eduardo.humanresources.service.ContractService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class ContractController {

    private final ContractService CONTRACT_SERVICE;

    public ContractController(ContractService contractService) {
        this.CONTRACT_SERVICE = contractService;
    }

    @PostMapping(path = "/contract")
    @ResponseBody
    public ResponseEntity<Contract> newContract(@RequestBody Contract contract) {
        Contract newContract = CONTRACT_SERVICE.createContract(contract);
        return ResponseEntity.status(HttpStatus.CREATED).body(newContract);
    }

    @DeleteMapping("/contract/{employeeId}")
    @ResponseBody
    public ResponseEntity deactivateContractByEmployeeId(@PathVariable Integer employeeId) {
        CONTRACT_SERVICE.deactivateContractByEmployeeId(employeeId);
        return ResponseEntity.ok().build();
    }

}
