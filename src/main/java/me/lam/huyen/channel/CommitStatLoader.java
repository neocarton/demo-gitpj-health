package me.lam.huyen.channel;

import me.lam.huyen.model.GitCommitStatWeekly;
import me.lam.huyen.model.GitProjectList;
import me.lam.huyen.service.GitProjectService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class CommitStatLoader {

	private Logger logger = LoggerFactory.getLogger(CommitStatLoader.class);

	@Autowired
	private GitProjectService gitProjectService;

	@Autowired
	private CommitStatLoadedGateway commitStatLoadedGateway;

	/**
	 * Handle event git projects loaded.
	 */
	@StreamListener(GitProjectChannel.PROJECT_LOADED)
	public void handle(GitProjectList projectList) {
		fetchCommitStats(projectList);
	}

	private void fetchCommitStats(GitProjectList projectList) {
		Map<String, GitCommitStatWeekly> commitStats = gitProjectService.fetchCommitStats(projectList);
		if (commitStats == null || commitStats.isEmpty()) {
			return;
		}
		commitStatLoadedGateway.send(commitStats);
	}
}
