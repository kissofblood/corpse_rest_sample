package com.corpse.repository;

import org.springframework.data.repository.CrudRepository;

import com.corpse.model.StorageHistory;

public interface StorageHistoryRepository extends CrudRepository<StorageHistory, Long> {

}
