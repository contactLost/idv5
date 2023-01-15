package com.cntclst.idv5.models;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;


public interface ItemRepository extends JpaRepository<Item, Integer> {
    @Transactional
    @Modifying
    @Query("update Item i set i.itemQuantity = ?1 where i.id = ?2")
    int updateItemQuantityById(Integer itemQuantity, Integer id);

    @Transactional
    @Modifying
    @Query("update Item i set i.itemName = ?1, i.itemDescription = ?2, i.itemQuantity = ?3 where i.id = ?4")
    int updateItemNameAndItemDescriptionAndItemQuantityById(String itemName, String itemDescription, Integer itemQuantity, Integer id);

    List<Item> findByItemOwner_Id(Integer id);

    @Override
    void deleteById(Integer integer);
}