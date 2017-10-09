package com.corpse.repository;

import org.springframework.data.repository.CrudRepository;

import com.corpse.model.Setting;

public interface SettingRepository extends CrudRepository<Setting, String> {

}
