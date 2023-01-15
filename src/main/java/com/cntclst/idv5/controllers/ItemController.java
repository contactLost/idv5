package com.cntclst.idv5.controllers;

import com.cntclst.idv5.models.*;
import com.cntclst.idv5.requests.ItemAddRequest;
import com.cntclst.idv5.requests.ItemQuantityUpdateRequest;
import com.cntclst.idv5.requests.ItemResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping
@CrossOrigin
public class ItemController {

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Autowired
    private ItemService itemService;

    //Tested Working
    @GetMapping("/item/getItemsOfUser")
    public ResponseEntity<?> getUserInfo(Principal user){

        MyUserDetails userObj = (MyUserDetails) userDetailsService.loadUserByUsername(user.getName());
        int userID = userObj.getID();

        List<Item> itemList = itemService.getItemsByUserID(userID);

        ItemResponse itemResponse = new ItemResponse();
        itemResponse.setItemList(itemList);

        return ResponseEntity.ok(itemResponse);
    }

    //Tested working
    @PostMapping("/item/addItem")
    public ResponseEntity<?> addItem(Principal user, @RequestBody ItemAddRequest itemAddRequest){

        MyUserDetails userObj = (MyUserDetails) userDetailsService.loadUserByUsername(user.getName());

        User user1 = new User();
        user1.setId(userObj.getID());
        Item newItem = new Item();

        newItem.setItemDescription(itemAddRequest.getItemDesc());
        newItem.setItemName(itemAddRequest.getItemName());
        newItem.setItemQuantity(itemAddRequest.getItemQuan());
        newItem.setItemOwner(user1);

        itemService.addItem(newItem);

        return ResponseEntity.ok("Successful");
    }
    //Tested working
    //Just need id and quantity
    @PostMapping("/item/updateQuantity")
    public ResponseEntity<?> updateQuantity(Principal user, @RequestBody ItemQuantityUpdateRequest itemQuantityUpdateRequest){

        itemService.updateItemQuantityById( itemQuantityUpdateRequest.getItemQuan(),itemQuantityUpdateRequest.getItemid() );
        return ResponseEntity.ok("Successful");
    }


    //Tested Working
    @PostMapping("/item/updateItem")
    public ResponseEntity<?> updateItem(Principal user, @RequestBody ItemQuantityUpdateRequest itemQuantityUpdateRequest){

        itemService.updateAllItemData(
                itemQuantityUpdateRequest.getItemName(),
                itemQuantityUpdateRequest.getItemDesc(),
                itemQuantityUpdateRequest.getItemQuan(),
                itemQuantityUpdateRequest.getItemid());
        return ResponseEntity.ok("Successful");
    }

    //Tested Working
    //Just needs id to be right
    @PostMapping("/item/deleteItem")
    public ResponseEntity<?> deleteItem(Principal user, @RequestBody ItemQuantityUpdateRequest itemQuantityUpdateRequest){

        itemService.deleteItem(itemQuantityUpdateRequest.getItemid());
        return ResponseEntity.ok("Successful");
    }
}
