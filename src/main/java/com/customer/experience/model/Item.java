package com.customer.experience.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "item")
public class Item {
    @Column(name = "id")
    private int id;

    @Column(name = "list_id")
    private int listId;

    @Column(name = "name")
    private String name;

    @Column(name = "quantity")
    private String quantity;
}
