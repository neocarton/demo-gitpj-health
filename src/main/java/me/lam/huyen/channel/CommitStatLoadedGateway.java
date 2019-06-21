package me.lam.huyen.channel;

import me.lam.huyen.model.GitCommitStatWeekly;
import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;

import java.util.Map;

@MessagingGateway
public interface CommitStatLoadedGateway {

	@Gateway(requestChannel = GitProjectChannel.COMMIT_STAT_LOADED)
	void send(Map<String, GitCommitStatWeekly> commitStats);
}
