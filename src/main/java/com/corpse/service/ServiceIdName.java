package com.corpse.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import com.corpse.model.ModelIdName;
import com.corpse.model.ResponseUDI;

@Transactional
public abstract class ServiceIdName<T extends ModelIdName> {

	protected CrudRepository<T, Long> repository;

	public List<T> getAll() {
		List<T> list = new ArrayList<>();
		repository.findAll().forEach(list::add);
		return list;
	}

	public List<T> getByKey(long id) {
		List<T> list = new ArrayList<>();
		T t = repository.findOne(id);
		if(t == null) {
			return list;
		}

		list.add(t);
		return list;
	}

	public ResponseUDI<Long> updateByKey(T idName) {
		ResponseUDI<Long> resp = new ResponseUDI<Long>();
		resp.setOperation(ResponseUDI.TypeOperation.UPDATE);
		if(repository.exists(idName.getId())) {
			repository.save(idName);
			resp.setCount(1);
		}
		else {
			resp.setCount(0);
		}
		resp.setId(idName.getId());
		return resp;
	}

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

	public ResponseUDI<Long> insert(T idName) {
		ResponseUDI<Long> resp = new ResponseUDI<Long>();
		T t = repository.save(idName);
		resp.setCount(1);
		resp.setOperation(ResponseUDI.TypeOperation.INSERT);
		resp.setId(t.getId());
		return resp;
	}
}
