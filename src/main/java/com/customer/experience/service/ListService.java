package com.customer.experience.service;

import java.util.List;

public interface ListService {
    void createList(String name, String desc, int userId);
    boolean mergeLists(List<Integer> ids);
    boolean deleteListsByIds(List<Integer> listIds);
}
