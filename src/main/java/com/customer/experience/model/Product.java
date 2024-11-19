package com.customer.experience.model;

import javax.persistence.*;

@Entity
@Table(name = "product")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "desc", insertable = false, updatable = false)
    private String desc;

    @Column(name = "rating")
    private int rating;

    @Column(name = "desc")
    private String brand;

    @Column(name = "qt_available")
    private String qtAvailable;
}
