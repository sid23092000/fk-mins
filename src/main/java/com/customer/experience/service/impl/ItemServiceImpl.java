package com.customer.experience.service.impl;


import com.customer.experience.model.Items;
import com.customer.experience.repository.ItemRepository;
import com.customer.experience.service.ItemService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class ItemServiceImpl implements ItemService {
    @Autowired
    ItemRepository itemRepository;
    @Override
    public void addAllItemByNameAndQuantity(List<Items> items) {
        try{
            itemRepository.saveAll(items);
        } catch (Exception e) {
            log.error(e.getMessage());
            throw e;
        }
    }
    @Override
    @Transactional
    public void deleteItemsByIdsAndListId(int userId, List<Integer> itemIds, int listId) throws Exception {
        try {
            if(itemIds == null || itemIds.isEmpty()){
                throw new Exception("Item list is empty");
            }
            Optional<List<Items>> itemsOptional = itemRepository.findByListId(listId);
            if(itemsOptional.isPresent()){
                log.info("Item list is not empty");
                itemRepository.deleteAllByIdInAndListId(itemIds, listId);
            }else{
                throw new Exception("Item list is empty 1");
            }


        } catch (Exception e) {
            log.error(e.getMessage());
            throw e;
        }
    }
}
