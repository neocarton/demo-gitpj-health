package me.lam.huyen.model;

import java.util.Collections;
import java.util.List;

public class GitCommitStatWeekly {

	private List<GitCommitStat> items = Collections.emptyList();

	private int dayCount = 0;

	private int weekCount = 0;

	private int totalCommit = 0;

	public GitCommitStatWeekly() {
	}

	public GitCommitStatWeekly(List<GitCommitStat> items) {
		this.items = items;
		init();
	}

	private void init() {
		if (items == null || items.isEmpty()) {
			return;
		}
		weekCount = items.size();
		dayCount = weekCount * 7;
		totalCommit = items.stream().map(GitCommitStat::getTotal)
				.reduce(0, (output, weekCommitCount) -> output + weekCommitCount);
		for (GitCommitStat item : items) {
			weekCount++;
			dayCount += item.getDays().size();
			totalCommit += item.getTotal();
		}
	}

	public List<GitCommitStat> getItems() {
		return items;
	}

	public void setItems(List<GitCommitStat> items) {
		this.items = items;
	}

	public int getDayCount() {
		return dayCount;
	}

	public void setDayCount(int dayCount) {
		this.dayCount = dayCount;
	}

	public int getWeekCount() {
		return weekCount;
	}

	public void setWeekCount(int weekCount) {
		this.weekCount = weekCount;
	}

	public int getTotalCommit() {
		return totalCommit;
	}

	public void setTotalCommit(int totalCommit) {
		this.totalCommit = totalCommit;
	}

	public int getAvgCommitPerWeek() {
		return totalCommit / weekCount;
	}

	public int getAvgCommitPerDay() {
		return totalCommit / dayCount;
	}

	@Override
	public String toString() {
		return "GitCommitStatWeekly{" +
				"items=" + items +
				", dayCount=" + dayCount +
				", weekCount=" + weekCount +
				", totalCommit=" + totalCommit +
				", avgCommitPerWeek=" + getAvgCommitPerWeek() +
				", avgCommitPerDay=" + getAvgCommitPerDay() +
				'}';
	}
}
