package com.musical.collage.musicalcollage.config;

import com.musical.collage.musicalcollage.enums.PlatformTypes;
import java.util.Map;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientBeans {

  private static final String LAST_FM_URL = "http://ws.audioscrobbler.com/2.0";

  @Bean(name = "lastFMWebClient")
  WebClient lastFMWebClient() {
    return WebClient.builder().baseUrl(LAST_FM_URL).build();
  }
}
