package com.musical.collage.musicalcollage.config;

import com.musical.collage.musicalcollage.enums.PlatformTypes;
import java.util.HashMap;
import java.util.Map;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClient.RequestHeadersSpec;

@Log4j2
@Configuration
public class WebClientConfig {

  private static String lastFMApiKey;

  @Value("${last-fm-api-key}")
  public void setSvnUrl(String key) {
    log.info(lastFMApiKey);
    lastFMApiKey = key;
  }

  public static final String TOP_ALBUM_URL_PARAM = "user.gettopalbums";

  private static final String LAST_FM_URL = "http://ws.audioscrobbler.com/2.0";
  private static Map<PlatformTypes, String> urlMap = Map.ofEntries(
    Map.entry(PlatformTypes.LAST_FM, LAST_FM_URL)
  );

  public static WebClient getWebClient(PlatformTypes type) {
    return WebClient.builder().baseUrl(urlMap.get(type)).build();
  }

  //user=andr30z&api_key=200102117c119f76c7e47a8cd22971e7&format=json

  public static String createPathVariables(String user) {
    var pathVariables = new HashMap<String, String>();
    pathVariables.put("user", user);
    pathVariables.put("api_key", lastFMApiKey);
    pathVariables.put("format", "json");
    return "/?user=" + user + "&api_key=" + lastFMApiKey + "&format=json";
  }

  public static RequestHeadersSpec<?> getUserTopAlbumsRequest(String user) {
    return getWebClient(PlatformTypes.LAST_FM)
      .get()
      .uri(createPathVariables(user) + "&method=" + TOP_ALBUM_URL_PARAM)
      .accept(MediaType.APPLICATION_JSON);
  }
}
