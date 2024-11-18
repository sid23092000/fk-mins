package com.customer.experience.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "product")
public class Product {
    @Column(name = "id")
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "desc")
    private String desc;

    @Column(name = "rating")
    private int rating;

    @Column(name = "desc")
    private String brand;

    @Column(name = "qt_available")
    private String qtAvailable;
}
