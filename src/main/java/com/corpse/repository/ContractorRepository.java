package com.corpse.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.corpse.model.Contractor;

public interface ContractorRepository extends CrudRepository<Contractor, Long> {

	List<Contractor> getByHashSync(String hashSync);
	List<Contractor> getByTrader(String trader);
	List<Contractor> getByIdAndTrader(Long id, String trader);

	@Modifying
	@Query("DELETE FROM Contractor WHERE id in (:ids)")
	int deleteByIdIn(@Param("ids") List<Long> ids);
}
