package com.customer.experience.controller;

import com.customer.experience.dto.ListTextDto;
import com.customer.experience.model.ListText;
import com.customer.experience.service.ListTextService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/list")
public class ListTextController {

    @Autowired
    private ListTextService listTextService;


    @PostMapping(value = "/text", consumes = "text/plain")
    public ResponseEntity<String> fetchProducts(@RequestBody String text,
                                                   @RequestHeader(value = "userId") int userId,
                                                @RequestParam(value = "listId") int listId) {
        try {
            System.out.println(text);
            listTextService.saveListText(userId, listId, text);
            return new ResponseEntity<>("List text saved successfully", HttpStatus.OK);
        } catch (Exception e) {
            System.out.println("Error in fetching the actual products");
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{listId}/text")
    public ResponseEntity<ListText> fetchProducts(@PathVariable(value = "listId") int listId,
                                                  @RequestHeader(value = "userId") int userId) {
        try {
            ListText listText = listTextService.fetchListText(userId, listId);
            return new ResponseEntity<>(listText, HttpStatus.OK);
        } catch (Exception e) {
            System.out.println("Error in fetching the actual products");
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
