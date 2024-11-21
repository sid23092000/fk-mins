package com.customer.experience.service;

import com.customer.experience.dto.ListTextDto;
import com.customer.experience.model.ListText;

public interface ListTextService {

    void saveListText(int userId, int listId, String listTextDto);

    ListText fetchListText(int userId, int listId);
}
