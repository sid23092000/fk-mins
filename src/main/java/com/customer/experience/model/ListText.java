package com.customer.experience.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "list_text")
public class ListText {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "list_id", nullable = false)
    private Integer listId;

    @Lob
    @Column(name = "text", nullable = false, columnDefinition = "TEXT")
    private String text;
}
