package com.aiplus.aiplus.entities.stockentities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class BrandTonica {

    @Id
    @Column(updatable = false, nullable = false)
    private String name;

    private String imgUrl;
    private String description;

}
