package com.aiplus.aiplus.entities.users;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("ADMIN")
public class Admin extends User {
    // Fields and methods specific to Admin
}
