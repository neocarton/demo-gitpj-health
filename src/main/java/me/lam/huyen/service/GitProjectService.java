package me.lam.huyen.service;

import me.lam.huyen.client.GitHubClient;
import me.lam.huyen.model.GitCommitStat;
import me.lam.huyen.model.GitCommitStatWeekly;
import me.lam.huyen.model.GitProject;
import me.lam.huyen.model.GitProjectList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class GitProjectService {

	private Logger logger = LoggerFactory.getLogger(GitProjectService.class);

	@Autowired
	private GitHubClient gitHubClient;

	public GitProjectList fetchTopProjects(int page, int pageSize) {
		logger.debug("Get git projects from github.com at page {} with page-size {}", page, pageSize);
		GitProjectList result = gitHubClient.searchRepositories("language:javascript", "stars", "desc", page, pageSize);
		logger.debug("Got {} projects from github.com", result.getItems().size());
		return result;
	}

	public Map<String, GitCommitStatWeekly> fetchCommitStats(GitProjectList projectList) {
		List<GitProject> projects = projectList.getItems();
		if (projects == null || projects.isEmpty()) {
			return Collections.emptyMap();
		}
		Map<String, GitCommitStatWeekly> result = new HashMap<>();
		for (GitProject project : projects) {
			String id = project.getId();
			String owner = project.getOwner().getLogin();
			String repos = project.getName();
			GitCommitStatWeekly commitStat = fetchCommitStat(owner, repos);
			result.put(id, commitStat);
		}
		return result;
	}

	private GitCommitStatWeekly fetchCommitStat(String owner, String repos) {
		logger.debug("Fetch commit statistic for project {}/{}", owner, repos);
		List<GitCommitStat> result = gitHubClient.getCommitStatistics(owner, repos);
		return new GitCommitStatWeekly(result);
	}
}
