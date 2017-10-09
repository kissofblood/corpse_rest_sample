package com.corpse.repository.document;

import org.springframework.data.jpa.repository.Query;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.query.Param;

import com.corpse.model.document.DemandDealer;

public interface DemandDealerRepository extends RepositoryDocument<DemandDealer> {

	@Override
	@Modifying
	@Query("DELETE FROM DemandDealer WHERE id in(:ids)")
	int deleteByIdIn(@Param("ids") List<Long> ids);
}
