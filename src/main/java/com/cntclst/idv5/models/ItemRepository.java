package com.cntclst.idv5.models;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface ItemRepository extends JpaRepository<Item, Integer> {
}