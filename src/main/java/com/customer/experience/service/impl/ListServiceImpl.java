package com.customer.experience.service.impl;

import com.customer.experience.model.Lists;
import com.customer.experience.repository.ListRepository;
import com.customer.experience.service.ListService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ListServiceImpl implements ListService {

    @Autowired
    ListRepository listRepository;

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
    public boolean deleteListsByIds(java.util.List<Integer> listIds) {
        try {
            for (Integer id : listIds) {
                listRepository.deleteById(id);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean mergeLists(java.util.List<Integer> ids) {
        return false;
    }

}
