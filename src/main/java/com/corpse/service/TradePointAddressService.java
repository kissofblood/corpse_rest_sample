package com.corpse.service;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.corpse.model.TradePointAddress;

@Service
@Transactional
public class TradePointAddressService {

	@Autowired
	private SessionFactory sessionFactory;

	@SuppressWarnings("unchecked")
	public List<TradePointAddress> getAll() {
		Session session = sessionFactory.getCurrentSession();
		return session.createQuery("FROM TradePointAddress").list();
	}
}
