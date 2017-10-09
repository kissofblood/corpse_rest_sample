package com.corpse.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.corpse.model.Task;

public interface TaskRepository extends CrudRepository<Task, Long> {

	@Modifying
	@Query("DELETE FROM Task WHERE id in (:ids)")
	int deleteByIdIn(@Param("ids") List<Long> ids);
}
