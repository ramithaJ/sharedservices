package com.wiley.gr.ace.authorservices.persistence.entity;

// Generated Jun 26, 2015 10:24:59 AM by Hibernate Tools 4.0.0

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Databasechangelog generated by hbm2java
 */
@Entity
@Table(name = "DATABASECHANGELOG")
public class Databasechangelog implements java.io.Serializable {

	private DatabasechangelogId id;

	public Databasechangelog() {
	}

	public Databasechangelog(DatabasechangelogId id) {
		this.id = id;
	}

	@EmbeddedId
	@AttributeOverrides({
			@AttributeOverride(name = "id", column = @Column(name = "ID", nullable = false)),
			@AttributeOverride(name = "author", column = @Column(name = "AUTHOR", nullable = false)),
			@AttributeOverride(name = "filename", column = @Column(name = "FILENAME", nullable = false)),
			@AttributeOverride(name = "dateexecuted", column = @Column(name = "DATEEXECUTED", nullable = false)),
			@AttributeOverride(name = "orderexecuted", column = @Column(name = "ORDEREXECUTED", nullable = false, precision = 10, scale = 0)),
			@AttributeOverride(name = "exectype", column = @Column(name = "EXECTYPE", nullable = false, length = 10)),
			@AttributeOverride(name = "md5sum", column = @Column(name = "MD5SUM", length = 35)),
			@AttributeOverride(name = "description", column = @Column(name = "DESCRIPTION")),
			@AttributeOverride(name = "comments", column = @Column(name = "COMMENTS")),
			@AttributeOverride(name = "tag", column = @Column(name = "TAG")),
			@AttributeOverride(name = "liquibase", column = @Column(name = "LIQUIBASE", length = 20)) })
	public DatabasechangelogId getId() {
		return this.id;
	}

	public void setId(DatabasechangelogId id) {
		this.id = id;
	}

}