package com.corpse.model;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "groups", schema = "public")
public class Group extends ModelIdName {

	private static final long serialVersionUID = -2768347605974936551L;
}
