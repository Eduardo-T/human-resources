package me.eduardo.humanresources.exception;

public class ContractNotFoundException extends RuntimeException {

    public ContractNotFoundException(Long contractId) {
        super("The contract " + contractId + " was not found!");
    }

    public ContractNotFoundException() {
        super("The given contract was not found!");
    }

    public ContractNotFoundException(String message) {
        super(message);
    }
}
