package com.corpse.service;

import org.springframework.transaction.annotation.Transactional;

import com.corpse.model.Account;
import com.corpse.model.Dealer;
import com.corpse.model.ResponseUDI;
import com.corpse.model.Storage;
import com.corpse.model.User;
import com.corpse.repository.DealerRepository;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class DealerService {

	@Autowired
	private SessionFactory sessionFactory;

	@Autowired
	private DealerRepository dealerRepository;

	@Autowired
	private AccountService accountService;

	@Autowired
	private StorageService storageService;

	@SuppressWarnings("unchecked")
	private List<Dealer> get(long id, User user) {
		Session session = sessionFactory.getCurrentSession();

		Query query;
		if(user.getIsAdmin()) {
			if(id == -1) {
				query = session.createQuery("FROM Dealer");
			}
			else {
				query = session.createQuery("FROM Dealer WHERE id=:id");
				query.setParameter("id", id);
			}
		}
		else {
			if(id == -1) {
				query = session.createQuery(
					"SELECT D FROM Dealer D INNER JOIN User U on(D.id=U.dealer) WHERE U.username=:username"
				);
				query.setParameter("username", user.getUsername());
			}
			else {
				query = session.createQuery(
					"SELECT D FROM Dealer D INNER JOIN User U on(D.id=U.dealer) WHERE D.id=:id AND U.username=:username"
				);
				query.setParameter("id", id);
				query.setParameter("username", user.getUsername());
			}
		}
		return query.list();
	}

	public List<Dealer> getAll(User user) {
		return get(-1, user);
	}

	public List<Dealer> getByKey(long id, User user) {
		return get(id, user);
	}

	public List<Dealer> getByKey(long id) {
		List<Dealer> list = new ArrayList<>();
		Dealer d = dealerRepository.findOne(id);
		if(d == null) {
			return list;
		}
		list.add(d);
		return list;
	}

	public ResponseUDI<Long> updateByKey(Dealer dealer) {
		ResponseUDI<Long> resp = new ResponseUDI<>();
		if(dealerRepository.exists(dealer.getId())) {
			dealerRepository.save(dealer);
			resp.setCount(1);
		}
		else {
			resp.setCount(0);
		}
		resp.setOperation(ResponseUDI.TypeOperation.UPDATE);
		resp.setId(dealer.getId());
		return resp;
	}

	public ResponseUDI<Long> deleteByKey(long id) {
		ResponseUDI<Long> resp = new ResponseUDI<>();
		if(dealerRepository.exists(id)) {
			resp.setCount(1);
			dealerRepository.delete(id);
		}
		else {
			resp.setCount(0);
		}
		resp.setOperation(ResponseUDI.TypeOperation.DELETE);
		resp.setId(id);
		return resp;
	}

	public ResponseUDI<Long> insert(Dealer dealer) {
		Long resultStorage;
		if(dealer.getStorage() == null || dealer.getStorage() == 0) {
			Storage storageModel = new Storage();
			storageModel.setName(dealer.getName());
			ResponseUDI<Long> respStorage = storageService.insert(storageModel);
			resultStorage = respStorage.getId();
		}
		else {
			resultStorage = dealer.getStorage();
		}

		Account accountModel = new Account();
		accountModel.setBalance(0.0);
		long idAccount = accountService.insert(accountModel).getId();

		dealer.setStorage(resultStorage);
		dealer.setAccount(idAccount);

		Dealer d = dealerRepository.save(dealer);
		ResponseUDI<Long> resp = new ResponseUDI<Long>();
		resp.setCount(1);
		resp.setOperation(ResponseUDI.TypeOperation.INSERT);
		resp.setId(d.getId());
		return resp;
	}
}
