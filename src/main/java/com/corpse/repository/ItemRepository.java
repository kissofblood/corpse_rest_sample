package com.corpse.repository;

import org.springframework.data.repository.CrudRepository;

import com.corpse.model.Item;

public interface ItemRepository extends CrudRepository<Item, Long> {

}
