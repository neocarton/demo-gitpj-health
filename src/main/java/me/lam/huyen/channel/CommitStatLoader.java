package me.lam.huyen.channel;

import me.lam.huyen.client.GitHubService;
import me.lam.huyen.model.Data;
import me.lam.huyen.model.GitCommitStat;
import me.lam.huyen.model.GitCommitStatWeekly;
import me.lam.huyen.service.DataService;
import me.lam.huyen.service.StateService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommitStatLoader {

	private Logger logger = LoggerFactory.getLogger(CommitStatLoader.class);

	@Value("${app.loader.commit.project_count}")
	private int projectCount;

	@Autowired
	private GitHubService gitHubService;

	@Autowired
	private DataService dataService;

	@Autowired
	private StateService stateService;

	/**
	 * Handle event git projects loaded.
	 */
	@Scheduled(initialDelayString = "${app.loader.commit.delay.inMilliSecond}",
			fixedDelayString = "${app.loader.commit.delay.inMilliSecond}")
	public void fetchCommitStats() {
		List<Data> projects = dataService.findProjectsHaveNoCommit(projectCount);
		if (projects == null || projects.isEmpty()) {
			return;
		}
		logger.debug("Fetching commit statistics for projects: {}",
				projects.stream().map(Data::getValue).collect(Collectors.toList()));
		for (Data project : projects) {
			fetchCommitStatAndSave(project);
		}
	}

	private void fetchCommitStatAndSave(Data project) {
		String id = project.getObjectId();
		String repos = project.getValue();
		// Load commit statistics
		List<GitCommitStat> commitStatList = gitHubService.getCommitStatistics(repos);
		GitCommitStatWeekly result = new GitCommitStatWeekly(commitStatList);
		dataService.saveCommitStat(id, result);
	}
}
