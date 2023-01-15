package com.cntclst.idv5.models;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItemService {

    @Autowired
    ItemRepository itemRepository;

    public List<Item> getItemsByUserID(int owner_id){
        List<Item> itemList = itemRepository.findByItemOwner_Id(owner_id);
        //Changing itemOwner part in the item to null to not to send the users password
        itemList.forEach(item -> item.setItemOwner(null));
        return itemList;
    }

    public void addItem(Item item){
        itemRepository.save(item);
    }

    public void updateItemQuantityById(int quantity, int id){
        itemRepository.updateItemQuantityById(quantity,id);
    }

    public void updateAllItemData(String name, String desc, int quan, int id){

        itemRepository.updateItemNameAndItemDescriptionAndItemQuantityById(name,desc,quan,id);
    }

    public void deleteItem(Integer id){
        itemRepository.deleteById(id);
    }
}
