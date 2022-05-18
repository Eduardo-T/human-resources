package me.eduardo.humanresources.exception;

import me.eduardo.humanresources.domain.ExceptionMessage;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ExceptionAdvice {

    @ResponseBody
    @ExceptionHandler(ContractNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    ExceptionMessage contractNotFoundHandler(ContractNotFoundException ex) {
        return new ExceptionMessage(ex.getMessage());
    }

    @ResponseBody
    @ExceptionHandler(EmployeeNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    ExceptionMessage employeeNotFoundHandler(EmployeeNotFoundException ex) {
        return new ExceptionMessage(ex.getMessage());
    }

    @ResponseBody
    @ExceptionHandler(EmployeeAlreadyExists.class)
    @ResponseStatus(HttpStatus.PRECONDITION_FAILED)
    ExceptionMessage employeeAlreadyExistsHandler(EmployeeAlreadyExists ex) {
        return new ExceptionMessage(ex.getMessage());
    }

    @ResponseBody
    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    ExceptionMessage exceptionHandler(RuntimeException ex) {
        return new ExceptionMessage(ex.getMessage());
    }

}
