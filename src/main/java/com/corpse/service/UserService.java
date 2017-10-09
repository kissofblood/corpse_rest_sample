package com.corpse.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.corpse.model.ResponseUDI;
import com.corpse.model.User;
import com.corpse.repository.UserRepository;
import com.corpse.util.Common;

@Service
@Transactional
public class UserService implements UserDetailsService {

	private static final Logger logger = LoggerFactory.getLogger(UserService.class);

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private SessionFactory sessionFactory;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepository.findOne(username);
		if(user == null) {
			logger.error(Common.log(username, "authentication failed"));
			throw new UsernameNotFoundException("Username not found");
		}

		logger.info(Common.log(username, "authentication success"));
		return user;
	}

	@SuppressWarnings("unchecked")
	private List<User> get(String username, Map<String, String> params, User user) {
		Map<String, Object> argsVal = new HashMap<>();
		List<String> args = new ArrayList<>();
		StringBuilder query = new StringBuilder("FROM User");
		for(Map.Entry<String, String> entry : params.entrySet()) {
			String key = entry.getKey();
			String value = entry.getValue();

			if(key.equals("dealer")) {
				args.add("dealer=:dealer");
				argsVal.put("dealer", Long.parseLong(value));
			}
			else if(key.equals("supervisor")) {
				args.add("supervisor=:supervisor");
				argsVal.put("supervisor", value);
			}
			else if(key.equals("is_supervisor")) {
				args.add("isSupervisor=:isSupervisor");
				argsVal.put("isSupervisor", Boolean.parseBoolean(value));
			}
		}

		if(!user.getIsAdmin()) {
			if(user.getRoles().contains("ROLE_ADMIN")) {
				args.add("dealer=:dealerRole");
				argsVal.put("dealerRole", user.getDealer());
			}
			else if(user.getIsSupervisor()) {
				args.add("supervisor=:supervisorRole");
				argsVal.put("supervisorRole", user.getUsername());
			}
			else if(user.getRoles().contains("ROLE_USER")) {
				args.add("username=:usernameRole");
				argsVal.put("usernameRole", user.getUsername());
			}
			else {
				return new ArrayList<>();
			}
			args.add("isAdmin=:isAdmin");
			argsVal.put("isAdmin", false);
		}

		if(!username.isEmpty()) {
			args.add("username=:usernameNotEmpty");
			argsVal.put("usernameNotEmpty", username);
		}

		if(!argsVal.isEmpty()) {
			query.append(" WHERE ");
			query.append("(" + String.join(" AND ", args) + ")");
			if(username.isEmpty()) {
				query.append(" OR username=:usernameIsEmpty");
				argsVal.put("usernameIsEmpty", user.getUsername());
			}
		}

		Session session = sessionFactory.getCurrentSession();
		Query hsql = session.createQuery(query.toString());
		for(Map.Entry<String, Object> e : argsVal.entrySet()) {
			hsql.setParameter(e.getKey(), e.getValue());
		}

		return hsql.list();
	}

	public List<User> getAll(Map<String, String> params, User user) {
		return get("", params, user);
	}

	public List<User> getByKey(String username, User user) {
		return get(username, new HashMap<>(), user);
	}

	public List<ResponseUDI<String>> updateByKey(List<User> users) {
		List<ResponseUDI<String>> resp = new ArrayList<>();
		Session session = sessionFactory.openSession();
		for(User user : users) {
			String hsql = "UPDATE User SET cities=:cities, dealer=:dealer, " +
							"email=:email, isAdmin=:isAdmin, isSupervisor=:isSupervisor, " +
							"name=:name, %s roles=:roles, settings=:settings, supervisor=:supervisor " +
							"WHERE username=:username";

			Query query = null;
			if(user.getPassword() != null) {
				if(user.getPassword().isEmpty()) {
					user.setPassword(null);
					query = session.createQuery(String.format(hsql, ""));
				}
				else {
					user.setPassword(passwordEncoder.encode(user.getPassword()));
					query = session.createQuery(String.format(hsql, "password=:password, "));
				}
			}
			else {
				query = session.createQuery(String.format(hsql, ""));
			}

			if(user.getPassword() != null) {
				query.setParameter("password", user.getPassword());
			}
			query.setParameter("cities", user.getCities());
			query.setParameter("dealer", user.getDealer());
			query.setParameter("email", user.getEmail());
			query.setParameter("isAdmin", user.getIsAdmin());
			query.setParameter("isSupervisor", user.getIsSupervisor());
			query.setParameter("name", user.getName());
			query.setParameter("roles", user.getRoles());
			query.setParameter("settings", user.getSettings());
			query.setParameter("supervisor", user.getSupervisor());
			query.setParameter("username", user.getUsername());

			ResponseUDI<String> r = new ResponseUDI<>();
			r.setCount(query.executeUpdate());
			r.setId(user.getUsername());
			r.setOperation(ResponseUDI.TypeOperation.UPDATE);
			resp.add(r);
		}
		return resp;
	}

	public ResponseUDI<String> updatePasswordByKey(String username, String password) {
		ResponseUDI<String> resp = new ResponseUDI<String>();
		resp.setCount(userRepository.updateByPassword(username, passwordEncoder.encode(password)));
		resp.setId(username);
		resp.setOperation(ResponseUDI.TypeOperation.UPDATE);
		return resp;
	}

	public ResponseUDI<String> updateIsAdminByKey(String username, boolean isAdmin) {
		ResponseUDI<String> resp = new ResponseUDI<String>();
		resp.setCount(userRepository.updateByIsAdmin(username, isAdmin));
		resp.setOperation(ResponseUDI.TypeOperation.UPDATE);
		resp.setId(username);
		return resp;
	}

	public ResponseUDI<String> deleteByKey(String username) {
		ResponseUDI<String> resp = new ResponseUDI<String>();
		if(userRepository.exists(username)) {
			userRepository.delete(username);
			resp.setCount(1);
		}
		else {
			resp.setCount(0);
		}
		resp.setOperation(ResponseUDI.TypeOperation.DELETE);
		resp.setId(username);
		return resp;
	}

	public List<ResponseUDI<String>> insert(List<User> users) {
		List<ResponseUDI<String>> respUDI = new ArrayList<>();
		for(User user : users) {
			user.setPassword(passwordEncoder.encode(user.getPassword()));
			userRepository.save(user);

			ResponseUDI<String> resp = new ResponseUDI<>();
			resp.setCount(1);
			resp.setOperation(ResponseUDI.TypeOperation.INSERT);
			resp.setId(user.getUsername());
			respUDI.add(resp);
		}
		return respUDI;
	}
}
