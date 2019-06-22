package me.lam.huyen.channel;

import me.lam.huyen.model.GitTopIssues;
import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;

import java.util.Map;

@MessagingGateway
public interface IssueLoadedGateway {

	@Gateway(requestChannel = GitProjectChannel.ISSUE_LOADED)
	void send(Map<String, GitTopIssues> issues);
}
