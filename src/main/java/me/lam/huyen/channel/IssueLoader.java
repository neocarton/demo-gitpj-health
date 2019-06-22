package me.lam.huyen.channel;

import me.lam.huyen.client.GitHubService;
import me.lam.huyen.model.Data;
import me.lam.huyen.model.GitIssue;
import me.lam.huyen.model.GitTopIssues;
import me.lam.huyen.service.DataService;
import me.lam.huyen.service.StateService;
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
public class IssueLoader {

	private Logger logger = LoggerFactory.getLogger(IssueLoader.class);

	@Value("${app.loader.issue.project_count}")
	private int projectCount;

	@Value("${app.loader.issue.page_size}")
	private int pageSize;

	@Value("${app.loader.issue.max_result}")
	private int maxResult;

	@Autowired
	private GitHubService gitHubService;

	@Autowired
	private DataService dataService;

	@Autowired
	private StateService stateService;

	/**
	 * Handle event git projects loaded.
	 */
	@Scheduled(initialDelayString = "${app.loader.issue.delay.inMilliSecond}",
			fixedDelayString = "${app.loader.issue.delay.inMilliSecond}")
	public void fetchIssues() {
		List<Data> projects = dataService.findProjectsHaveNoIssue(projectCount);
		if (projects == null || projects.isEmpty()) {
			return;
		}
		logger.debug("Fetching issues for projects: {}",
				projects.stream().map(Data::getValue).collect(Collectors.toList()));
		for (Data project : projects) {
			fetchIssuesAndSave(project);
		}
	}

	private void fetchIssuesAndSave(Data project) {
		String id = project.getObjectId();
		int page = 1;
		List<GitIssue> issues = new ArrayList<>(maxResult);
		while (issues.size() < maxResult) {
			String repos = project.getValue();
			// Load commit statistics
			List<GitIssue> curIssues = gitHubService.getIssues(repos, "created_at", "desc", page++, pageSize); // Get top 100 issues for evaluation
			if (curIssues == null || curIssues.isEmpty()) {
				break;
			}
			// Add to result lists
			for (GitIssue issue : curIssues) {
				if (issue.getPullRequest() == null) {
					issues.add(issue);
				}
			}
		}
		// Save data and state
		GitTopIssues topIssues = new GitTopIssues(issues);
		dataService.saveIssues(id, topIssues);
	}
}
