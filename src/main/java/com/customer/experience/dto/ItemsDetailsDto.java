package com.customer.experience.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ItemsDetailsDto {
    private int listId;
    private String name;
    private int quantity;
}
