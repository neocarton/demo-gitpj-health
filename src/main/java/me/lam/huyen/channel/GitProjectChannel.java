package me.lam.huyen.channel;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

public interface GitProjectChannel {

	String PROJECT_LOADED = "projectLoaded";

	@Output
	MessageChannel projectLoaded();
}
