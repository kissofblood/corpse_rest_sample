package com.corpse.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.corpse.model.Item;
import com.corpse.repository.ItemRepository;

@Service
@Transactional
public class ItemService extends ServiceIdName<Item> {

	@Autowired
	public void setItemRepository(ItemRepository itemRepository) {
		repository = itemRepository;
	}
}
