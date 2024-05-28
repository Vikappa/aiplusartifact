package com.aiplus.aiplus.entities.stockentities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "brand_tonica")
@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class BrandTonica {

    @Id
    @Column(updatable = false, nullable = false)
    private String name;

    private String description;

    @JsonBackReference
    @OneToMany(mappedBy = "brandTonica", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Tonica> toniche;

}
