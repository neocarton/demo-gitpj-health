package me.lam.huyen.channel;

import me.lam.huyen.model.GitCommitStatWeekly;
import me.lam.huyen.model.GitTopIssues;
import me.lam.huyen.model.GitProjectList;
import me.lam.huyen.service.DataService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * Data service listens to all kind of messages and store them
 */
@Service
public class DataProcessor {

	private Logger logger = LoggerFactory.getLogger(DataProcessor.class);

	@Autowired
	private DataService dataService;

	@StreamListener(GitProjectChannel.PROJECT_LOADED)
	public void handleProjectLoaded(GitProjectList projectList) {
		dataService.saveProjects(projectList);
	}

	@StreamListener(GitProjectChannel.COMMIT_STAT_LOADED)
	public void handleCommitStatLoaded(Map<String, GitCommitStatWeekly> commitStats) {
		dataService.saveCommitStats(commitStats);
	}

	@StreamListener(GitProjectChannel.ISSUE_LOADED)
	public void handleIssueLoaded(Map<String, GitTopIssues> issues) {
		dataService.saveIssues(issues);
	}
}
