package me.lam.huyen.channel;

import me.lam.huyen.client.GitHubClient;
import me.lam.huyen.model.GitProject;
import me.lam.huyen.model.GitProjectList;
import me.lam.huyen.model.State;
import me.lam.huyen.service.DataService;
import me.lam.huyen.service.StateService;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjectLoader {

	private Logger logger = LoggerFactory.getLogger(ProjectLoader.class);

	@Value("${app.loader.project.page_size}")
	private int pageSize;

    @Value("${app.loader.project.max_result}")
    private int maxResult;

	@Value("${app.loader.project.language:}")
	private String language;

	@Autowired
	private GitHubClient gitHubClient;

	@Autowired
	private DataService dataService;

	@Autowired
	private StateService stateService;

    @Scheduled(fixedDelayString = "${app.loader.project.delay.inMilliSecond}")
	public void fetchProjects() {
		// Start from beginning or last stopped
		State state = stateService.get(State.PROJECT_LOADER_ID);
		int proceededCount = state.getTotalProceededCount();
		if (proceededCount >= maxResult) {
			return;
		}
		// Load projects
		int page = state.getNextPage();
		try {
            logger.debug("Fetch git projects from github.com at page {} with page-size {}", page, pageSize);
            int gitPageNum = page + 1; // Github page start from 1
            String query = "";
            if (StringUtils.isNotBlank(language)) {
                query = "language:" + language;
            }
            GitProjectList result = gitHubClient.searchRepositories(query, "stars", "desc", gitPageNum, pageSize);
            List<GitProject> projects = result.getItems();
            if (projects == null || projects.isEmpty()) {
                logger.debug("No new project was found");
                return;
            }
            // Save data and state
            int projectCount = projects.size();
            state.setPage(page);
            state.setLastProceededCount(projectCount);
            state.setTotalProceededCount(proceededCount + projectCount);
            dataService.saveProjects(projects, state);
        }
		catch (Exception exc) {
            logger.warn("Failed to fetch projects for page '{}': {}", page,
                    ExceptionUtils.getRootCauseMessage(exc));
            logger.trace("Exception", exc);
        }
	}
}
