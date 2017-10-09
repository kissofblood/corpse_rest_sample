package com.corpse.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "items", schema = "public")
public class Item extends ModelIdName {

	private static final long serialVersionUID = 7734874187488406843L;

	@Column(name = "article")
	private String article;

	@Column(name = "collection")
	private Long collection;

	@Column(name = "image")
	private String image;

	@Column(name = "group_id")
	private Long groupId;

	public String getArticle() {
		return article;
	}

	public void setArticle(String article) {
		this.article = article;
	}

	public Long getCollection() {
		return collection;
	}

	public void setCollection(Long collection) {
		this.collection = collection;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public Long getGroupId() {
		return groupId;
	}

	public void setGroupId(Long groupId) {
		this.groupId = groupId;
	}
}
