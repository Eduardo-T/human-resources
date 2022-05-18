package me.eduardo.humanresources.domain;

import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.time.Instant;
import java.util.Date;
import java.util.Objects;

@Table(name = "employee")
@Entity
public class Employee extends AbstractBaseEntity<Integer> {

    @OneToOne
    private Contract contract;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Integer id;
    @Column(length = 13, nullable = false)
    private String taxIdNumber;
    @Column(length = 60, nullable = false)
    private String name;
    @Column(length = 120, nullable = false)
    private String lastName;
    @Column(nullable = false)
    private Date birthDate;
    @Email
    @Column(length = 60, nullable = false)
    private String email;
    @Column(length = 20, nullable = false)
    private String cellPhone;
    public Employee(@NonNull boolean isActive, @NonNull Date dateCreated, @NonNull String taxIdNumber, @NonNull String name, String lastName, Date birthDate, String email, String cellPhone) {
        super(isActive, dateCreated);
        this.taxIdNumber = taxIdNumber;
        this.name = name;
        this.lastName = lastName;
        this.birthDate = birthDate;
        this.email = email;
        this.cellPhone = cellPhone;
    }

    public Employee() {
        super();
    }

    public boolean hasExpired() {
        return contract != null && Date.from(Instant.now()).after(contract.getDateTo());
    }

    @Override
    @Nullable
    public Integer getId() {
        return id;
    }

    @Override
    public void setId(@NonNull Integer id) {
        this.id = id;
    }

    @NonNull
    public String getName() {
        return name;
    }

    public void setName(@NonNull String name) {
        this.name = name;
    }

    @NonNull
    public String getLastName() {
        return lastName;
    }

    public void setLastName(@NonNull String lastName) {
        this.lastName = lastName;
    }

    @NonNull
    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(@NonNull Date birthDate) {
        this.birthDate = birthDate;
    }

    @NonNull
    public String getEmail() {
        return email;
    }

    public void setEmail(@NonNull String email) {
        this.email = email;
    }

    @NonNull
    public String getCellPhone() {
        return cellPhone;
    }

    public void setCellPhone(@NonNull String cellPhone) {
        this.cellPhone = cellPhone;
    }

    @NonNull
    public String getTaxIdNumber() {
        return taxIdNumber;
    }

    public void setTaxIdNumber(@NonNull String taxIdNumber) {
        this.taxIdNumber = taxIdNumber;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", taxIdNumber='" + taxIdNumber + '\'' +
                ", name='" + name + '\'' +
                ", lastName='" + lastName + '\'' +
                ", birthDate=" + birthDate +
                ", email='" + email + '\'' +
                ", cellPhone='" + cellPhone + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Employee employee = (Employee) o;

        if (!Objects.equals(id, employee.id)) return false;
        if (!taxIdNumber.equals(employee.taxIdNumber)) return false;
        if (!name.equals(employee.name)) return false;
        if (!lastName.equals(employee.lastName)) return false;
        if (!birthDate.equals(employee.birthDate)) return false;
        if (!email.equals(employee.email)) return false;
        return cellPhone.equals(employee.cellPhone);
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
