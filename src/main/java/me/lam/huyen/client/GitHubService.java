package me.lam.huyen.client;

import me.lam.huyen.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@Component
public class GitHubService implements GitHubClient {

	@Autowired
	private GitHubClient gitHubClient;

	@Override
	public Map<String, String> getURLs() {
		try {
			return gitHubClient.getURLs();
		}
		catch (Exception exc) {
			return Collections.emptyMap();
		}
	}

	@Override
	public GitProjectList searchRepositories(String query, String sort, String order, Integer page, Integer pageSize) {
		try {
			return gitHubClient.searchRepositories(query, sort, order, page, pageSize);
		}
		catch (Exception exc) {
			return new GitProjectList();
		}
	}

	@Override
	public List<GitCommitStat> getCommitStatistics(String repo) {
		try {
			return gitHubClient.getCommitStatistics(repo);
		}
		catch (Exception exc) {
			return Collections.emptyList();
		}
	}

	@Override
	public List<GitIssue> getIssues(String repo, String sort, String order, Integer page, Integer pageSize) {
		try {
			return gitHubClient.getIssues(repo, sort, order, page, pageSize);
		}
		catch (Exception exc) {
			return Collections.emptyList();
		}
	}

	@Override
	public List<GitPullRequest> getPullRequests(String repo, String sort, String order, Integer page, Integer pageSize) {
		try {
			return gitHubClient.getPullRequests(repo, sort, order, page, pageSize);
		}
		catch (Exception exc) {
			return Collections.emptyList();
		}
	}

	@Override
	public List<GitContributorStat> getContributorStatistics(String repo) {
		try {
			return gitHubClient.getContributorStatistics(repo);
		}
		catch (Exception exc) {
			return Collections.emptyList();
		}
	}
}
