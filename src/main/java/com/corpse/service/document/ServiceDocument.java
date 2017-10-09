package com.corpse.service.document;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.transaction.annotation.Transactional;

import com.corpse.model.ResponseUDI;
import com.corpse.model.User;
import com.corpse.model.document.Document;
import com.corpse.model.document.ResponseUDIDocument;
import com.corpse.repository.document.RepositoryDocument;

@Transactional
public abstract class ServiceDocument<T extends Document> {

	protected RepositoryDocument<T> repository;
	protected SessionFactory sessionFactory;
	protected Class<T> clazz;

	protected void prepareFilter(Map<String, String> params, List<String> nameArgs,
			Map<String, Object> args, StringBuilder query, long id, User user) {
		boolean isAll = false;
		for(Map.Entry<String, String> entry : params.entrySet()) {
			String key = entry.getKey();
			String value = entry.getValue();

			if(key.equals("number")) {
				nameArgs.add("D.number=:numberArg");
				args.put("numberArg", Long.parseLong(value));
			}

			if(key.equals("momentfrom")) {
			nameArgs.add("D.moment>=:momentFromArg");
				Calendar calendar = Calendar.getInstance();
				calendar.setTimeInMillis(Long.parseLong(value));
				args.put("momentFromArg", calendar);
			}

			if(key.equals("momentto")) {
				nameArgs.add("D.moment<=:momentToArg");
				Calendar calendar = Calendar.getInstance();
				calendar.setTimeInMillis(Long.parseLong(value));
				args.put("momentToArg", calendar);
			}

			if(key.equals("applicable")) {
				nameArgs.add("D.applicable=:applicableArg");
				args.put("applicableArg", Boolean.parseBoolean(value));
			}

			if(key.equals("owner")) {
				nameArgs.add("D.owner=:ownerArg");
				args.put("ownerArg", value);
			}

			if(key.equals("tradepoint")) {
				nameArgs.add("D.tradePoint=:tradePointArg");
				args.put("tradePointArg", Long.parseLong(value));
			}

			if(key.equals("all")) {
				isAll = Boolean.parseBoolean(value);
			}

			if(key.equals("dealer")) {
				nameArgs.add("U.dealer=:dealerArg");
				args.put("dealerArg", Long.parseLong(value));
			}
		}

		if(isAll) {
			if(user.getIsAdmin()) {
				query.append(
					"SELECT D FROM User U, " + clazz.getSimpleName() + " D WHERE D.owner=U.username "
				);
			}
			else if(user.getIsSupervisor()) {
				query.append(
					"SELECT D FROM User U, " + clazz.getSimpleName() + " D WHERE D.owner=U.username "
				);
				nameArgs.add("(U.supervisor=:supervisorIsAll OR U.username=:usernameIsAll)");
				nameArgs.add("U.is_admin=false");

				args.put("supervisorIsAll", user.getUsername());
				args.put("usernameIsAll", user.getUsername());
			}
			else {
				query.append(
					"SELECT D FROM User U, " + clazz.getSimpleName() + " D WHERE D.owner=U.username "
				);
				nameArgs.add("D.owner=:ownerIsAll");
				args.put("ownerIsAll", user.getUsername());
			}
		}
		else {
			if(id == -1) {
				query.append(
					"SELECT D FROM User U, " + clazz.getSimpleName() + " D WHERE D.owner=U.username "
				);
				nameArgs.add("D.owner=:ownerNoAll");
				args.put("ownerNoAll", user.getUsername());
			}
			else {
				if(user.getIsAdmin()) {
					query.append(
						"SELECT D FROM User U, " + clazz.getSimpleName() + " D WHERE D.owner=U.username "
					);
				}
				else if(user.getIsSupervisor()) {
					query.append(
						"SELECT D FROM User U, " + clazz.getSimpleName() + " D WHERE D.owner=U.username "
					);
					nameArgs.add("(U.supervisor=:supervisorNoAll OR U.username=:usernameNoAll)");
					nameArgs.add("U.is_admin=false");
					args.put("supervisorNoAll", user.getUsername());
					args.put("usernameNoAll", user.getUsername());
				}
				else {
					query.append(
						"SELECT D FROM User U, " + clazz.getSimpleName() + " D WHERE D.owner=U.username "
					);
					nameArgs.add("D.owner=:ownerNoAll");
					args.put("ownerNoAll", user.getUsername());
				}

				nameArgs.add("D.id=id");
				args.put("id", id);
			}
		}
	}

	@SuppressWarnings("unchecked")
	private List<T> getFilter(long id, Map<String, String> params, User user) {
		List<String> nameArgs = new ArrayList<>();
		Map<String, Object> args = new HashMap<>();
		StringBuilder query = new StringBuilder();

		prepareFilter(params, nameArgs, args, query, id, user);

		Session session = sessionFactory.getCurrentSession();
		Query hsql;
		if(args.isEmpty()) {
			hsql = session.createQuery(query.toString());
		}
		else {
			query.append(" AND " + String.join(" AND ", nameArgs));
			hsql = session.createQuery(query.toString());
			for(Map.Entry<String, Object> entry : args.entrySet()) {
				hsql.setParameter(entry.getKey(), entry.getValue());
			}
		}
		return hsql.list();
	}

	public List<T> getFilterAll(Map<String, String> params, User user) {
		return getFilter(-1, params, user);
	}

	public List<T> getFilterByKey(long id, User user) {
		return getFilter(id, new HashMap<String, String>(), user);
	}
	
	protected abstract void checkValue(T document);

	public ResponseUDI<Long> deleteByKey(long id) {
		ResponseUDI<Long> resp = new ResponseUDI<Long>();
		if(repository.exists(id)) {
			repository.delete(id);
			resp.setCount(1);
		}
		else {
			resp.setCount(0);
		}
		resp.setOperation(ResponseUDI.TypeOperation.DELETE);
		resp.setId(id);
		return resp;
	}

	public ResponseUDI<Long> deleteByKeys(List<Long> ids) {
		ResponseUDI<Long> resp = new ResponseUDI<>();
		resp.setCount(repository.deleteByIdIn(ids));
		resp.setId(0l);
		resp.setOperation(ResponseUDI.TypeOperation.DELETE);
		return resp;
	}
	
	public ResponseUDI<Long> updateByKey(T document) {
		checkValue(document);

		ResponseUDI<Long> resp = new ResponseUDI<>();
		List<T> h = repository.getByHashSync(document.getHashSync());
		if(h.isEmpty()) {
			if(document.getMoment() == null) {
				document.setMoment(Calendar.getInstance());
			}
		
			document.setUpdated(Calendar.getInstance());
			if(repository.exists(document.getId())) {
				repository.save(document);
				resp.setCount(1);
			}
			else {
				resp.setCount(0);
			}
			resp.setId(document.getId());
		}
		else {
			resp.setId(h.get(0).getId());
			resp.setCount(h.size());
		}
		resp.setOperation(ResponseUDI.TypeOperation.UPDATE);
		return resp;
	}
	
	public ResponseUDIDocument insert(T document) {
		checkValue(document);
		
		ResponseUDIDocument resp = new ResponseUDIDocument();
		List<T> h = repository.getByHashSync(document.getHashSync());
		if(h.isEmpty()) {
			if(document.getMoment() == null) {
				document.setMoment(Calendar.getInstance());
			}
			
			Session session = sessionFactory.getCurrentSession();
			session.save(document);
			session.refresh(document);
			resp.setId(document.getId());
			resp.setNumber(document.getNumber());
		}
		else {
			resp.setId(h.get(0).getId());
			resp.setNumber(h.get(0).getNumber());
		}

		resp.setCount(1);
		resp.setOperation(ResponseUDI.TypeOperation.INSERT);
		return resp;
	}
	
	public List<ResponseUDIDocument> insertObjects(List<T> documents) {
		List<ResponseUDIDocument> result = new ArrayList<>();
		for(T doc : documents) {
			result.add(insert(doc));
		}
		return result;
	}
	
	public List<ResponseUDI<Long>> updateObjects(List<T> documents) {
		List<ResponseUDI<Long>> result = new ArrayList<>();
		for(T doc : documents) {
			result.add(updateByKey(doc));
		}
		return result;
	}
}
