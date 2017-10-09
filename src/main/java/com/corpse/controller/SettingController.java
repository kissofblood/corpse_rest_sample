package com.corpse.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.corpse.model.ResponseUDI;
import com.corpse.model.Setting;
import com.corpse.service.SettingService;
import com.corpse.util.Common;

@Controller
@RequestMapping("/api/setting")
@Secured(Common.ROLE_OWNER)
public class SettingController {

	@Autowired
	private SettingService settingService;

	@RequestMapping(method = RequestMethod.GET)
	@ResponseBody
	public List<Setting> getAll() {
		return settingService.getAll();
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	@ResponseBody
	public List<Setting> getByKey(@PathVariable("id") String id) {
		return settingService.getByKey(id);
	}

	@RequestMapping(value = "/update", method = RequestMethod.POST)
	@ResponseBody
	public ResponseUDI<String> updateByKey(@RequestParam("id") String id,
			@RequestParam("value") String value) {
		Setting setting = new Setting();
		setting.setId(id);
		setting.setValue(value);
		return settingService.updateByKey(setting);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	@ResponseBody
	public ResponseUDI<String> deleteByKey(@PathVariable("id") String id) {
		return settingService.deleteByKey(id);
	}

	@RequestMapping(value = "/insert", method = RequestMethod.POST)
	@ResponseBody
	public ResponseUDI<String> insert(@RequestParam("id") String id,
			@RequestParam("value") String value) {
		Setting setting = new Setting();
		setting.setId(id);
		setting.setValue(value);
		return settingService.insert(setting);
	}
}
