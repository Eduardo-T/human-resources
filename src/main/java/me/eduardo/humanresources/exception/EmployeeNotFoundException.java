package me.eduardo.humanresources.exception;

public class EmployeeNotFoundException extends RuntimeException {

    public EmployeeNotFoundException(Integer employeeId) {
        super("The employee " + employeeId + " was not found!");
    }

    public EmployeeNotFoundException() {
        super("The given employee was not found!");
    }

    public EmployeeNotFoundException(String message) {
        super(message);
    }

}
