package com.customer.experience.repository;

import com.customer.experience.model.ListModel;
import com.customer.experience.model.Tutorial;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ListRepoImpl implements ListRepo {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<ListModel> getAllLists(int userId) {
        String sql = "SELECT * FROM list WHERE user_id = ?";
        return jdbcTemplate.query(sql, new Object[]{userId}, BeanPropertyRowMapper.newInstance(ListModel.class));
    }

}
