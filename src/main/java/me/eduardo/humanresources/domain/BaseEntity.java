package me.eduardo.humanresources.domain;

import java.util.Date;

public interface BaseEntity {

    boolean isActive();

    Date getDateCreated();

}
