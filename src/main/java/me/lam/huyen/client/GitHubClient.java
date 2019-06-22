package me.lam.huyen.client;

import me.lam.huyen.model.*;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

@FeignClient(name = "github", url = "${app.github.base_url}", fallback = GitHubService.class)
interface GitHubClient {

	@RequestMapping(method = RequestMethod.GET, value = "/",
			headers = {"Authorization=token ${app.github.access_token}"})
	Map<String, String> getURLs();

	/**
	 * Search repositories.
	 * Reference: https://developer.github.com/v3/search/#search-repositories
	 */
	@RequestMapping(method = RequestMethod.GET, path = "/search/repositories",
			headers = {"Authorization=token ${app.github.access_token}", "Accept=application/vnd.github.v3+json"})
    GitProjectList searchRepositories(@RequestParam(name = "q") String query,
                                      @RequestParam(name = "sort", required = false) String sort,
                                      @RequestParam(name = "order", required = false) String order,
                                      @RequestParam(name = "page", required = false) Integer page,
									  @RequestParam(name = "per_page", required = false) Integer pageSize);

	/**
	 * Get repository commit statistics
	 * https://developer.github.com/v3/repos/statistics/#get-contributors-list-with-additions-deletions-and-commit-counts
	 */
	@RequestMapping(method = RequestMethod.GET, path = "/repos/{repo}/stats/commit_activity",
			headers = {"Authorization=token ${app.github.access_token}", "Accept=application/vnd.github.v3+json"})
	List<GitCommitStat> getCommitStatistics(@PathVariable("repo") String repo);

	/**
	 * Get repository issues
	 * https://developer.github.com/v3/issues/#list-issues-for-a-repository
	 */
	@RequestMapping(method = RequestMethod.GET, path = "/repos/{repo}/issues",
			headers = {"Authorization=token ${app.github.access_token}", "Accept=application/vnd.github.v3+json"})
	List<GitIssue> getIssues(@PathVariable("repo") String repo,
							 @RequestParam(name = "sort", required = false) String sort,
							 @RequestParam(name = "order", required = false) String order,
							 @RequestParam(name = "page", required = false) Integer page,
							 @RequestParam(name = "per_page", required = false) Integer pageSize);

	/**
	 * Get repository pull requests
	 * https://developer.github.com/v3/pulls/#list-pull-requests
	 */
	@RequestMapping(method = RequestMethod.GET, path = "/repos/{repo}/pulls",
			headers = {"Authorization=token ${app.github.access_token}", "Accept=application/vnd.github.v3+json"})
	List<GitPullRequest> getPullRequests(@PathVariable("repo") String repo,
										 @RequestParam(name = "sort", required = false) String sort,
										 @RequestParam(name = "order", required = false) String order,
										 @RequestParam(name = "page", required = false) Integer page,
										 @RequestParam(name = "per_page", required = false) Integer pageSize);

	/**
	 * Get repository contributor statistics
	 * https://developer.github.com/v3/repos/statistics/#get-contributors-list-with-additions-deletions-and-commit-counts
	 */
	@RequestMapping(method = RequestMethod.GET, path = "/repos/{repo}/stats/contributors",
			headers = {"Authorization=token ${app.github.access_token}", "Accept=application/vnd.github.v3+json"})
	List<GitContributorStat> getContributorStatistics(@PathVariable("repo") String repo);
}
