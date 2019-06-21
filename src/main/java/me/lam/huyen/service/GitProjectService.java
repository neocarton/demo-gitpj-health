package me.lam.huyen.service;

import me.lam.huyen.client.GitHubClient;
import me.lam.huyen.model.GitProjectList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GitProjectService {

	private Logger logger = LoggerFactory.getLogger(GitProjectService.class);

	@Autowired
	private GitHubClient gitHubClient;

	public GitProjectList fetchTopProjects(int page, int pageSize) {
		logger.debug("Get git projects from github.com at page {} with page-size {}", page, pageSize);
		GitProjectList result = gitHubClient.searchRepositories("language:javascript", "stars", "desc", page, pageSize);
		logger.debug("Got {}/{} projects from github.com", result.getItems().size(), result.getCount());
		return result;
	}
}
