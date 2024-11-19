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
    public ResponseEntity<ApiResponse<String>> mergeLists(@RequestParam String name,
                                                          @RequestParam String desc,
                                                          @RequestBody List<Integer> ids,
                                                          @RequestHeader int userId) {
        try {
            listService.mergeLists(name, desc, ids, userId);
            return ResponseEntity.ok(ApiResponse.success("Lists merged successfully"));
        } catch (Exception e) {
            return new ResponseEntity<>(ApiResponse.error("Failed to merge lists"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/delete")
    public ResponseEntity<ApiResponse<String>> deleteLists(@RequestBody List<Integer> ids,
                                                           @RequestHeader Integer userId) {
        try {
            listService.deleteListsByIds(userId, ids);
            return ResponseEntity.ok(ApiResponse.success("Lists deleted successfully"));

        } catch (Exception e) {
            return new ResponseEntity<>(ApiResponse.error("Failed to delete list"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
