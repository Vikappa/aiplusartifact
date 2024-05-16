package com.aiplus.aiplus.entities.stockentities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "brand_tonica")
@Entity
public class BrandTonica {

    @Id
    @Column(updatable = false, nullable = false)
    private String name;
    private String description;

}
