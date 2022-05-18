package me.eduardo.humanresources.deserializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.util.StdDateFormat;
import me.eduardo.humanresources.domain.Contract;
import me.eduardo.humanresources.domain.ContractType;
import me.eduardo.humanresources.domain.Employee;
import me.eduardo.humanresources.repository.ContractTypeRepository;
import me.eduardo.humanresources.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.text.ParseException;
import java.util.Date;

public class ContractDeserializer extends StdDeserializer<Contract> {

    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private ContractTypeRepository contractTypeRepository;

    public ContractDeserializer(Class<?> vc) {
        super(vc);
    }

    public ContractDeserializer() {
        super((Class<?>) null);
    }

    @Override
    public Contract deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        JsonNode node = jsonParser.getCodec().readTree(jsonParser);
        int employeeId = (Integer) node.get("employeeId").numberValue();
        short contractTypeId = node.get("contractTypeId").shortValue();
        Date dateFrom = null;
        Date dateTo = null;
        try {
            dateFrom = new StdDateFormat().parse(node.get("dateFrom").textValue());
            dateTo = new StdDateFormat().parse(node.get("dateTo").textValue());
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        double salaryPerDay = node.get("salaryPerDay").doubleValue();

        Employee employee = employeeRepository.findById(employeeId).orElseThrow(NullPointerException::new);
        ContractType contractType = contractTypeRepository.findById(contractTypeId).orElseThrow(NullPointerException::new);

        Contract contract = new Contract();
        contract.setContractType(contractType);
        contract.setEmployee(employee);
        contract.setDateFrom(dateFrom);
        contract.setDateTo(dateTo);
        contract.setSalaryPerDay(salaryPerDay);

        return contract;
    }
}
