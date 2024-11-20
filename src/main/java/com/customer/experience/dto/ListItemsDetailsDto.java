package com.customer.experience.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ListItemsDetailsDto {
    private int id;
    private ArrayList<ItemsDetailsDto> items;
}
