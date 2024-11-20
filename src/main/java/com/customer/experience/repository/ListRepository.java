package com.customer.experience.repository;

import com.customer.experience.model.Items;
import com.customer.experience.model.Lists;
import com.customer.experience.model.Lists;
import com.customer.experience.model.Lists;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

import java.util.List;
import java.util.Optional;

@Repository
public interface ListRepository extends JpaRepository<Lists, Integer> {
    @Transactional
    void deleteByIdInAndUserId(List<Integer> listIds, int userId);
    List<Lists> findAllByUserId(int userId);
}
