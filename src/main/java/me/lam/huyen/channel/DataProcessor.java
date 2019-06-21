package me.lam.huyen.channel;

import me.lam.huyen.model.GitCommitStatWeekly;
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

	/**
	 * Handle event git projects loaded.
	 */
	@StreamListener(GitProjectChannel.PROJECT_LOADED)
	public void handleProjectLoaded(GitProjectList projectList) {
		dataService.saveProjects(projectList);
	}

	/**
	 * Handle event git projects loaded.
	 */
	@StreamListener(GitProjectChannel.COMMIT_STAT_LOADED)
	public void handleCommitStatLoaded(Map<String, GitCommitStatWeekly> commitStats) {
		dataService.saveCommitStats(commitStats);
	}
}
