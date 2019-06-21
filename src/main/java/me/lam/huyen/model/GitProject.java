package me.lam.huyen.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;
import java.util.Objects;

public class GitProject {

	private String id;

	private String nodeId;

	private String name;

	private String fullName;

	private GitUser owner;

	@JsonProperty("html_url")
	private String url;

	private String description;

	private String language;

	private Long size;

	private Integer forks;

	/**
	 * stars
	 */
	private Integer watchers;

	private Integer score;

	private Boolean archived;

	private Boolean disabled;

	private Date createdAt;

	private Date pushedAt;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getNodeId() {
		return nodeId;
	}

	public void setNodeId(String nodeId) {
		this.nodeId = nodeId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public GitUser getOwner() {
		return owner;
	}

	public void setOwner(GitUser owner) {
		this.owner = owner;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public Long getSize() {
		return size;
	}

	public void setSize(Long size) {
		this.size = size;
	}

	public Integer getForks() {
		return forks;
	}

	public void setForks(Integer forks) {
		this.forks = forks;
	}

	public Integer getWatchers() {
		return watchers;
	}

	public void setWatchers(Integer watchers) {
		this.watchers = watchers;
	}

	public Integer getScore() {
		return score;
	}

	public void setScore(Integer score) {
		this.score = score;
	}

	public Boolean getArchived() {
		return archived;
	}

	public void setArchived(Boolean archived) {
		this.archived = archived;
	}

	public Boolean getDisabled() {
		return disabled;
	}

	public void setDisabled(Boolean disabled) {
		this.disabled = disabled;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public Date getPushedAt() {
		return pushedAt;
	}

	public void setPushedAt(Date pushedAt) {
		this.pushedAt = pushedAt;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		GitProject that = (GitProject) o;
		return Objects.equals(id, that.id);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public String toString() {
		return "GitProject{" +
				"id='" + id + '\'' +
				", nodeId='" + nodeId + '\'' +
				", name='" + name + '\'' +
				", fullName='" + fullName + '\'' +
				", owner=" + owner +
				", url='" + url + '\'' +
				", description='" + description + '\'' +
				", language='" + language + '\'' +
				", size=" + size +
				", forks=" + forks +
				", watchers=" + watchers +
				", score=" + score +
				", archived=" + archived +
				", disabled=" + disabled +
				", createdAt=" + createdAt +
				", pushedAt=" + pushedAt +
				'}';
	}
}
