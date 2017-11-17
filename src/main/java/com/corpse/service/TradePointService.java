package com.corpse.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.corpse.model.Account;
import com.corpse.model.ResponseUDI;
import com.corpse.model.Storage;
import com.corpse.model.TradePoint;
import com.corpse.model.User;
import com.corpse.repository.TradePointRepository;

@Service
@Transactional
public class TradePointService {

	@Autowired
	private TradePointRepository tradePointRepository;

	@Autowired
	private StorageService storageService;

	@Autowired
	private AccountService accountService;

	@Autowired
	private SessionFactory sessionFactory;

	@SuppressWarnings("unchecked")
	private List<TradePoint> get(long id, Map<String, String> params, User user) {
		StringBuilder hsql = new StringBuilder();
		List<String> nameArgs = new ArrayList<>();
		Map<String, Object> args = new HashMap<>();

		boolean isAll = false;
		for(Map.Entry<String, String> entry : params.entrySet()) {
			String key = entry.getKey();
			String value = entry.getValue();

			if(key.equals("dealer")) {
				nameArgs.add("T.dealer=:dealerArg");
				args.put("dealerArg", Long.parseLong(value));
			}

			if(key.equals("trader")) {
				nameArgs.add("T.trader=:traderArg");
				args.put("traderArg", value);
			}

			if(key.equals("contractor")) {
				nameArgs.add("T.contractor=:contractorArg");
				args.put("contractorArg", Long.parseLong(value));
			}

			if(key.equals("all")) {
				isAll = Boolean.parseBoolean(value);
			}
		}

		if(isAll) {
			if(user.getIsAdmin()) {
				hsql.append("SELECT T FROM User U, TradePoint T WHERE U.username=T.trader ");
			}
			else if(user.getIsSupervisor()) {
				hsql.append("SELECT T FROM User U, TradePoint T WHERE U.username=T.trader");
				nameArgs.add("(U.supervisor=:supervisorIsAll OR U.username=:usernameIsAll)");
				nameArgs.add("U.is_admin=false");
				args.put("supervisorIsAll", user.getUsername());
				args.put("usernameIsAll", user.getUsername());
			}
			else {
				hsql.append("FROM TradePoint T WHERE 1=1 ");
				nameArgs.add("T.trader=:traderIsAll");
				args.put("traderIsAll", user.getUsername());
			}
		}
		else {
			if(id == -1) {
				hsql.append("FROM TradePoint T WHERE 1=1 ");
				nameArgs.add("T.trader=:traderNoAll");
				args.put("traderNoAll", user.getUsername());
			}
			else {
				if(user.getIsAdmin()) {
					hsql.append("FROM TradePoint T");
				}
				else if(user.getIsSupervisor()) {
					hsql.append("SELECT T FROM User U, TradePoint T WHERE U.username=T.trader ");
					nameArgs.add("(U.supervisor=:supervisorNoAll OR U.username=:usernameNoAll)");
					nameArgs.add("U.is_admin=false");
					args.put("supervisorNoAll", user.getUsername());
					args.put("usernameNoAll", user.getUsername());
				}
				else {
					hsql.append("FROM TradePoint T WHERE 1=1 ");
					nameArgs.add("T.trader=:traderNoAll");
					args.put("traderNoAll", user.getUsername());
				}
				nameArgs.add("T.id=:id");
				args.put("id", id);
			}
		}

		if(!args.isEmpty()) {
			hsql.append(" AND " + String.join(" AND ", nameArgs));
		}

		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery(hsql.toString());
		for(Map.Entry<String, Object> entry : args.entrySet()) {
			query.setParameter(entry.getKey(), entry.getValue());
		}

		return query.list();
	}

	@Cacheable(value = "tradepoints", key = "#user.username")
	public List<TradePoint> getAll(Map<String, String> params, User user) {
	     try {
             long time = 5000L;
             Thread.sleep(time);
         } catch (InterruptedException e) {
             throw new IllegalStateException(e);
        }
		return get(-1, params, user);
	}

	public List<TradePoint> getByKey(long id, User user) {
		return get(id, new HashMap<String, String>(), user);
	}

	public ResponseUDI<Long> updateByKey(TradePoint tradePoint) {
		ResponseUDI<Long> resp = new ResponseUDI<Long>();
		List<TradePoint> h = tradePointRepository.getByHashSync(tradePoint.getHashSync());
		if(h.isEmpty()) {
			tradePointRepository.save(tradePoint);
			resp.setId(tradePoint.getId());
			resp.setCount(1);
		}
		else {
			resp.setId(h.get(0).getId());
			resp.setCount(h.size());
		}
		resp.setOperation(ResponseUDI.TypeOperation.UPDATE);
		return resp;
	}

	public ResponseUDI<Long> deleteByKey(long id) {
		ResponseUDI<Long> resp = new ResponseUDI<Long>();
		if(tradePointRepository.exists(id)) {
			resp.setCount(1);
			tradePointRepository.delete(id);
		}
		else {
			resp.setCount(0);
		}
		resp.setOperation(ResponseUDI.TypeOperation.DELETE);
		resp.setId(id);
		return resp;
	}

	public ResponseUDI<Long> insert(TradePoint tradePoint) {
		ResponseUDI<Long> resp = new ResponseUDI<Long>();
		List<TradePoint> h = tradePointRepository.getByHashSync(tradePoint.getHashSync());
		if(h.isEmpty()) {
			if(tradePoint.getStorage() == null || tradePoint.getStorage() == 0) {
				Storage storage = new Storage();
				storage.setName(tradePoint.getName());
				ResponseUDI<Long> respStorage = storageService.insert(storage);
				tradePoint.setStorage(respStorage.getId());
			}

			Account account = new Account();
			account.setBalance(0.0);
			tradePoint.setAccount(accountService.insert(account).getId());

			TradePoint t = tradePointRepository.save(tradePoint);
			resp.setId(t.getId());
		}
		else {
			resp.setId(h.get(0).getId());
		}
		resp.setCount(1);
		resp.setOperation(ResponseUDI.TypeOperation.INSERT);
		return resp;
	}

	public List<ResponseUDI<Long>> insertObject(List<TradePoint> tradePoints) {
		List<ResponseUDI<Long>> resp = new ArrayList<>();
		tradePoints.stream().forEach(item -> resp.add(insert(item)));
		return resp;
	}

	public List<ResponseUDI<Long>> updateObject(List<TradePoint> tradePoints) {
		List<ResponseUDI<Long>> resp = new ArrayList<>();
		tradePoints.stream().forEach(item -> resp.add(updateByKey(item)));
		return resp;
	}

	public ResponseUDI<Long> deleteByKeys(List<Long> ids) {
		ResponseUDI<Long> resp = new ResponseUDI<>();
		resp.setId(0l);
		resp.setCount(tradePointRepository.deleteByIdIn(ids));
		resp.setOperation(ResponseUDI.TypeOperation.DELETE);
		return resp;
	}

	public ResponseUDI<Long> deleteForceByKeys(List<Long> ids) {
		List<String> t = new ArrayList<>();
		for(Long id : ids) {
			t.add(String.valueOf(id));
		}
		Session session = sessionFactory.getCurrentSession();
		session.createSQLQuery("SELECT * FROM public.\"forceDeleteTradePoint\"(cast(:ids as integer[]))")
			.setParameter("ids", "{" +  String.join(",", t) + "}")
			.list();

		ResponseUDI<Long> resp = new ResponseUDI<>();
		resp.setCount(-1);
		resp.setId(0l);
		resp.setOperation(ResponseUDI.TypeOperation.DELETE);
		return resp;
	}
}
