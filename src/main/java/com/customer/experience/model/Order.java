package com.customer.experience.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name = "order")
public class Order {
    @Column(name = "id")
    private int id;

    @Column(name = "user_id")
    private int userId;

    @Column(name = "prod_id")
    private String prodId;

    @Column(name = "prod_name")
    private String prodName;

    @Column(name = "order_date")
    private LocalDateTime orderDate;
}
