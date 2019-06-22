package me.lam.huyen.channel;

import me.lam.huyen.client.GitHubService;
import me.lam.huyen.model.Data;
import me.lam.huyen.model.GitContributorStat;
import me.lam.huyen.model.GitContributorStatList;
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
public class ContributorStatLoader {

	private Logger logger = LoggerFactory.getLogger(ContributorStatLoader.class);

	@Value("${app.loader.contributor.project_count}")
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
	@Scheduled(initialDelayString = "${app.loader.contributor.delay.inMilliSecond}",
			fixedDelayString = "${app.loader.contributor.delay.inMilliSecond}")
	public void fetchContributors() {
		List<Data> projects = dataService.findProjectsHaveNoContributor(projectCount);
		if (projects == null || projects.isEmpty()) {
			return;
		}
		logger.debug("Fetching contributors for projects: {}",
				projects.stream().map(Data::getValue).collect(Collectors.toList()));
		for (Data project : projects) {
			fetchContributorsAndSave(project);
		}
	}

	private void fetchContributorsAndSave(Data project) {
		String id = project.getObjectId();
		String repos = project.getValue();
		// Load commit statistics
		List<GitContributorStat> contributors = gitHubService.getContributorStatistics(repos);
		if (contributors == null || contributors.isEmpty()) {
			return;
		}
		// Save data and state
		GitContributorStatList result = new GitContributorStatList(contributors);
		dataService.saveContributorStats(id, result);
	}
}
