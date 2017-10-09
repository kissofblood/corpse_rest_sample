package com.corpse.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.corpse.model.Collection;
import com.corpse.repository.CollectionRepository;

@Service
@Transactional
public class CollectionService extends ServiceIdName<Collection> {

	@Autowired
	public void setCollectionRepository(CollectionRepository collectionRepository) {
		repository = collectionRepository;
	}
}
