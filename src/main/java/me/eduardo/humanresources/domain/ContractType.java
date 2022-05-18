package me.eduardo.humanresources.domain;

import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

@Table(name = "contract_type")
@Entity
public class ContractType extends AbstractBaseEntity<Short> {

    @OneToOne
    private Contract contract;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Short id;

    @Column(length = 80, nullable = false)
    private String name;
    @Column(length = 255, nullable = true)
    private String description;


    public ContractType(@NonNull boolean isActive, @NonNull Date dateCreated, @NonNull String name, @Nullable String description) {
        super(isActive, dateCreated);
        this.name = name;
        this.description = description;
    }

    public ContractType() {
        super();
    }

    @Nullable
    @Override
    public Short getId() {
        return id;
    }

    @Override
    public void setId(@NonNull Short id) {
        this.id = id;
    }

    @NonNull
    public String getName() {
        return name;
    }

    public void setName(@NonNull String name) {
        this.name = name;
    }

    @Nullable
    public String getDescription() {
        return description;
    }

    public void setDescription(@Nullable String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "ContractType{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ContractType that = (ContractType) o;

        if (!Objects.equals(id, that.id)) return false;
        if (!name.equals(that.name)) return false;
        return Objects.equals(description, that.description);
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

}
