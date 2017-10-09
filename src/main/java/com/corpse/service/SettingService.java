package com.corpse.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.corpse.model.ResponseUDI;
import com.corpse.model.Setting;
import com.corpse.repository.SettingRepository;

@Service
@Transactional
public class SettingService {

	@Autowired
	private SettingRepository settingRepository;

	public List<Setting> getAll() {
		List<Setting> list = new ArrayList<>();
		settingRepository.findAll().forEach(list::add);
		return list;
	}

	public List<Setting> getByKey(String id) {
		List<Setting> list = new ArrayList<>();
		Setting setting = settingRepository.findOne(id);
		if(setting != null) {
			list.add(setting);
		}
		return list;
	}

	public ResponseUDI<String> updateByKey(Setting setting) {

		ResponseUDI<String> resp = new ResponseUDI<String>();
		if(settingRepository.exists(setting.getId())) {
			settingRepository.save(setting);
			resp.setCount(1);
		}
		else {
			resp.setCount(0);
		}
		resp.setOperation(ResponseUDI.TypeOperation.UPDATE);
		resp.setId(setting.getId());
		return resp;
	}

	public ResponseUDI<String> deleteByKey(String id) {
		ResponseUDI<String> resp = new ResponseUDI<String>();
		if(settingRepository.exists(id)) {
			resp.setCount(1);
			settingRepository.delete(id);
		}
		else {
			resp.setCount(0);
		}
		resp.setOperation(ResponseUDI.TypeOperation.DELETE);
		resp.setId(id);
		return resp;
	}

	public ResponseUDI<String> insert(Setting setting) {
		settingRepository.save(setting);

		ResponseUDI<String> resp = new ResponseUDI<String>();
		resp.setCount(1);
		resp.setOperation(ResponseUDI.TypeOperation.INSERT);
		resp.setId(setting.getId());
		return resp;
	}
}
