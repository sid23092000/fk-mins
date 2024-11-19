package com.customer.experience.service.impl;

import com.customer.experience.model.Items;
import com.customer.experience.model.Lists;
import com.customer.experience.repository.ItemsRepository;
import com.customer.experience.repository.ListRepository;
import com.customer.experience.service.ListService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class ListServiceImpl implements ListService {

    @Autowired
    ListRepository listRepository;

    @Autowired
    ItemsRepository itemsRepository;


    @Override
    public void createList(String name, String desc, int userId) {
        log.info("[createList] creating list for the user = {}", userId);
        Lists lists = new Lists();
        lists.setName(name);
        lists.setDesc(desc);
        lists.setUserId(userId);
        listRepository.save(lists);
    }

    @Override
    public void deleteListsByIds(int userId, List<Integer> listIds) {
        try {
            listRepository.deleteByIdInAndUserId(listIds, userId);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void mergeLists(String name, String desc, List<Integer> ids, int userId) {
        try {
            // Step 1: Retrieve the original lists based on the provided IDs
            List<Lists> lists = listRepository.findAllById(ids);

            if (lists.isEmpty()) {
                throw new RuntimeException("No lists found with the provided IDs.");
            }

            // Step 2: Create a new merged list with the provided name, description, and userId
            Lists newList = new Lists();
            newList.setName(name);
            newList.setDesc(desc);
            newList.setUserId(userId);

            // Save the new merged list
            Lists listAdded = listRepository.save(newList);

            // Retrieve all items associated with the original lists using the same IDs
            List<Items> items = itemsRepository.findAllById(ids);

            // Update the listId for all the items to point to the new merged list
            int newListId = listAdded.getId();  // Get the ID of the newly saved list

            for (Items item : items) {
                item.setListId(newListId);  // Update each item's listId to the new list's ID
            }
            itemsRepository.saveAll(items);

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error while merging lists: " + e.getMessage());
        }
    }


}
