package com.customer.experience.service.impl;

import com.customer.experience.model.List;
import com.customer.experience.repository.ListRepository;
import com.customer.experience.service.ListService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ListServiceImpl implements ListService {

    @Autowired
    ListRepository listRepository;

    private static final Logger log = LoggerFactory.getLogger(ListServiceImpl.class);

    @Override
    public void createList(String name, String desc, int userId) {
        log.info("[createList] creating list for the user = {}", userId);
        List list = new List();
        list.setName(name);
        list.setDesc(desc);
        list.setUserId(userId);
        listRepository.save(list);
    }
}
