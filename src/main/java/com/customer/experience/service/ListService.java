package com.customer.experience.service;

import java.util.List;

public interface ListService {
    void createList(String name, String desc, int userId);
    void mergeLists(String name, String desc, List<Integer> ids, int userId);
    void deleteListsByIds(int userId, List<Integer> listIds);
}
