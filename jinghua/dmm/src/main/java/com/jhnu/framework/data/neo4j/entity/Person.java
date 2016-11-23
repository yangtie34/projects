package com.jhnu.framework.data.neo4j.entity;

import org.springframework.data.neo4j.annotation.GraphId;
import org.springframework.data.neo4j.annotation.NodeEntity;

@NodeEntity
public class Person {
	@GraphId
	private Long id;
	private String name;
	private Integer born;
	
	
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getBorn() {
		return born;
	}
	public void setBorn(Integer born) {
		this.born = born;
	}
	
	
}
