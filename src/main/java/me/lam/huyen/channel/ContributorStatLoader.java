package me.lam.huyen.channel;

import me.lam.huyen.client.GitHubClient;
import me.lam.huyen.model.Data;
import me.lam.huyen.model.GitContributorStat;
import me.lam.huyen.model.GitContributorStatList;
import me.lam.huyen.service.DataService;
import me.lam.huyen.service.StateService;
import org.apache.commons.lang3.exception.ExceptionUtils;
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
	private GitHubClient gitHubClient;

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
		try {
            // Load commit statistics
            List<GitContributorStat> contributors = gitHubClient.getContributorStatistics(repos);
            if (contributors == null || contributors.isEmpty()) {
                return;
            }
            // Save data and state
            GitContributorStatList result = new GitContributorStatList(contributors);
            dataService.saveContributorStats(id, result);
        }
		catch (Exception exc) {
			logger.warn("Failed to fetch contributor statistics for project '{}': {}", repos,
					ExceptionUtils.getRootCauseMessage(exc));
			logger.trace("Exception", exc);
		    dataService.saveContributorStatsError(id, exc);
        }
	}
}
