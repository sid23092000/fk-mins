package com.customer.experience.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ListsDescDto {
    private int id;
    private String name;
    private String desc;
}
