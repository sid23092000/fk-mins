package com.customer.experience.repository;

import com.customer.experience.model.ListText;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ListTextRepository extends JpaRepository<ListText, Integer> {
    ListText findByListId(int listId);
}
