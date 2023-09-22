package com.musical.collage.musicalcollage.service;

import com.musical.collage.musicalcollage.dto.lastfm.LastFMRequestParams;
import com.musical.collage.musicalcollage.dto.lastfm.LastFMUserTopAlbumsResponse;
import com.musical.collage.musicalcollage.dto.lastfm.LastFMUserTopTracksResponse;
import com.musical.collage.musicalcollage.enums.PlatformTypes;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClient.RequestHeadersSpec;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Log4j2
@Configuration
public class CollageResourcesService {

  private static String lastFMApiKey;

  private final WebClient lastFMClient;

  public CollageResourcesService(
    @Qualifier("lastFMWebClient") WebClient lastFMClient
  ) {
    this.lastFMClient = lastFMClient;
  }

  @Value("${last-fm-api-key}")
  public void setSvnUrl(String key) {
    log.info(lastFMApiKey);
    lastFMApiKey = key;
  }

  //user=andr30z&api_key=200102117c119f76c7e47a8cd22971e7&format=json

  private static String createPathVariables(
    String user,
    String method,
    String period,
    int limit
  ) {
    return (
      "/?user=" +
      user +
      "&api_key=" +
      lastFMApiKey +
      "&format=json" +
      "&period=" +
      period +
      "&method=" +
      method +
      "&limit=" +
      limit
    );
  }

  private <T> T getUserTopMusicData(
    String uri,
    LastFMRequestParams lastFMRequestParams,
    Class<T> convertToClass
  ) {
    return lastFMClient
      .get()
      .uri(
        createPathVariables(
          lastFMRequestParams.user(),
          uri,
          lastFMRequestParams.period(),
          lastFMRequestParams.size() * lastFMRequestParams.size()
        )
      )
      .accept(MediaType.APPLICATION_JSON)
      .retrieve()
      .bodyToMono(convertToClass)
      .block();
  }

  public LastFMUserTopAlbumsResponse getUserLastFMTopAlbums(
    LastFMRequestParams lastFMRequestParams
  ) {
    final String TOP_ALBUM_URL_PARAM = "user.gettopalbums";
    return this.getUserTopMusicData(
        TOP_ALBUM_URL_PARAM,
        lastFMRequestParams,
        LastFMUserTopAlbumsResponse.class
      );
  }

  public LastFMUserTopTracksResponse getUserLastFMTopTracks(
    LastFMRequestParams lastFMRequestParams
  ) {
    final String TOP_TRACKS_URL_PARAM = "user.gettoptracks";
    return this.getUserTopMusicData(
        TOP_TRACKS_URL_PARAM,
        lastFMRequestParams,
        LastFMUserTopTracksResponse.class
      );
  }

  public Mono<byte[]> downloadImageFromLink(String imageLink) {
    return WebClient
      .builder()
      .baseUrl(imageLink)
      .exchangeStrategies(
        ExchangeStrategies
          .builder()
          .codecs(configurer ->
            configurer.defaultCodecs().maxInMemorySize(16 * 1024 * 1024)
          )
          .build()
      )
      .build()
      .get()
      .accept(MediaType.IMAGE_JPEG, MediaType.IMAGE_PNG)
      .retrieve()
      .bodyToMono(byte[].class);
  }

  public List<byte[]> downloadAllImagesFromLinks(List<String> imageLinks) {
    return Flux
      .merge(imageLinks.stream().map(this::downloadImageFromLink).toList())
      .collectList()
      .block();
  }
}
