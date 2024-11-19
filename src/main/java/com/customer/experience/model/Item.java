package com.customer.experience.model;

import javax.persistence.*;

@Entity
@Table(name = "item")
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "list_id")
    private int listId;

    @Column(name = "name")
    private String name;

    @Column(name = "quantity")
    private String quantity;
}
