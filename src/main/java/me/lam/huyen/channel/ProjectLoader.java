package me.lam.huyen.channel;

import me.lam.huyen.client.GitHubClient;
import me.lam.huyen.config.ApplicationConfiguration;
import me.lam.huyen.model.GitProject;
import me.lam.huyen.model.GitProjectList;
import me.lam.huyen.model.State;
import me.lam.huyen.service.StateService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjectLoader {

	private Logger logger = LoggerFactory.getLogger(ProjectLoader.class);

	@Autowired
	private ApplicationConfiguration config;

	@Autowired
	private GitHubClient gitHubClient;

	@Autowired
	private StateService stateService;

	@Autowired
	private ProjectLoadedGateway projectLoadedGateway;

	public void fetchProjects() {
		int delay = config.getDelay();
		int pageSize = config.getPageSize();
		int maxResult = config.getMaxResult();
		// Start from beginning or last stopped
		State state = stateService.get();
		Integer page = state.getPage();
		int proceededCount = state.getLoadedCount();
		logger.debug("Start to fetch git projects from page {} and page-size {}", page, pageSize);
		while (proceededCount < maxResult) {
			int loadedCount = fetchProjectsAndPassOn(page, pageSize);
			if (loadedCount == 0) {
				break;
			}
			page++;
			proceededCount += loadedCount;
			logger.info("Loaded {} projects", proceededCount);
			sleep(delay);
		};
	}

	public int fetchProjectsAndPassOn(int page, int pageSize) {
		// Load repositories
		logger.debug("Get git projects from github.com at page {} with page-size {}", page, pageSize);
		GitProjectList result = gitHubClient.searchRepositories("language:javascript", "stars", "desc", page, pageSize);
		List<GitProject> projects = result.getItems();
		if (projects == null || projects.isEmpty()) {
			return 0;
		}
		logger.debug("Got {} projects from github.com", projects.size());
		// Push repositories to data pipeline
		result.setPage(page);
		projectLoadedGateway.send(result);
		return projects.size();
	}

	private void sleep(int delay) {
		try {
			if (delay <=0 ) {
				return;
			}
			Thread.sleep(delay);
		}
		catch (Exception exc) {
			throw new RuntimeException(exc);
		}
	}
}
