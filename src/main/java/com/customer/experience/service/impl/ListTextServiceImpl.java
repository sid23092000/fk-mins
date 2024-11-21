package com.customer.experience.service.impl;

import com.customer.experience.Utils.VernacWrapper;
import com.customer.experience.dto.ListTextDto;
import com.customer.experience.model.Items;
import com.customer.experience.model.ListText;
import com.customer.experience.model.Lists;
import com.customer.experience.repository.ListRepository;
import com.customer.experience.repository.ListTextRepository;
import com.customer.experience.service.ItemService;
import com.customer.experience.service.ListTextService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import java.util.Optional;

@Service
public class ListTextServiceImpl implements ListTextService {

    @Autowired
    ListTextRepository listTextRepository;

    @Autowired
    ListRepository listRepository;

    @Autowired
    ItemService itemService;

    @Override
    public void saveListText(int userId, int listId, String listTextDto) {

        try {
            ListText listText = new ListText();

            ListText listTextOptional = listTextRepository.findByListId(listId);
            if(listTextOptional != null) {
                listTextRepository.deleteAllByListId(listId);
            }

            listText.setListId(listId);
            listText.setText(listTextDto);
            System.out.println("debug2");
            listTextRepository.save(listText);
            System.out.println("debug3");
            VernacWrapper vernacWrapper = new VernacWrapper();
            List<Items> items = vernacWrapper.getListFromText(userId, listId, listTextDto);

            itemService.addAllItemByNameAndQuantity(items, listId);
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public ListText fetchListText(int userId, int listId) {
        return listTextRepository.findByListId(listId);

    }
}
