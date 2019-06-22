package me.lam.huyen.model;

import java.sql.Date;

public class GitPullRequest extends GitIssue {

    private Date mergedAt;

    public Date getMergedAt() {
        return mergedAt;
    }

    public void setMergedAt(Date mergedAt) {
        this.mergedAt = mergedAt;
    }

    // second
    public long getOpenTime() {
        Date createdAt = getCreatedAt();
        long createdTime = (createdAt != null) ? createdAt.getTime() : 0;
        long closeTime = (mergedAt != null) ? mergedAt.getTime() : System.currentTimeMillis();
        return (closeTime - createdTime) / 1000;
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
                ", openTime=" + getOpenTime() +
                '}';
    }
}
