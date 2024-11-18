package com.customer.experience.service;

import com.customer.experience.model.ListModel;
import com.customer.experience.repository.ListRepoImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

@Service
public class ListService {
    @Autowired
    private ListRepoImpl listRepoImpl;
    public List<ListModel> getListModel(int userId) {
        try{
            List<ListModel> listModels = new ArrayList<>();
            listModels = listRepoImpl.getAllLists(userId);
            return listModels;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
