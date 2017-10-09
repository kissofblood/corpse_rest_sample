package com.corpse.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.corpse.model.Account;
import com.corpse.model.ResponseUDI;
import com.corpse.repository.AccountRepository;

@Service
@Transactional
public class AccountService {

	@Autowired
	private AccountRepository accountRepository;

	public List<Account> getByKey(long id) {
		List<Account> list = new ArrayList<>();
		Account account = accountRepository.findOne(id);
		if(account == null) {
			return list;
		}

		list.add(account);
		return list;
	}

	public ResponseUDI<Long> insert(Account account) {
		ResponseUDI<Long> resp = new ResponseUDI<>();

		Account t = accountRepository.save(account);
		resp.setCount(1);
		resp.setOperation(ResponseUDI.TypeOperation.INSERT);
		resp.setId(t.getId());
		return resp;
	}
}
