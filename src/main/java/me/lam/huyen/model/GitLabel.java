package me.lam.huyen.model;

import java.util.Objects;

public class GitLabel {

	private String id;

	private String nodeId;

	private String name; // bug

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

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		GitLabel gitLabel = (GitLabel) o;
		return Objects.equals(id, gitLabel.id) &&
				Objects.equals(nodeId, gitLabel.nodeId) &&
				Objects.equals(name, gitLabel.name);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, nodeId, name);
	}

	@Override
	public String toString() {
		return "GitLabel{" +
				"id='" + id + '\'' +
				", nodeId='" + nodeId + '\'' +
				", name='" + name + '\'' +
				'}';
	}
}
