package com.corpse.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.corpse.model.Storage;
import com.corpse.repository.StorageRepository;

@Service
@Transactional
public class StorageService extends ServiceIdName<Storage> {

	@Autowired
	public void setStorageRepository(StorageRepository storageRepository) {
		repository = storageRepository;
	}
}
