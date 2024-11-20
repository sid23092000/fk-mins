package com.customer.experience.repository;

import com.customer.experience.model.Items;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ItemRepository extends JpaRepository<Items, Integer> {
    List<Items> findAllByListId(int id);
    void deleteAllByIdInAndListId(List<Integer> itemIds, int listId);
    Optional<List<Items>> findByListId(int listId);
}
