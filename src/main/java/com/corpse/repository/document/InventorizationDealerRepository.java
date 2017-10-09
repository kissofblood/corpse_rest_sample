package com.corpse.repository.document;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.corpse.model.document.InventorizationDealer;

public interface InventorizationDealerRepository extends RepositoryDocument<InventorizationDealer> {

	@Override
	@Modifying
	@Query("DELETE FROM InventorizationDealer WHERE id in(:ids)")
	int deleteByIdIn(@Param("ids") List<Long> ids);
}
