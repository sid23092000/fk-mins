package com.customer.experience.controller;
import com.customer.experience.model.ListModel;
import com.customer.experience.model.Tutorial;
import com.customer.experience.service.ListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
public class ListController {
    @Autowired
    private ListService listService;
    @PostMapping("/create")
    public ResponseEntity<String> createList(@RequestBody Tutorial tutorial) {
        try {
//            tutorialRepository.save(new Tutorial(tutorial.getTitle(), tutorial.getDescription(), false));
            return new ResponseEntity<>("Tutorial was created successfully.", HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/lists/{userId}")
    public ResponseEntity<List<ListModel>> getLists(@PathVariable("userId") String userId){
        try {
            System.out.println("getting data for userid :" + userId);
            List<ListModel> lists = listService.getListModel(Integer.parseInt(userId));
            return new ResponseEntity<>(lists, HttpStatus.OK);
        } catch (Exception e) {
            System.out.println("getting data error for userid :" + userId);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
