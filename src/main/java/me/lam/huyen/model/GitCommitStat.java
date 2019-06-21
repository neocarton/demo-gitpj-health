package me.lam.huyen.model;

import java.util.List;

public class GitCommitStat {

	private List<Integer> days;

	private Integer total;

	private Long week;

	public List<Integer> getDays() {
		return days;
	}

	public void setDays(List<Integer> days) {
		this.days = days;
	}

	public Integer getTotal() {
		return total;
	}

	public void setTotal(Integer total) {
		this.total = total;
	}

	public Long getWeek() {
		return week;
	}

	public void setWeek(Long week) {
		this.week = week;
	}

	@Override
	public String toString() {
		return "GitCommitStat{" +
				"days=" + days +
				", total=" + total +
				", week=" + week +
				'}';
	}
}
