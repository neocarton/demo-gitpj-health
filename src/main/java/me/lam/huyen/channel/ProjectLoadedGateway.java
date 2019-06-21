package me.lam.huyen.channel;

import me.lam.huyen.model.GitProjectList;
import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;

@MessagingGateway
public interface ProjectLoadedGateway {

	@Gateway(requestChannel = GitProjectChannel.PROJECT_LOADED)
	void send(GitProjectList projectList);
}
