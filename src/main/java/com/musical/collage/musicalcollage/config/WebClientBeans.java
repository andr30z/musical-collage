package com.musical.collage.musicalcollage.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientBeans {

  private static final String LAST_FM_BASE_URL =
    "http://ws.audioscrobbler.com/2.0";
  private static final String SPOTIFY_BASE_URL = "https://api.spotify.com";

  @Bean(name = "lastFMWebClient")
  WebClient lastFMWebClient() {
    return WebClient.builder().baseUrl(LAST_FM_BASE_URL).build();
  }

  @Bean(name = "spotifyWebClient")
  WebClient spotifyWebClient() {
    return WebClient.builder().baseUrl(SPOTIFY_BASE_URL).build();
  }
}
