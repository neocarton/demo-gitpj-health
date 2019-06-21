package me.lam.huyen.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Collections;
import java.util.List;

public class GitProjectList {

	@JsonProperty("total_count")
	private Integer count;

	private Boolean incompleteResults;

	private List<GitProject> items = Collections.emptyList();

	private Integer page;

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	public Boolean getIncompleteResults() {
		return incompleteResults;
	}

	public void setIncompleteResults(Boolean incompleteResults) {
		this.incompleteResults = incompleteResults;
	}

	public List<GitProject> getItems() {
		return items;
	}

	public void setItems(List<GitProject> items) {
		this.items = items;
	}

	public Integer getPage() {
		return page;
	}

	public void setPage(Integer page) {
		this.page = page;
	}

	@Override
	public String toString() {
		return "GitProjectList{" +
				"count=" + count +
				", incompleteResults=" + incompleteResults +
				", items=" + items +
				", page=" + page +
				'}';
	}
}
