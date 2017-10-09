package com.corpse.service;

import java.sql.Timestamp;
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

import com.corpse.model.StorageBalance;
import com.corpse.model.StorageHistory;

@Service
@Transactional
public class BalanceService {

	@Autowired
	private SessionFactory sessionFactory;

	@SuppressWarnings("unchecked")
	public List<StorageHistory> getAllHistory(Map<String, String> params) {
		Session session = sessionFactory.getCurrentSession();
		StringBuilder query = new StringBuilder("FROM StorageHistory ");
		Map<String, Object> args = new HashMap<>();
		List<String> keys = new ArrayList<>();

		for(Map.Entry<String, String> entry : params.entrySet()) {
			String key = entry.getKey();
			String value = entry.getValue();

			if(key.equals("momentFrom")) {
				keys.add("moment>=:momentFrom");
				Calendar c = Calendar.getInstance();
				c.setTimeInMillis(Long.parseLong(value));
				args.put("momentFrom", c);
			}
			else if(key.equals("momentTo")) {
				keys.add("moment<=:momentTo");
				Calendar c = Calendar.getInstance();
				c.setTimeInMillis(Long.parseLong(value));
				args.put("momentTo", c);
			}
			else if(key.equals("storage")) {
				keys.add("storage=:storage");
				args.put("storage", Long.parseLong(value));
			}
			else if(key.equals("item")) {
				keys.add("item=:item");
				args.put("item", Long.parseLong(value));
			}
		}

		if(!keys.isEmpty()) {
			query.append("WHERE " + String.join(" AND ", keys));
		}
		Query hsql = session.createQuery(query.toString());
		for(Map.Entry<String, Object> entry : args.entrySet()) {
			hsql.setParameter(entry.getKey(), entry.getValue());
		}
		return hsql.list();
	}

	@SuppressWarnings("unchecked")
	public List<StorageBalance> getAllBalance(Map<String, String> params) {
		Session session = sessionFactory.getCurrentSession();
		StringBuilder query = new StringBuilder("SELECT * FROM public.storagebalance(:moment) B");
		Map<String, Object> args = new HashMap<>();
		List<String> keys = new ArrayList<>();

		for(Map.Entry<String, String> entry : params.entrySet()) {
			String key = entry.getKey();
			String value = entry.getValue();

			if(key.equals("moment")) {
				args.put("moment", new Timestamp(Long.parseLong(value)));
			}
			else if(key.equals("storage")) {
				keys.add("storage=:storage");
				args.put("storage", Long.parseLong(value));
			}
			else if(key.equals("item")) {
				keys.add("item=:item");
				args.put("item", Long.parseLong(value));
			}
			else if(key.equals("stockMode")) {
				if(value.equals("positiveOnly")) {
					keys.add("cost>=0");
				}
				else if(value.equals("negativeOnly")) {
					keys.add("cost<=0");
				}
			}
		}

		if(!args.containsKey("moment")) {
			args.put("moment", new Timestamp(Calendar.getInstance().getTimeInMillis()));
		}

		if(!keys.isEmpty()) {
			query.append(" WHERE " + String.join(" AND ", keys));
		}

		Query hsql = session.createSQLQuery(query.toString()).addEntity(StorageBalance.class);
		for(Map.Entry<String, Object> entry : args.entrySet()) {
			hsql.setParameter(entry.getKey(), entry.getValue());
		}
		return hsql.list();
	}
}
