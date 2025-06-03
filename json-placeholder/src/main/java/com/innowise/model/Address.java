package com.innowise.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "address")
@NoArgsConstructor
@AllArgsConstructor
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "address_seq")
    @SequenceGenerator(name = "address_seq", sequenceName = "seq_address_id", allocationSize = 1)
    @Column(name = "id")
    private Long id;

    @Column(name = "street", nullable = false)
    private String street;

    @Column(name = "suite", nullable = false)
    private String suite;

    @Column(name = "city", nullable = false)
    private String city;

    @Column(name = "zipcode", nullable = false)
    private String zipcode;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "geo_id", nullable = false)
    private Geo geo;
} 