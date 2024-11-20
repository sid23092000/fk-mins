package com.customer.experience.service.impl;


import com.customer.experience.model.Items;
import com.customer.experience.repository.ItemRepository;
import com.customer.experience.service.ItemService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItemServiceImpl implements ItemService {
    @Autowired
    ItemRepository itemRepository;
    private static final Logger log = LoggerFactory.getLogger(ItemServiceImpl.class);
    @Override
    public void addAllItemByNameAndQuantity(List<Items> items) {
        try{
            itemRepository.saveAll(items);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }
    @Override
    public void deleteListsByIds(int userId, List<Integer> listIds) {
        try {
            itemRepository.deleteAllById(listIds);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }
}
