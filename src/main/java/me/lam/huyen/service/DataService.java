package me.lam.huyen.service;

import me.lam.huyen.model.*;
import me.lam.huyen.repository.DataRepository;
import org.apache.commons.lang3.exception.ExceptionUtils;
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

	public List<Data> findProjectsHaveNoCommit(int limit) {
		return dataRepository.findProjectsWithNoKey("commit.count", limit);
	}

	public List<Data> findProjectsHaveNoIssue(int limit) {
		return dataRepository.findProjectsWithNoKey("issue.count", limit);
	}

	public List<Data> findProjectsHaveNoPullRequest(int limit) {
		return dataRepository.findProjectsWithNoKey("pull_request.count", limit);
	}

	@Transactional
	public void saveProjects(List<GitProject> projects, State state) {
		for (GitProject project : projects) {
			saveProject(project);
		}
		stateService.save(state);
	}

	private void saveProject(GitProject project) {
		Map<String, String> properties = new LinkedHashMap<>();
		properties.put("name", project.getName());
		properties.put("full_name", project.getFullName());
		properties.put("url", project.getUrl());
		GitUser owner = project.getOwner();
		properties.put("owner", (owner != null) ? owner.getLogin() : null);
		properties.put("size", Objects.toString(project.getSize(), null));
		properties.put("forks", Objects.toString(project.getForks(), null));
		properties.put("stars", Objects.toString(project.getWatchers(), null));
		String id = project.getId();
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
	public void saveCommitStat(String id, GitCommitStatWeekly commitStatWeekly) {
		Map<String, String> properties = new LinkedHashMap<>();
		int commitCount = commitStatWeekly.getTotalCommit();
		properties.put("commit.count", Objects.toString(commitCount));
		if (commitCount > 0) {
			properties.put("commit.week_count", Objects.toString(commitStatWeekly.getWeekCount()));
			properties.put("commit.daily_average", Objects.toString(commitStatWeekly.getDailyAverage(), null));
			properties.put("commit.weekly_average", Objects.toString(commitStatWeekly.getWeeklyAverage(), null));
		}
		save(id, properties);
	}

	@Transactional
	public void saveCommitStatError(String id, Exception error) {
		String data = ExceptionUtils.getStackTrace(error);
		Map<String, String> properties = new LinkedHashMap<>();
		properties.put("commit.last_error", data);
		save(id, properties);
	}

	@Transactional
	public void saveIssues(String id, GitTopIssues topIssues) {
		Map<String, String> properties = new LinkedHashMap<>();
		int issueCount = topIssues.getIssueCount();
		properties.put("issue.count", Objects.toString(issueCount));
		properties.put("issue.count.open", Objects.toString(topIssues.getOpenIssueCount()));
		properties.put("issue.count.close", Objects.toString(topIssues.getCloseIssueCount()));
		if (issueCount > 0) {
			properties.put("issue.total_open_time_s", Objects.toString(topIssues.getTotalOpenTime()));
			properties.put("issue.avg_open_time_s", Objects.toString(topIssues.getAvgOpenTime()));
			Float openRatio = topIssues.getOpenRatio();
			Float closeRatio = topIssues.getCloseRatio();
			properties.put("issue.open_ratio", Objects.toString(openRatio, null));
			properties.put("issue.close_ratio", Objects.toString(closeRatio, null));
			openRatio = (openRatio != null) ? openRatio : 0;
			closeRatio = (closeRatio != null) ? closeRatio : 0;
			Float closeToOpenRatio = (openRatio != 0) ? closeRatio / openRatio : null;
			properties.put("issue.close_to_open_ratio", Objects.toString(closeToOpenRatio, null));
		}
		save(id, properties);
	}

	@Transactional
	public void saveIssuesError(String id, Exception error) {
		Map<String, String> properties = new LinkedHashMap<>();
		properties.put("issue.last_error", ExceptionUtils.getStackTrace(error));
		save(id, properties);
	}

	@Transactional
	public void savePullRequests(String id, GitTopIssues<?> topIssues) {
		Map<String, String> properties = new LinkedHashMap<>();
		int issueCount = topIssues.getIssueCount();
		properties.put("pull_request.count", Objects.toString(issueCount));
		properties.put("pull_request.count.open", Objects.toString(topIssues.getOpenIssueCount()));
		if (issueCount > 0) {
			properties.put("pull_request.total_open_time_s", Objects.toString(topIssues.getTotalOpenTime()));
			properties.put("pull_request.avg_merge_time_s", Objects.toString(topIssues.getAvgOpenTime()));
			properties.put("pull_request.comments", Objects.toString(topIssues.getTotalComments()));
			properties.put("pull_request.avg_comments", Objects.toString(topIssues.getAvgComments()));
		}
		save(id, properties);
	}
}
