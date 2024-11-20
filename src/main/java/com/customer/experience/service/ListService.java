package com.customer.experience.service;

import com.customer.experience.dto.ListItemsDetailsDto;
import com.customer.experience.dto.ListsDescDto;

import java.util.List;

public interface ListService {
    void createList(String name, String desc, int userId);
    List<ListsDescDto> fetchLists(int userId);

    ListItemsDetailsDto fetchListItems(int listId);
}
