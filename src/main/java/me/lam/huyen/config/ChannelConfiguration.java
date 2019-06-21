package me.lam.huyen.config;

import me.lam.huyen.channel.GitProjectChannel;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableBinding({GitProjectChannel.class})
public class ChannelConfiguration {
}
