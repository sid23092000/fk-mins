package com.customer.experience.repository;

import com.customer.experience.model.ListText;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface ListTextRepository extends JpaRepository<ListText, Integer> {
    ListText findByListId(int listId);

    @Transactional
    void deleteAllByListId(int listId);
}
