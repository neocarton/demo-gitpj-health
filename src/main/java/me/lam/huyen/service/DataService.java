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
		save(id, properties);
	}

	private void save(String id, Map<String, String> properties) {
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
		properties.put("commit.count", Objects.toString(commitStatWeekly.getTotalCommit()));
		properties.put("commit.week_count", Objects.toString(commitStatWeekly.getWeekCount()));
		properties.put("commit.daily_average", Objects.toString(commitStatWeekly.getAvgCommitPerDay(), null));
		save(id, properties);
	}

	@Transactional
	public void saveIssues(Map<String, GitTopIssues> issues) {
		for (Map.Entry<String, GitTopIssues> issueEntry : issues.entrySet()) {
			String id = issueEntry.getKey();
			GitTopIssues topIssues = issueEntry.getValue();
			saveIssues(id, topIssues);
		}
	}

	private void saveIssues(String id, GitTopIssues topIssues) {
		Map<String, String> properties = new LinkedHashMap<>();
		properties.put("issue.count", Objects.toString(topIssues.getIssueCount()));
		properties.put("issue.count.open", Objects.toString(topIssues.getOpenIssueCount()));
		properties.put("issue.count.close", Objects.toString(topIssues.getCloseIssueCount()));
		properties.put("issue.total_open_time", Objects.toString(topIssues.getTotalOpenTime()));
		properties.put("issue.avg_open_time", Objects.toString(topIssues.getAvgOpenTime()));
		Float openRatio = topIssues.getOpenRatio();
		Float closeRatio = topIssues.getCloseRatio();
		properties.put("issue.open_ratio", Objects.toString(openRatio, null));
		properties.put("issue.close_ratio", Objects.toString(closeRatio, null));
        openRatio = (openRatio != null) ? openRatio : 0;
        closeRatio = (closeRatio != null) ? closeRatio : 0;
		Float closeToOpenRatio = (openRatio != 0) ? closeRatio/openRatio : null;
		properties.put("issue.close_to_open_ratio", Objects.toString(closeToOpenRatio, null));
		save(id, properties);
	}
}
