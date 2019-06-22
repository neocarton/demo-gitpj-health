package me.lam.huyen.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class GitPullRequest {

	@JsonProperty("html_url")
	private String url;

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	@Override
	public String toString() {
		return "GitPullRequest{" +
				"url='" + url + '\'' +
				'}';
	}
}
