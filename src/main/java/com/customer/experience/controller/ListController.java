package com.customer.experience.controller;

import com.customer.experience.dto.ApiResponse;
import com.customer.experience.service.ListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/list")
public class ListController {

    @Autowired
    ListService listService;

    @PostMapping("/create")
    public ResponseEntity<ApiResponse<String>> createList(@RequestParam String name, @RequestParam String desc
    , @RequestHeader int userId) {
        try {
            listService.createList(name, desc, userId);
            return new ResponseEntity<>(ApiResponse.success("List created successfully"), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(ApiResponse.error("Failed to create list"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/merge")
    public ResponseEntity<ApiResponse<String>> mergeLists(@RequestBody List<Integer> ids) {
        boolean merged = listService.mergeLists(ids);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<ApiResponse<String>> deleteLists(@RequestBody List<Integer> ids) {
        boolean deleted = listService.deleteListsByIds(ids);

        if (deleted) {
            return ResponseEntity.ok(ApiResponse.success("Lists deleted successfully"));
        } else {
            return ResponseEntity.status(400).body(ApiResponse.error("Failed to delete lists"));
        }
    }
}
