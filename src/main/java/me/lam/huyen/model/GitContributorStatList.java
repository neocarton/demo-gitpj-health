package me.lam.huyen.model;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class GitContributorStatList {

	private List<GitContributorStat> items = Collections.emptyList();

	private int totalContributors = 0;

	private int totalCommits = 0;

	public GitContributorStatList() {
	}

	public GitContributorStatList(List<GitContributorStat> items) {
		this.items = items;
		init();
	}

	private void init() {
		for (GitContributorStat item : items) {
			totalContributors++;
			totalCommits += item.getTotal();
		}
	}

	public List<GitContributorStat> getItems() {
		return items;
	}

	public void setItems(List<GitContributorStat> items) {
		this.items = items;
	}

	public int getTotalContributors() {
		return totalContributors;
	}

	public void setTotalContributors(int totalContributors) {
		this.totalContributors = totalContributors;
	}

	public int getTotalCommits() {
		return totalCommits;
	}

	public void setTotalCommits(int totalCommits) {
		this.totalCommits = totalCommits;
	}

	public Float getAvgCommits() {
		return (totalContributors != 0) ? ((float) totalCommits / totalContributors) : null;
	}

	@Override
	public String toString() {
		return "GitContributorStatList{" +
				"items=" + items +
				", totalContributors=" + totalContributors +
				", totalCommits=" + totalCommits +
				'}';
	}
}
