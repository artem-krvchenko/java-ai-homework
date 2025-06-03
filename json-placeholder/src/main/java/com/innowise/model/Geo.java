package com.innowise.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "geo")
@NoArgsConstructor
@AllArgsConstructor
public class Geo {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "geo_seq")
    @SequenceGenerator(name = "geo_seq", sequenceName = "seq_geo_id", allocationSize = 1)
    @Column(name = "id")
    private Long id;

    @Column(name = "lat", nullable = false)
    private String lat;

    @Column(name = "lng", nullable = false)
    private String lng;
} 