package me.lam.huyen.channel;

import me.lam.huyen.client.GitHubClient;
import me.lam.huyen.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class IssueLoader {

	private Logger logger = LoggerFactory.getLogger(IssueLoader.class);

	@Autowired
	private GitHubClient gitHubClient;

	@Autowired
	private IssueLoadedGateway issueLoadedGateway;

	/**
	 * Handle event git projects loaded.
	 */
	@StreamListener(GitProjectChannel.PROJECT_LOADED)
	public void handle(GitProjectList projectList) {
		Map<String, GitTopIssues> result = fetchIssues(projectList);
		issueLoadedGateway.send(result);
	}

	private Map<String, GitTopIssues> fetchIssues(GitProjectList projectList) {
		List<GitProject> projects = projectList.getItems();
		if (projects == null || projects.isEmpty()) {
			return Collections.emptyMap();
		}
		Map<String, GitTopIssues> result = new HashMap<>();
		for (GitProject project : projects) {
			String id = project.getId();
			String owner = project.getOwner().getLogin();
			String repos = project.getName();
			GitTopIssues issues = fetchIssues(owner, repos);
			result.put(id, issues);
		}
		if (result == null) {
			return Collections.emptyMap();
		}
		return result;
	}

	private GitTopIssues fetchIssues(String owner, String repos) {
		logger.debug("Fetch issues for project {}/{}", owner, repos);
		List<GitIssue> result = gitHubClient.getIssues(owner, repos, "created_at", "desc", 1, 100); // Get 100 issues
		return new GitTopIssues(result);
	}
}
