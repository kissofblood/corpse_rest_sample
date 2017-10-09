package com.corpse.model;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "trade_point_types", schema = "public")
public class TradePointType extends ModelIdName {

	private static final long serialVersionUID = -8281584994515306809L;
}
