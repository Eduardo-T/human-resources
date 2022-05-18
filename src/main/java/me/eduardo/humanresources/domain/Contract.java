package me.eduardo.humanresources.domain;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import me.eduardo.humanresources.deserializer.ContractDeserializer;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

@Table(name = "contract")
@Entity
@JsonDeserialize(using = ContractDeserializer.class)
public class Contract extends AbstractBaseEntity<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(unique = true, nullable = false)
    private Long id;

    @OneToOne(cascade = CascadeType.REFRESH)
    @JoinColumn(name = "employee_id", referencedColumnName = "id")
    private Employee employee;

    @OneToOne(cascade = CascadeType.REFRESH)
    @JoinColumn(name = "contract_type_id", referencedColumnName = "id")
    private ContractType contractType;
    @Column(nullable = false)
    private Date dateFrom;
    @Column(nullable = false)
    private Date dateTo;
    @Column(nullable = false)
    private Double salaryPerDay;

    public Contract(@NonNull boolean isActive, @NonNull Date dateCreated, @NonNull Long id, @NonNull Date dateFrom, @NonNull Date dateTo, @NonNull Double salaryPerDay) {
        super(isActive, dateCreated);
        this.id = id;
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
        this.salaryPerDay = salaryPerDay;
    }

    public Contract() {
        super();
    }

    @Override
    @Nullable
    public Long getId() {
        return id;
    }

    @Override
    public void setId(@NonNull Long id) {
        this.id = id;
    }

    @NonNull
    public Date getDateFrom() {
        return dateFrom;
    }

    public void setDateFrom(@NonNull Date dateFrom) {
        this.dateFrom = dateFrom;
    }

    @NonNull
    public Date getDateTo() {
        return dateTo;
    }

    public void setDateTo(@NonNull Date dateTo) {
        this.dateTo = dateTo;
    }

    public Double getSalaryPerDay() {
        return salaryPerDay;
    }

    public void setSalaryPerDay(@NonNull Double salaryPerDay) {
        this.salaryPerDay = salaryPerDay;
    }

    @NonNull
    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(@NonNull Employee employee) {
        this.employee = employee;
    }

    @NonNull
    public ContractType getContractType() {
        return contractType;
    }

    public void setContractType(@NonNull ContractType contractType) {
        this.contractType = contractType;
    }

    @Override
    public String toString() {
        return "Contract{" +
                "id=" + id +
                ", employee=" + employee +
                ", contractType=" + contractType +
                ", dateFrom=" + dateFrom +
                ", dateTo=" + dateTo +
                ", salaryPerDay=" + salaryPerDay +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Contract contract = (Contract) o;

        if (!Objects.equals(id, contract.id)) return false;
        if (!employee.equals(contract.employee)) return false;
        if (!contractType.equals(contract.contractType)) return false;
        if (!dateFrom.equals(contract.dateFrom)) return false;
        if (!dateTo.equals(contract.dateTo)) return false;
        return salaryPerDay.equals(contract.salaryPerDay);
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
