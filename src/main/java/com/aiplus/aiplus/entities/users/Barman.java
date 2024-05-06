package com.aiplus.aiplus.entities.users;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("BARMAN")
public class Barman extends User {
    // Fields and methods specific to Barman
}
