package me.lam.huyen.model;

import java.util.List;

public class GitTopIssues {

	private List<GitIssue> items;

	private int issueCount = 0;

	private long totalOpenTime = 0; // ms

	private int openIssueCount = 0;

	private int closeIssueCount = 0;

	public GitTopIssues() {
	}

	public GitTopIssues(List<GitIssue> items) {
		this.items = items;
		init();
	}

	private void init() {
		if (items == null || items.isEmpty()) {
			return;
		}
		for (GitIssue item : items) {
			issueCount++;
			totalOpenTime += item.getOpenTime();
			boolean open = "open".equals(item.getState());
			openIssueCount += (open) ? 1 : 0;
			closeIssueCount += (!open) ? 1 : 0;
		}
	}

	public List<GitIssue> getItems() {
		return items;
	}

	public void setItems(List<GitIssue> items) {
		this.items = items;
	}

	public int getIssueCount() {
		return issueCount;
	}

	public void setIssueCount(int issueCount) {
		this.issueCount = issueCount;
	}

	public long getTotalOpenTime() {
		return totalOpenTime;
	}

	public void setTotalOpenTime(long totalOpenTime) {
		this.totalOpenTime = totalOpenTime;
	}

	public int getOpenIssueCount() {
		return openIssueCount;
	}

	public void setOpenIssueCount(int openIssueCount) {
		this.openIssueCount = openIssueCount;
	}

	public int getCloseIssueCount() {
		return closeIssueCount;
	}

	public void setCloseIssueCount(int closeIssueCount) {
		this.closeIssueCount = closeIssueCount;
	}

	public Float getAvgOpenTime() {
		return (issueCount != 0) ? ((float) totalOpenTime / issueCount) : null;
	}

	public Float getOpenRatio() {
		return (issueCount != 0) ? ((float) openIssueCount / issueCount) : null;
	}

	public Float getCloseRatio() {
		return (issueCount != 0) ? ((float) closeIssueCount / issueCount) : null;
	}

	@Override
	public String toString() {
		return "GitTopIssues{" +
				"items=" + items +
				", issueCount=" + issueCount +
				", totalOpenTime=" + totalOpenTime +
				", openIssueCount=" + openIssueCount +
				", closeIssueCount=" + closeIssueCount +
				", avgOpenTime=" + getAvgOpenTime() +
				", openRatio=" + getOpenRatio() +
				", closeRatio=" + getCloseRatio() +
				'}';
	}
}
