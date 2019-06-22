package me.lam.huyen.channel;

import me.lam.huyen.client.GitHubClient;
import me.lam.huyen.model.GitCommitStat;
import me.lam.huyen.model.GitCommitStatWeekly;
import me.lam.huyen.model.GitProject;
import me.lam.huyen.model.GitProjectList;
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
public class CommitStatLoader {

	private Logger logger = LoggerFactory.getLogger(CommitStatLoader.class);

	@Autowired
	private GitHubClient gitHubClient;

	@Autowired
	private CommitStatLoadedGateway commitStatLoadedGateway;

	/**
	 * Handle event git projects loaded.
	 */
	@StreamListener(GitProjectChannel.PROJECT_LOADED)
	public void handle(GitProjectList projectList) {
		Map<String, GitCommitStatWeekly> result = fetchCommitStats(projectList);
		commitStatLoadedGateway.send(result);
	}

	private Map<String, GitCommitStatWeekly> fetchCommitStats(GitProjectList projectList) {
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
		if (result == null) {
			return Collections.emptyMap();
		}
		return result;
	}

	private GitCommitStatWeekly fetchCommitStat(String owner, String repos) {
		logger.debug("Fetch commit statistic for project {}/{}", owner, repos);
		List<GitCommitStat> result = gitHubClient.getCommitStatistics(owner, repos);
		return new GitCommitStatWeekly(result);
	}

}
