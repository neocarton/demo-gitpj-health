package me.lam.huyen.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Objects;

@Entity
public class ReposHealthScore {

	@Id
	@Column
	private String objectId;

	@Column
	private String owner;

	@Column
	private String repos;

	@Column
	private Float healthScore;

	@Column
	private Float healthScoreByAveraging;

	@Column
	private Float forkRatio;

	@Column
	private Float starRatio;

	@Column
	private Float commitsPerDayRatio;

	@Column
	private Float commitsPerDayPerPersonRatio;

	@Column(name = "pr_open_time_ratio")
	private Float pullOpenTimeRatio;

	@Column(name = "pr_merge_time_ratio")
	private Float pullMergeTimeRatio;

	@Column(name = "iss_open_time_ratio")
	private Float issueOpenTimeRatio;

	@Column(name = "iss_close_time_ratio")
	private Float issueCloseTimeRatio;

	public String getObjectId() {
		return objectId;
	}

	public void setObjectId(String objectId) {
		this.objectId = objectId;
	}

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	public String getRepos() {
		return repos;
	}

	public void setRepos(String repos) {
		this.repos = repos;
	}

	public Float getHealthScore() {
		return healthScore;
	}

	public void setHealthScore(Float healthScore) {
		this.healthScore = healthScore;
	}

	public Float getHealthScoreByAveraging() {
		return healthScoreByAveraging;
	}

	public void setHealthScoreByAveraging(Float healthScoreByAveraging) {
		this.healthScoreByAveraging = healthScoreByAveraging;
	}

	public Float getForkRatio() {
		return forkRatio;
	}

	public void setForkRatio(Float forkRatio) {
		this.forkRatio = forkRatio;
	}

	public Float getStarRatio() {
		return starRatio;
	}

	public void setStarRatio(Float starRatio) {
		this.starRatio = starRatio;
	}

	public Float getCommitsPerDayRatio() {
		return commitsPerDayRatio;
	}

	public void setCommitsPerDayRatio(Float commitsPerDayRatio) {
		this.commitsPerDayRatio = commitsPerDayRatio;
	}

	public Float getCommitsPerDayPerPersonRatio() {
		return commitsPerDayPerPersonRatio;
	}

	public void setCommitsPerDayPerPersonRatio(Float commitsPerDayPerPersonRatio) {
		this.commitsPerDayPerPersonRatio = commitsPerDayPerPersonRatio;
	}

	public Float getPullOpenTimeRatio() {
		return pullOpenTimeRatio;
	}

	public void setPullOpenTimeRatio(Float pullOpenTimeRatio) {
		this.pullOpenTimeRatio = pullOpenTimeRatio;
	}

	public Float getPullMergeTimeRatio() {
		return pullMergeTimeRatio;
	}

	public void setPullMergeTimeRatio(Float pullMergeTimeRatio) {
		this.pullMergeTimeRatio = pullMergeTimeRatio;
	}

	public Float getIssueOpenTimeRatio() {
		return issueOpenTimeRatio;
	}

	public void setIssueOpenTimeRatio(Float issueOpenTimeRatio) {
		this.issueOpenTimeRatio = issueOpenTimeRatio;
	}

	public Float getIssueCloseTimeRatio() {
		return issueCloseTimeRatio;
	}

	public void setIssueCloseTimeRatio(Float issueCloseTimeRatio) {
		this.issueCloseTimeRatio = issueCloseTimeRatio;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		ReposHealthScore that = (ReposHealthScore) o;
		return Objects.equals(objectId, that.objectId);
	}

	@Override
	public int hashCode() {
		return Objects.hash(objectId);
	}

	@Override
	public String toString() {
		return "ReposHealthScore{" +
				"objectId='" + objectId + '\'' +
				", owner='" + owner + '\'' +
				", repos='" + repos + '\'' +
				", healthScore=" + healthScore +
				", healthScoreByAveraging=" + healthScoreByAveraging +
				", forkRatio=" + forkRatio +
				", starRatio=" + starRatio +
				", commitsPerDayRatio=" + commitsPerDayRatio +
				", commitsPerDayPerPersonRatio=" + commitsPerDayPerPersonRatio +
				", pullOpenTimeRatio=" + pullOpenTimeRatio +
				", pullMergeTimeRatio=" + pullMergeTimeRatio +
				", issueOpenTimeRatio=" + issueOpenTimeRatio +
				", issueCloseTimeRatio=" + issueCloseTimeRatio +
				'}';
	}
}
