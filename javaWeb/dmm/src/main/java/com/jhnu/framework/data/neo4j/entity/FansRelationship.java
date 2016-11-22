package com.jhnu.framework.data.neo4j.entity;

import org.springframework.data.neo4j.annotation.EndNode;
import org.springframework.data.neo4j.annotation.GraphId;
import org.springframework.data.neo4j.annotation.RelationshipEntity;
import org.springframework.data.neo4j.annotation.StartNode;

@RelationshipEntity(type="ACTED_IN")
public class FansRelationship {

	@GraphId
	private Long id;

	@StartNode
	private Person from;

	@EndNode
	private Movie to;

	private String roles;

	public FansRelationship() {
	}

	public FansRelationship(Person from, Movie to) {
		this.from = from;
		this.to = to;
		this.roles = from.getId() + "->" + to.getId();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Person getFrom() {
		return from;
	}

	public void setFrom(Person from) {
		this.from = from;
	}

	public Movie getTo() {
		return to;
	}

	public void setTo(Movie to) {
		this.to = to;
	}

	public String getRoles() {
		return roles;
	}

	public void setRoles(String roles) {
		this.roles = roles;
	}

}
