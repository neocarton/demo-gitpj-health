package me.lam.huyen.model;

import java.util.Objects;

public class GitUser {

	private String id;

	private String nodeId;

	private String login;

	private String type;

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

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		GitUser gitUser = (GitUser) o;
		return Objects.equals(id, gitUser.id);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public String toString() {
		return "GitUser{" +
				"id='" + id + '\'' +
				", nodeId='" + nodeId + '\'' +
				", login='" + login + '\'' +
				", type='" + type + '\'' +
				'}';
	}
}
