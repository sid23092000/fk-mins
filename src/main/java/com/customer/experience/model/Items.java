package com.customer.experience.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import lombok.*;

import javax.persistence.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "item")
public class Items {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "list_id")
    private int listId;

    @Column(name = "name")
    private String name;

    @Column(name = "quantity")
    private int quantity;

    @Override
    public String toString() {
        return name + " " + quantity;
    }
}
