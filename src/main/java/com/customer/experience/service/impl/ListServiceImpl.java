package com.customer.experience.service.impl;

import com.customer.experience.dto.ItemsDetailsDto;
import com.customer.experience.dto.ListItemsDetailsDto;
import com.customer.experience.dto.ListsDescDto;
import com.customer.experience.model.Items;
import com.customer.experience.model.Lists;
import com.customer.experience.repository.ItemRepository;
import com.customer.experience.model.Users;
import com.customer.experience.model.Lists;
import com.customer.experience.repository.ListRepository;
import com.customer.experience.repository.UserRepository;
import com.customer.experience.service.ListService;
import com.customer.experience.service.ListTextService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ListServiceImpl implements ListService {

    @Autowired
    ListRepository listRepository;

    @Autowired
    ItemRepository itemRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    ListTextService listTextService;


    @Override
    public Integer createList(String name, String desc, int userId) {
        try {
            log.info("[createList] creating list for the user = {}", userId);
            Lists newList = new Lists();
            newList.setName(name);
            newList.setDesc(desc);
            newList.setUserId(userId);
            Lists lists = listRepository.save(newList);
            listTextService.saveListText(userId, lists.getId(), "");
            return lists.getId();

        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public void deleteListsByIds(int userId, List<Integer> listIds) throws Exception {

        if(listIds == null || listIds.isEmpty()) {
            throw new Exception("List ids not found");
        }

        Optional<Users> userOptional = userRepository.findById(userId);
        if(userOptional.isPresent()) {
            List<Items> itemsForList = itemRepository.findAllByListIdIn(listIds);
            List<Integer> itemIds = itemsForList.stream()
                    .map(Items::getId)
                    .collect(Collectors.toList());
            itemRepository.deleteAllById(itemIds);
            listRepository.deleteByIdInAndUserId(listIds, userId);
        } else {
            throw new Exception("User not found");
        }
    }

    @Override
    public void mergeLists(String name, String desc, List<Integer> ids, int userId) throws Exception {
        try {

            if(ids.isEmpty() || ids.size() == 1) {
                throw new Exception("Either single list is passed or list is empty");
            }

            List<Lists> lists = listRepository.findAllById(ids);

            if (lists.isEmpty()) {
                throw new Exception("No lists found with the provided IDs.");
            }

            Lists newList = new Lists();
            newList.setName(name);
            newList.setDesc(desc);
            newList.setUserId(userId);

            Lists listAdded = listRepository.save(newList);

            List<Items> items = itemRepository.findAllByListIdIn(ids);

            List<Items> newCombinedItemsList = getItems(items, listAdded);
            itemRepository.saveAll(newCombinedItemsList);
            AtomicInteger counter = new AtomicInteger(1);

            String formattedText = newCombinedItemsList.stream()
                    .map(item -> counter.getAndIncrement() + ". " + item.getName() + " " + item.getQuantity())
                    .collect(Collectors.joining("\n"));
            listTextService.saveListText(userId, listAdded.getId(), formattedText);

        } catch (Exception e) {
            throw new Exception("Error while merging lists: " + e.getMessage());
        }
    }

    private static List<Items> getItems(List<Items> items, Lists listAdded) throws Exception {
        if(items.isEmpty()) {
            throw new Exception("No items found to merge list");
        }
        // Update the listId for all the items to point to the new merged list
        int newListId = listAdded.getId();  // Get the ID of the newly saved list

        List<Items> newCombinedItemsList = new ArrayList<>();
        for (Items item : items) {
            Items newItem = new Items();
            newItem.setListId(newListId);  // Update each item's listId to the new list's ID
            newItem.setName(item.getName());
            newItem.setQuantity(item.getQuantity());
            newCombinedItemsList.add(newItem);
        }
        return newCombinedItemsList;
    }

    @Override
    public List<ListsDescDto> fetchLists(int userId) {
        log.info("[fetchListModel] fetching list for the user = {}", userId);
        List<Lists> lists = listRepository.findAllByUserId(userId);
        return lists.stream().map(list -> new ListsDescDto(list.getId(), list.getName(), list.getDesc())).collect(Collectors.toList());
    }

    @Override
    public ListItemsDetailsDto fetchListItems(int listId) {
        log.info("[fetchListModel] fetching list items for the list = {}", listId);
        List<Items> listItems;
        try {
            listItems = itemRepository.findAllByListId(listId);
            if (listItems.isEmpty()) {
                return new ListItemsDetailsDto();
            }
            ListItemsDetailsDto listItemsDetailsDto = new ListItemsDetailsDto();
            listItemsDetailsDto.setId(listId);
            ArrayList<ItemsDetailsDto> itemsDetailsDtoList = new ArrayList<>();
            for (Items item : listItems) {
                ItemsDetailsDto dto = new ItemsDetailsDto();
                dto.setName(item.getName());
                dto.setQuantity(item.getQuantity());
                itemsDetailsDtoList.add(dto);
            }

            listItemsDetailsDto.setItems(itemsDetailsDtoList);
            return listItemsDetailsDto;
        } catch (Exception e) {
            log.error(e.getMessage());
            throw e;
        }
    }


}
