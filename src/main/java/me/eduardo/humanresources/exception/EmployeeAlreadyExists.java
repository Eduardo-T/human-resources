package me.eduardo.humanresources.exception;

public class EmployeeAlreadyExists extends RuntimeException {

    public EmployeeAlreadyExists() {
        super();
    }

    public EmployeeAlreadyExists(String message) {
        super(message);
    }

    public EmployeeAlreadyExists(Integer id) {
        super("The employee " + id + " already exists!");
    }
}
