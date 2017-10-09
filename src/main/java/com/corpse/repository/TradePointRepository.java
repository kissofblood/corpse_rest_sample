package com.corpse.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.corpse.model.TradePoint;

public interface TradePointRepository extends CrudRepository<TradePoint, Long> {

	List<TradePoint> getByHashSync(String hashSync);

	@Modifying
	@Query("DELETE FROM TradePoint WHERE id in(:ids)")
	int deleteByIdIn(@Param("ids") List<Long> id);
}
