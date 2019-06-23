package me.lam.huyen.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import me.lam.huyen.config.ZonedDateTimeDeserializer;

import java.time.ZonedDateTime;

public class GitPullRequest extends GitIssue {

    @JsonProperty("merged_at")
    @JsonDeserialize(using = ZonedDateTimeDeserializer.class)
    private ZonedDateTime mergedAt;

    public ZonedDateTime getMergedAt() {
        return mergedAt;
    }

    public void setMergedAt(ZonedDateTime mergedAt) {
        this.mergedAt = mergedAt;
    }

    public boolean isOpen() {
        return (mergedAt == null);
    }

    // second
    public long getOpenTime() {
        return getDuration(getCreatedAt(), (mergedAt != null) ? mergedAt : ZonedDateTime.now(), 0);
    }

    // second
    public long getCloseTime() {
        return getDuration(getCreatedAt(), mergedAt, 0);
    }

    @Override
    public String toString() {
        return "GitPullRequest{" +
                "id='" + getId() + '\'' +
                ", nodeId='" + getNodeId() + '\'' +
                ", url='" + getUrl() + '\'' +
                ", state='" + getState() + '\'' +
                ", title='" + getTitle() + '\'' +
                ", labels=" + getLabels() +
                ", user=" + getUser() +
                ", assignee=" + getAssignee() +
                ", assignees=" + getAssignees() +
                ", comments=" + getComments() +
                ", closedAt=" + getClosedAt() +
                ", createdAt=" + getCreatedAt() +
                ", updatedAt=" + getUpdatedAt() +
                ", mergedAt=" + mergedAt +
                ", pullRequest=" + getPullRequest() +
                '}';
    }
}
