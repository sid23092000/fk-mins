package com.customer.experience.service;

import com.customer.experience.dto.ListItemsDetailsDto;
import com.customer.experience.dto.ListsDescDto;

import java.util.List;

import java.util.List;

public interface ListService {
    Integer createList(String name, String desc, int userId);
    List<ListsDescDto> fetchLists(int userId);

    ListItemsDetailsDto fetchListItems(int listId);
    void mergeLists(String name, String desc, List<Integer> ids, int userId) throws Exception;
    void deleteListsByIds(int userId, List<Integer> listIds) throws Exception;
}
