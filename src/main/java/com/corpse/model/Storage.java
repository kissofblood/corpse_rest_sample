package com.corpse.model;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "storages", schema = "public")
public class Storage extends ModelIdName {

	private static final long serialVersionUID = -6298710785333413442L;

}
