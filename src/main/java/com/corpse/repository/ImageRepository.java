package com.corpse.repository;

import org.springframework.data.repository.CrudRepository;

import com.corpse.model.Image;

public interface ImageRepository extends CrudRepository<Image, String> {

}
