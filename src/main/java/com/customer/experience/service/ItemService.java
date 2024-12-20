package com.customer.experience.service;

import com.customer.experience.model.Items;

import java.util.List;

public interface ItemService {
    void addAllItemByNameAndQuantity(List<Items> items, int listId) throws Exception;
    void deleteItemsByIdsAndListId(int userId, List<Integer> listIds, int listId) throws Exception;
}
