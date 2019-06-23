package me.lam.huyen.model;

import java.util.List;

public class GitTopIssues<T extends GitIssue> {

	private List<T> items;

	private int issueCount = 0;

	private long totalOpenTime = 0; // second

	private long totalCloseTime = 0; // second

	private int openIssueCount = 0;

	private int closeIssueCount = 0;

	private long totalComments = 0;

	public GitTopIssues() {
	}

	public GitTopIssues(List<T> items) {
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
			totalCloseTime += item.getCloseTime();
			boolean open = item.isOpen();
			openIssueCount += (open) ? 1 : 0;
			closeIssueCount += (!open) ? 1 : 0;
			totalComments += item.getComments();
		}
	}

	public List<T> getItems() {
		return items;
	}

	public void setItems(List<T> items) {
		this.items = items;
	}

	public int getIssueCount() {
		return issueCount;
	}

	public void setIssueCount(int issueCount) {
		this.issueCount = issueCount;
	}

	// seconds
	public long getTotalOpenTime() {
		return totalOpenTime;
	}

	public void setTotalOpenTime(long totalOpenTime) {
		this.totalOpenTime = totalOpenTime;
	}

	public long getTotalCloseTime() {
		return totalCloseTime;
	}

	public void setTotalCloseTime(long totalCloseTime) {
		this.totalCloseTime = totalCloseTime;
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

	public long getTotalComments() {
		return totalComments;
	}

	public void setTotalComments(long totalComments) {
		this.totalComments = totalComments;
	}

	// seconds
	public Long getAvgOpenTime() {
		return (issueCount != 0) ? (totalOpenTime / issueCount) : null;
	}

	// seconds
	public Long getAvgCloseTime() {
		return (issueCount != 0) ? (totalCloseTime / issueCount) : null;
	}

	public Float getOpenRatio() {
		return (issueCount != 0) ? ((float) openIssueCount / issueCount) : null;
	}

	public Float getCloseRatio() {
		return (issueCount != 0) ? ((float) closeIssueCount / issueCount) : null;
	}

	public Float getAvgComments() {
		return (issueCount != 0) ? ((float) totalComments / issueCount) : null;
	}

	@Override
	public String toString() {
		return "GitTopIssues{" +
				"items=" + items +
				", issueCount=" + issueCount +
				", totalOpenTime=" + totalOpenTime +
				", totalCloseTime=" + totalCloseTime +
				", openIssueCount=" + openIssueCount +
				", closeIssueCount=" + closeIssueCount +
				", avgOpenTime=" + getAvgOpenTime() +
				", openRatio=" + getOpenRatio() +
				", closeRatio=" + getCloseRatio() +
				", avgComments=" + getAvgComments() +
				'}';
	}
}
