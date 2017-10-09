package com.corpse.repository.document;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;

import com.corpse.model.document.Document;

@NoRepositoryBean
public interface RepositoryDocument<T extends Document> extends CrudRepository<T, Long> {

	List<T> getByHashSync(String hashSync);
	int deleteByIdIn(List<Long> ids);
}
