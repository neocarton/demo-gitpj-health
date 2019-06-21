package me.lam.huyen.service;

import me.lam.huyen.model.*;
import me.lam.huyen.repository.DataRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Data service listens to all kind of messages and store them
 */
@Service
public class DataService {

	private static final int MAX_WEEK_COUNTS = 53;

	private Logger logger = LoggerFactory.getLogger(DataService.class);

	@Autowired
	private DataRepository dataRepository;

	@Autowired
	private StateService stateService;

	@Transactional
	public void saveProjects(GitProjectList projectList) {
		List<GitProject> projects = projectList.getItems();
		if (projects == null || projects.isEmpty()) {
			return;
		}
		int page = projectList.getPage();
		logger.debug("Saving '{}' git projects loaded from page '{}'", projects.size(), page);
		for (GitProject project : projects) {
			saveProject(project);
		}
		stateService.save(page, projects.size());
		logger.debug("Successfully saved '{}' git projects loaded from page '{}'", projects.size(), page);
	}

	private void saveProject(GitProject project) {
		String id = project.getId();
		Map<String, String> properties = new LinkedHashMap<>();
		properties.put("name", project.getName());
		properties.put("url", project.getUrl());
		GitUser owner = project.getOwner();
		properties.put("owner", (owner != null) ? owner.getLogin() : null);
		properties.put("size", Objects.toString(project.getSize(), null));
		properties.put("forks", Objects.toString(project.getForks(), null));
		properties.put("stars", Objects.toString(project.getWatchers(), null));
		List<Data> data = properties.entrySet().stream()
				.filter(entry -> entry.getValue() != null)
				.map(entry -> new Data(id, entry.getKey(), entry.getValue()))
				.collect(Collectors.toList());
		dataRepository.saveAll(data);
	}

	@Transactional
	public void saveCommitStats(Map<String, GitCommitStatWeekly> commitStats) {
		for (Map.Entry<String, GitCommitStatWeekly> commitStatWeeklyEntry : commitStats.entrySet()) {
			String id = commitStatWeeklyEntry.getKey();
			GitCommitStatWeekly commitStatWeekly = commitStatWeeklyEntry.getValue();
			saveCommitStat(id, commitStatWeekly);
		}
	}

	private void saveCommitStat(String id, GitCommitStatWeekly commitStatWeekly) {
		Map<String, String> properties = new LinkedHashMap<>();
		properties.put("commit.total", String.valueOf(commitStatWeekly.getTotalCommit()));
		properties.put("commit.week_count", String.valueOf(commitStatWeekly.getWeekCount()));
		properties.put("commit.weekly_average", String.valueOf(commitStatWeekly.getAvgCommitPerWeek()));
		properties.put("commit.daily_average", String.valueOf(commitStatWeekly.getAvgCommitPerDay()));
		List<Data> data = properties.entrySet().stream()
				.filter(entry -> entry.getValue() != null)
				.map(entry -> new Data(id, entry.getKey(), entry.getValue()))
				.collect(Collectors.toList());
		dataRepository.saveAll(data);
	}
}
