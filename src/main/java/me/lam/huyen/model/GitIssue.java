package me.lam.huyen.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import me.lam.huyen.config.ZonedDateTimeDeserializer;

import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Objects;

public class GitIssue {

	private String id;

	private String nodeId;

	@JsonProperty("html_url")
	private String url;

	private String state;

	private String title;

	private List<GitLabel> labels;

	private GitUser user;

	private GitUser assignee;

	private List<GitUser> assignees;

	private int comments = 0;

	@JsonProperty("closed_at")
    @JsonDeserialize(using = ZonedDateTimeDeserializer.class)
	private ZonedDateTime closedAt;

	@JsonProperty("created_at")
    @JsonDeserialize(using = ZonedDateTimeDeserializer.class)
	private ZonedDateTime createdAt;

	@JsonProperty("updated_at")
    @JsonDeserialize(using = ZonedDateTimeDeserializer.class)
	private ZonedDateTime updatedAt;

	private GitPullRequest pullRequest;

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

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public List<GitLabel> getLabels() {
		return labels;
	}

	public void setLabels(List<GitLabel> labels) {
		this.labels = labels;
	}

	public GitUser getUser() {
		return user;
	}

	public void setUser(GitUser user) {
		this.user = user;
	}

	public GitUser getAssignee() {
		return assignee;
	}

	public void setAssignee(GitUser assignee) {
		this.assignee = assignee;
	}

	public List<GitUser> getAssignees() {
		return assignees;
	}

	public void setAssignees(List<GitUser> assignees) {
		this.assignees = assignees;
	}

	public int getComments() {
		return comments;
	}

	public void setComments(Integer comments) {
		this.comments = (comments != null) ? comments : 0;
	}

	public ZonedDateTime getClosedAt() {
		return closedAt;
	}

	public void setClosedAt(ZonedDateTime closedAt) {
		this.closedAt = closedAt;
	}

	public ZonedDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(ZonedDateTime createdAt) {
		this.createdAt = createdAt;
	}

	public ZonedDateTime getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(ZonedDateTime updatedAt) {
		this.updatedAt = updatedAt;
	}

	public GitPullRequest getPullRequest() {
		return pullRequest;
	}

	public void setPullRequest(GitPullRequest pullRequest) {
		this.pullRequest = pullRequest;
	}

	public boolean isOpen() {
		return (createdAt == null);
	}

	// second
	public long getOpenTime() {
		return getDuration(createdAt, (closedAt != null) ? closedAt : ZonedDateTime.now(), 0);
	}

	// second
	public long getCloseTime() {
		return getDuration(createdAt, closedAt, 0);
	}

	// second
	protected long getDuration(ZonedDateTime from, ZonedDateTime to, long fallbackValue) {
		if (to == null) {
			return fallbackValue;
		}
		return ChronoUnit.SECONDS.between(from, to);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		GitIssue gitIssue = (GitIssue) o;
		return Objects.equals(id, gitIssue.id);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public String toString() {
		return this.getClass().getSimpleName() + "{" +
				"id='" + id + '\'' +
				", nodeId='" + nodeId + '\'' +
				", url='" + url + '\'' +
				", state='" + state + '\'' +
				", title='" + title + '\'' +
				", labels=" + labels +
				", user=" + user +
				", assignee=" + assignee +
				", assignees=" + assignees +
				", comments=" + comments +
				", closedAt=" + closedAt +
				", createdAt=" + createdAt +
				", updatedAt=" + updatedAt +
				", pullRequest=" + pullRequest +
				'}';
	}
}
