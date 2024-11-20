package com.customer.experience.controller;

import com.customer.experience.dto.ApiResponse;
import com.customer.experience.dto.ItemsDetailsDto;
import com.customer.experience.model.Items;
import com.customer.experience.service.ItemService;
import com.customer.experience.service.impl.ListServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;



@Controller
@RestController
@RequestMapping("/api/item")
public class ItemController {

    @Autowired
    private ItemService itemService;
    private static final Logger log = LoggerFactory.getLogger(ItemController.class);

    @PostMapping("/create/{list_id}")
    public ResponseEntity<ApiResponse<String>> createItem(@RequestBody List<ItemsDetailsDto> itemsDetailsDtoList, @RequestHeader int userId, @PathVariable("list_id") int listId) {

        try{
            List<Items> items = new ArrayList<>();
            for (ItemsDetailsDto itemVal : itemsDetailsDtoList) {
                Items item = new Items();
                item.setName(itemVal.getName());
                item.setQuantity(itemVal.getQuantity());
                item.setListId(listId);
                items.add(item);
            }
            itemService.addAllItemByNameAndQuantity(items);
            return new ResponseEntity<>(ApiResponse.success("Item created successfully"), HttpStatus.OK);
        } catch (Exception e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(ApiResponse.error("Failed to create item"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/delete/{list_id}")
    public ResponseEntity<ApiResponse<String>> deleteLists(@RequestBody List<Integer> ids,
                                                           @RequestHeader Integer userId,
                                                           @PathVariable("list_id") Integer listId) {
        try {
            itemService.deleteItemsByIdsAndListId(userId, ids, listId);
            return ResponseEntity.ok(ApiResponse.success("Lists deleted successfully"));
        } catch (Exception e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(ApiResponse.error("Failed to delete list"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
