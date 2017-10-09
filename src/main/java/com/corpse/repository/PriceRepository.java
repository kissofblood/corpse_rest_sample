package com.corpse.repository;

import org.springframework.data.repository.CrudRepository;

import com.corpse.model.Price;

public interface PriceRepository extends CrudRepository<Price, Long> {

}
