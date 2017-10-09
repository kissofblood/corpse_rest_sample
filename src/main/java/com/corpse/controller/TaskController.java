package com.corpse.controller;

import java.security.Principal;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.corpse.model.ResponseUDI;
import com.corpse.model.Task;
import com.corpse.service.TaskService;
import com.corpse.util.Common;

@Controller
@RequestMapping("/api/task")
@Secured(Common.ROLE_OWNER)
public class TaskController {

	@Autowired
	private TaskService taskService;

	@RequestMapping(method = RequestMethod.GET)
	@ResponseBody
	public List<Task> getAll(Principal principal, @RequestParam Map<String, String> params) {
		return taskService.getAll(params, Common.getUser(principal));
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	@ResponseBody
	public List<Task> getByKey(Principal principal, @PathVariable("id") long id) {
		return taskService.getByKey(id, Common.getUser(principal));
	}

	@RequestMapping(value = "/update", method = RequestMethod.POST)
	@ResponseBody
	public ResponseUDI<Long> updateByKey(@PathVariable("id") long id,
			@PathVariable("created") Long created,
			@PathVariable("createUser") String createUser,
			@PathVariable("trader") String trader,
			@PathVariable("tradePoint") Long tradePoint,
			@PathVariable("description") String description,
			@PathVariable("dueDate") Long dueDate,
			@PathVariable("state") Integer state) {
		Task task = new Task();
		task.setId(id);
		Calendar ccreated = Calendar.getInstance();
		ccreated.setTimeInMillis(created);
		task.setCreated(ccreated);
		task.setCreateUser(createUser);
		task.setTrader(trader);
		task.setTradePoint(tradePoint);
		task.setDescription(description);
		Calendar cdueDate = Calendar.getInstance();
		cdueDate.setTimeInMillis(dueDate);
		task.setDueDate(cdueDate);
		task.setState(state);
		return taskService.updateByKey(task);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	@ResponseBody
	public ResponseUDI<Long> deleteByKey(@PathVariable("id") Long id) {
		return taskService.deleteByKey(id);
	}

	@RequestMapping(value = "/insert", method = RequestMethod.POST)
	@ResponseBody
	public ResponseUDI<Long> insert(@PathVariable("created") Long created,
			@PathVariable("createUser") String createUser,
			@PathVariable("trader") String trader,
			@PathVariable("tradePoint") Long tradePoint,
			@PathVariable("description") String description,
			@PathVariable("dueDate") Long dueDate,
			@PathVariable("state") Integer state) {
		Task task = new Task();
		Calendar ccreated = Calendar.getInstance();
		ccreated.setTimeInMillis(created);
		task.setCreated(ccreated);
		task.setCreateUser(createUser);
		task.setTrader(trader);
		task.setTradePoint(tradePoint);
		task.setDescription(description);
		Calendar cdueDate = Calendar.getInstance();
		cdueDate.setTimeInMillis(dueDate);
		task.setDueDate(cdueDate);
		task.setState(state);
		return taskService.insert(task);
	}

	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	@ResponseBody
	public ResponseUDI<Long> deleteByKeys(@RequestBody List<Long> ids) {
		return taskService.deleteByKeys(ids);
	}

	@RequestMapping(value = "/update/object", method = RequestMethod.POST)
	@ResponseBody
	public List<ResponseUDI<Long>> updateObjects(@RequestBody List<Task> tasks) {
		return taskService.updateObjects(tasks);
	}

	@RequestMapping(value = "/insert/object", method = RequestMethod.POST)
	@ResponseBody
	public List<ResponseUDI<Long>> insertObjects(@RequestBody List<Task> tasks) {
		return taskService.insertObjects(tasks);
	}
}
