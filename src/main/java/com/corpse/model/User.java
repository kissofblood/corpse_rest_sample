package com.corpse.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Type;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.corpse.util.Common;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "users", schema = "public")
public class User implements UserDetails {

	private static final long serialVersionUID = 4872250702191795243L;

	@Id
	@Column(name = "username")
	private String username;

	@Column(name = "name")
	private String name;

	@Column(name = "email")
	private String email;

	@Column(name = "pwd", nullable = false)
	private String password;

	@Column(name = "dealer")
	private Long dealer;

	@Column(name = "supervisor")
	private String supervisor;

	@Column(name = "is_supervisor")
	private Boolean isSupervisor;

	@Column(name = "is_admin")
	private Boolean isAdmin;

	@Column(name = "roles")
	@Type(type = Common.ARRAY_STRING_TYPE)
	private List<String> roles;

	@Column(name = "cities")
	@Type(type = Common.ARRAY_STRING_TYPE)
	private List<String> cities;

	@Column(name = "external", updatable = false, insertable = false)
	private boolean external;

	@Column(name = "settings")
	@Type(type = Common.MAP_JSON_TYPE)
	private Map<String, Object> settings;

	@JsonIgnore
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		List<GrantedAuthority> list = new ArrayList<>();
		for(String t : roles) {
			list.add(new SimpleGrantedAuthority(t));
		}
		if(!external) {
			list.add(new SimpleGrantedAuthority(Common.ROLE_OWNER));
		}
		return list;
	}

	@Override
	public String getPassword() {
		return password;
	}

	public String getUsername() {
		return username;
	}

	@JsonIgnore
	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@JsonIgnore
	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@JsonIgnore
	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@JsonIgnore
	@Override
	public boolean isEnabled() {
		return true;
	}

	public String getName() {
		return name;
	}

	public String getEmail() {
		return email;
	}

	public Long getDealer() {
		return dealer;
	}

	public String getSupervisor() {
		return supervisor;
	}

	public Boolean getIsSupervisor() {
		return isSupervisor;
	}

	public Boolean getIsAdmin() {
		return isAdmin;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setIsSupervisor(Boolean isSupervisor) {
		this.isSupervisor = isSupervisor;
	}

	public void setSupervisor(String supervisor) {
		this.supervisor = supervisor;
	}

	public void setIsAdmin(Boolean isAdmin) {
		this.isAdmin = isAdmin;
	}

	public void setDealer(Long dealer) {
		this.dealer = dealer;
	}

	public List<String> getRoles() {
		return this.roles;
	}

	public void setRoles(List<String> roles) {
		this.roles = roles;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public List<String> getCities() {
		return this.cities;
	}

	public void setCities(List<String> cities) {
		this.cities = cities;
	}

	public Map<String, Object> getSettings() {
		return settings;
	}

	public void setSettings(Map<String, Object> settings) {
		this.settings = settings;
	}
}

