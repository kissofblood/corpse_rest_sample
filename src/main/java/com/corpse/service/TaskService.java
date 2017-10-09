package com.corpse.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.corpse.model.ResponseUDI;
import com.corpse.model.Task;
import com.corpse.model.User;
import com.corpse.repository.TaskRepository;

@Service
@Transactional
public class TaskService {

	@Autowired
	private TaskRepository taskRepository;

	@Autowired
	private SessionFactory sessionFactory;

	@SuppressWarnings("unchecked")
	private List<Task> get(long id, Map<String, String> params, User user) {
		StringBuilder query = new StringBuilder(
			"SELECT T FROM Task T, User U WHERE T.createUser=U.username "
		);
		List<String> nameArg = new ArrayList<>();
		Map<String, Object> args = new HashMap<>();
		for(Map.Entry<String, String> entry : params.entrySet()) {
			String key = entry.getKey();
			String value = entry.getValue();

			if(key.equals("dueDate")) {
				nameArg.add("T.dueDate=:dueDateArg");
				Calendar calendar = Calendar.getInstance();
				calendar.setTimeInMillis(Long.parseLong(value));
				args.put("dueDateArg", calendar);
			}
			else if(key.equals("tradePoint")) {
				nameArg.add("T.tradePoint=:tradePointArg");
				args.put("tradePointArg", Long.parseLong(value));
			}
			else if(key.equals("trader")) {
				nameArg.add("T.trader=:traderArg");
				args.put("traderArg", value);
			}
		}

		if(id != -1) {
			nameArg.add("T.id=:id");
			args.put("id", id);
		}

		if(!user.getIsAdmin()) {
			if(user.getRoles().contains("ROLE_ADMIN")) {
				nameArg.add("(U.dealer=:dealerRole OR T.createUser=:createUserRole)");
				args.put("dealerRole", user.getDealer());
				args.put("createUserRole", user.getUsername());
			}
			else {
				nameArg.add("(T.createUser=:createUserRole OR T.trader=:traderRole)");
				args.put("createUserRole", user.getUsername());
				args.put("traderRole", user.getUsername());
			}
			nameArg.add("U.isAdmin=false");
		}

		if(!args.isEmpty()) {
			query.append(" AND " + String.join(" AND ", nameArg));
		}

		Session session = sessionFactory.getCurrentSession();
		Query hsql = session.createQuery(query.toString());
		for(Map.Entry<String, Object> entry : args.entrySet()) {
			hsql.setParameter(entry.getKey(), entry.getValue());
		}

		return hsql.list();
	}

	public List<Task> getAll(Map<String, String> params, User user) {
		return get(-1, params, user);
	}

	public List<Task> getByKey(long id, User user) {
		return get(id, new HashMap<>(), user);
	}

	public ResponseUDI<Long> updateByKey(Task task) {

		ResponseUDI<Long> resp = new ResponseUDI<>();
		resp.setId(task.getId());
		if(taskRepository.exists(task.getId())) {
			taskRepository.save(task);
			resp.setCount(1);
		}
		else {
			resp.setCount(0);
		}
		resp.setOperation(ResponseUDI.TypeOperation.UPDATE);
		return resp;
	}

	public ResponseUDI<Long> deleteByKey(long id) {
		ResponseUDI<Long> resp = new ResponseUDI<Long>();
		if(taskRepository.exists(id)) {
			taskRepository.delete(id);
			resp.setCount(1);
		}
		else {
			resp.setCount(0);
		}
		resp.setOperation(ResponseUDI.TypeOperation.DELETE);
		resp.setId(id);
		return resp;
	}

	public ResponseUDI<Long> insert(Task task) {
		Session session = sessionFactory.getCurrentSession();
		session.save(task);

		ResponseUDI<Long> resp = new ResponseUDI<Long>();
		resp.setCount(1);
		resp.setOperation(ResponseUDI.TypeOperation.INSERT);
		resp.setId(task.getId());
		return resp;
	}

	public List<ResponseUDI<Long>> insertObjects(List<Task> tasks) {
		List<ResponseUDI<Long>> resp = new ArrayList<>();
		for(Task task : tasks) {
			resp.add(insert(task));
		}
		return resp;
	}

	public List<ResponseUDI<Long>> updateObjects(List<Task> tasks) {
		List<ResponseUDI<Long>> resp = new ArrayList<>();
		for(Task task : tasks) {
			resp.add(updateByKey(task));
		}
		return resp;
	}

	public ResponseUDI<Long> deleteByKeys(List<Long> ids) {
		ResponseUDI<Long> resp = new ResponseUDI<>();
		resp.setId(0l);
		resp.setCount(taskRepository.deleteByIdIn(ids));
		resp.setOperation(ResponseUDI.TypeOperation.DELETE);
		return resp;
	}
}
