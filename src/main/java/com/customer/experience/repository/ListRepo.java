package com.customer.experience.repository;

import com.customer.experience.model.ListModel;

import java.util.List;

public interface ListRepo {
    List<ListModel> getAllLists(int userId);
}
