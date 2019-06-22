package me.lam.huyen.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Collections;
import java.util.List;

public class GitContributorStat {

	private GitUser author;

	private Integer total;

	private List<Week> weeks = Collections.emptyList();

	public GitUser getAuthor() {
		return author;
	}

	public void setAuthor(GitUser author) {
		this.author = author;
	}

	public Integer getTotal() {
		return total;
	}

	public void setTotal(Integer total) {
		this.total = total;
	}

	public List<Week> getWeeks() {
		return weeks;
	}

	public void setWeeks(List<Week> weeks) {
		this.weeks = (weeks != null) ? weeks : Collections.emptyList();
	}

	@Override
	public String toString() {
		return "GitContributorStat{" +
				"author=" + author +
				", total=" + total +
				", weeks=" + weeks +
				'}';
	}

	public static class Week {
		@JsonProperty("w")
		private Long weekStartTime;

		@JsonProperty("a")
		private Long addedCount;

		@JsonProperty("d")
		private Long deletedCount;

		@JsonProperty("c")
		private Integer commitCount;

		public Long getWeekStartTime() {
			return weekStartTime;
		}

		public void setWeekStartTime(Long weekStartTime) {
			this.weekStartTime = weekStartTime;
		}

		public Long getAddedCount() {
			return addedCount;
		}

		public void setAddedCount(Long addedCount) {
			this.addedCount = addedCount;
		}

		public Long getDeletedCount() {
			return deletedCount;
		}

		public void setDeletedCount(Long deletedCount) {
			this.deletedCount = deletedCount;
		}

		public Integer getCommitCount() {
			return commitCount;
		}

		public void setCommitCount(Integer commitCount) {
			this.commitCount = commitCount;
		}
	}
}
