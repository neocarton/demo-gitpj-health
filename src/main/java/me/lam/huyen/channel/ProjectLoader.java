package me.lam.huyen.channel;

import me.lam.huyen.config.ApplicationConfiguration;
import me.lam.huyen.model.GitProject;
import me.lam.huyen.model.GitProjectList;
import me.lam.huyen.model.State;
import me.lam.huyen.service.GitProjectService;
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
	private StateService stateService;

	@Autowired
	private GitProjectService gitProjectService;

	@Autowired
	private ProjectLoadedGateway projectLoadedGateway;

	public void loadProjects() {
		int delay = config.getDelay();
		int pageSize = config.getPageSize();
		int maxResult = config.getMaxResult();
		// Start from beginning or last stopped
		State state = stateService.get();
		Integer page = state.getPage();
		int proceededCount = state.getLoadedCount();
		logger.debug("Start to fetch git projects from page {} and page-size {}", page, pageSize);
		while (proceededCount < maxResult) {
			int loadedCount = loadProjectsAndPassOn(page, pageSize);
			if (loadedCount == 0) {
				break;
			}
			page++;
			proceededCount += loadedCount;
			logger.debug("Loaded {} projects", proceededCount);
			sleep(delay);
		};
	}

	public int loadProjectsAndPassOn(int page, int pageSize) {
		// Load repositories
		GitProjectList projectList = gitProjectService.fetchTopProjects(page, pageSize);
		List<GitProject> projects = projectList.getItems();
		if (projects == null || projects.isEmpty()) {
			return 0;
		}
		// Push repositories to data pipeline
		projectList.setPage(page);
		projectLoadedGateway.send(projectList);
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
