package com.corpse.model;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "collections", schema = "public")
public class Collection extends ModelIdName  {

	private static final long serialVersionUID = -2276025706463319505L;

}
