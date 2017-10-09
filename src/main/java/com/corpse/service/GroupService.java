package com.corpse.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.corpse.model.Group;
import com.corpse.repository.GroupRepository;

@Service
@Transactional
public class GroupService extends ServiceIdName<Group> {

	@Autowired
	public void setGroupRepository(GroupRepository groupRepository) {
		repository = groupRepository;
	}
}
