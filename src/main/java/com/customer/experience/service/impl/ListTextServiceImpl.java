package com.customer.experience.service.impl;

import com.customer.experience.dto.ListTextDto;
import com.customer.experience.model.ListText;
import com.customer.experience.model.Lists;
import com.customer.experience.repository.ListRepository;
import com.customer.experience.repository.ListTextRepository;
import com.customer.experience.service.ListTextService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ListTextServiceImpl implements ListTextService {

    @Autowired
    ListTextRepository listTextRepository;

    @Autowired
    ListRepository listRepository;

    @Override
    public void saveListText(int userId, int listId, String listTextDto) {

            ListText listText = new ListText();

            Optional<ListText> listTextOptional = listTextRepository.findById(listId);
            if(listTextOptional.isPresent()) {
                listTextRepository.deleteById(listId);
            }

            listText.setListId(listId);
            listText.setText(listTextDto);
            listTextRepository.save(listText);

    }

    @Override
    public ListText fetchListText(int userId, int listId) {
        return listTextRepository.findByListId(listId);

    }
}
