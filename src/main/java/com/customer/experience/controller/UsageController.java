package com.customer.experience.controller;


import com.customer.experience.Utils.VernacWrapper;
import com.customer.experience.model.Items;
import com.customer.experience.model.Lists;
import com.customer.experience.repository.ListRepository;
import com.customer.experience.service.ItemService;
import com.customer.experience.service.ListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/usage")
public class UsageController {


    @Autowired
    private ListService listService;

    @Autowired
    private ItemService itemService;

    @Autowired
    private ListRepository listRepository;


    @PostMapping("")
    public ResponseEntity<List<Items>> getListByUsage(@RequestHeader int userId, @RequestBody() String input) {

        try {
            VernacWrapper vernacWrapper = new VernacWrapper();
            List<Items> items = null;
            boolean success = false;
            int attempts = 0;

            Lists xyz=new Lists();
            xyz.setName(input);
            xyz.setDesc(input+" based usage");
            xyz.setUserId(userId);
            Lists lists = listRepository.save(xyz);
            int curr_listId= lists.getId();
            System.out.println(curr_listId);
            while (!success && attempts < 10) {
                try {

                    items = vernacWrapper.generateUsageBasedList(input, curr_listId);
                    itemService.addAllItemByNameAndQuantity(items, curr_listId);
                    success = true;
                } catch (Exception e) {
                    attempts++;
                    if (attempts >= 10) {
                        System.out.println("Error in parsing the usage after 3 attempts");
                        return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
                    }
                }
            }
            System.out.println(items);
            return new ResponseEntity<>(items, HttpStatus.OK);
        } catch (Exception e) {
            System.out.println("Unexpected error");
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }



}
