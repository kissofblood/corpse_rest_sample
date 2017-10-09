package com.corpse.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.corpse.model.Contractor;
import com.corpse.model.ResponseUDI;
import com.corpse.model.User;
import com.corpse.repository.ContractorRepository;

@Service
@Transactional
public class ContractorService {

	@Autowired
	private ContractorRepository contractorRepository;

	public List<Contractor> getAll(User user) {
		List<Contractor> result = new ArrayList<>();

		if(user.getIsAdmin()) {
			contractorRepository.findAll().forEach(result::add);
		}
		else {
			contractorRepository.getByTrader(user.getUsername()).forEach(result::add);
		}
		return result;
	}

	public List<Contractor> getByKey(long id, User user) {
		List<Contractor> result = new ArrayList<>();

		if(user.getIsAdmin()) {
			Contractor t = contractorRepository.findOne(id);
			if(t != null) {
				result.add(t);
			}
		}
		else {
			contractorRepository
				.getByIdAndTrader(id, user.getUsername())
				.forEach(result::add);
		}
		return result;
	}

	private ResponseUDI<Long> updateOrInsert(Contractor contractor) {
		ResponseUDI<Long> resp = new ResponseUDI<Long>();
		List<Contractor> h = contractorRepository.getByHashSync(contractor.getHashSync());
		if(h.isEmpty()) {
			Contractor c = contractorRepository.save(contractor);
			resp.setId(c.getId());
		}
		else {
			resp.setId(h.get(0).getId());
		}
		resp.setCount(1);
		return resp;
	}

	public ResponseUDI<Long> updateByKey(Contractor contractor) {
		ResponseUDI<Long> resp = updateOrInsert(contractor);
		resp.setOperation(ResponseUDI.TypeOperation.UPDATE);
		return resp;
	}

	public ResponseUDI<Long> insert(Contractor contractor) {
		ResponseUDI<Long> resp = updateOrInsert(contractor);
		resp.setOperation(ResponseUDI.TypeOperation.INSERT);
		return resp;
	}

	public List<ResponseUDI<Long>> insertObject(User user, List<Contractor> contractors) {
		List<ResponseUDI<Long>> resp = new ArrayList<>();
		for(Contractor contractor : contractors) {
			contractor.setTrader(user.getUsername());
			contractor.setDealer(contractor.getDealer() == null ? user.getDealer() : contractor.getDealer());
			resp.add(insert(contractor));
		}
		return resp;
	}

	public List<ResponseUDI<Long>> updateObject(User user, List<Contractor> contractors) {
		List<ResponseUDI<Long>> resp = new ArrayList<>();
		for(Contractor contractor : contractors) {
			contractor.setTrader(user.getUsername());
			contractor.setDealer(contractor.getDealer() == null ? user.getDealer() : contractor.getDealer());
			resp.add(updateByKey(contractor));
		}
		return resp;
	}

	public ResponseUDI<Long> deleteByKey(long id) {
		ResponseUDI<Long> resp = new ResponseUDI<Long>();
		if(contractorRepository.exists(id)) {
			contractorRepository.delete(id);
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
		resp.setCount(contractorRepository.deleteByIdIn(ids));
		resp.setId(0l);
		resp.setOperation(ResponseUDI.TypeOperation.DELETE);
		return resp;
	}
}
