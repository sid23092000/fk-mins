package com.customer.experience.service.impl;

import com.customer.experience.dto.ItemsDetailsDto;
import com.customer.experience.dto.ListItemsDetailsDto;
import com.customer.experience.dto.ListsDescDto;
import com.customer.experience.model.Items;
import com.customer.experience.model.Lists;
import com.customer.experience.repository.ItemRepository;
import com.customer.experience.repository.ListRepository;
import com.customer.experience.service.ListService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ListServiceImpl implements ListService {

    @Autowired
    ListRepository listRepository;
    @Autowired
    ItemRepository itemRepository;

    private static final Logger log = LoggerFactory.getLogger(ListServiceImpl.class);

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
                return null;
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
