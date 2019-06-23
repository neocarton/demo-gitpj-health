package me.lam.huyen.channel;

import me.lam.huyen.client.GitHubClient;
import me.lam.huyen.model.Data;
import me.lam.huyen.model.GitPullRequest;
import me.lam.huyen.model.GitTopIssues;
import me.lam.huyen.service.DataService;
import me.lam.huyen.service.StateService;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PullRequestLoader {

	private Logger logger = LoggerFactory.getLogger(PullRequestLoader.class);

	@Value("${app.loader.pull_request.project_count}")
	private int projectCount;

	@Value("${app.loader.pull_request.page_size}")
	private int pageSize;

	@Value("${app.loader.pull_request.max_result}")
	private int maxResult;

	@Autowired
	private GitHubClient gitHubClient;

	@Autowired
	private DataService dataService;

	@Autowired
	private StateService stateService;

	/**
	 * Handle event git projects loaded.
	 */
	@Scheduled(initialDelayString = "${app.loader.pull_request.delay.inMilliSecond}",
			fixedDelayString = "${app.loader.pull_request.delay.inMilliSecond}")
	public void fetchPullRequests() {
		List<Data> projects = dataService.findProjectsHaveNoPullRequest(projectCount);
		if (projects == null || projects.isEmpty()) {
			return;
		}
		logger.debug("Fetching pull requests for projects: {}",
				projects.stream().map(Data::getValue).collect(Collectors.toList()));
		for (Data project : projects) {
			fetchPullRequestsAndSave(project);
		}
	}

	private void fetchPullRequestsAndSave(Data project) {
		String id = project.getObjectId();
		String repos = project.getValue();
		try {
			int page = 1;
			List<GitPullRequest> pullRequests = new ArrayList<>(maxResult);
			while (pullRequests.size() < maxResult) {
				// Load commit statistics
				List<GitPullRequest> curPullRequests = gitHubClient.getPullRequests(repos, "all", "created", "desc", page++, pageSize);
				if (curPullRequests == null || curPullRequests.isEmpty()) {
					break;
				}
				// Add to result lists
				for (GitPullRequest pullRequest : curPullRequests) {
					if (pullRequest.getPullRequest() == null) {
						pullRequests.add(pullRequest);
					}
				}
			}
			// Save data and state
			GitTopIssues<GitPullRequest> topPullRequests = new GitTopIssues<>(pullRequests);
			dataService.savePullRequests(id, topPullRequests);
		}
		catch (Exception exc) {
			logger.warn("Failed to fetch pull requests for project '{}': {}", repos,
					ExceptionUtils.getRootCauseMessage(exc));
			logger.trace("Exception", exc);
			dataService.savePullRequestsError(id, exc);
		}
	}
}
