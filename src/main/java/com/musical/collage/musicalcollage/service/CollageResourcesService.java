package com.musical.collage.musicalcollage.service;

import com.musical.collage.musicalcollage.dto.lastfm.LastFMRequestParams;
import com.musical.collage.musicalcollage.dto.lastfm.LastFMUserTopAlbumsResponse;
import com.musical.collage.musicalcollage.dto.lastfm.LastFMUserTopTracksResponse;
import com.musical.collage.musicalcollage.dto.spotify.SpotifyRequestParams;
import com.musical.collage.musicalcollage.dto.spotify.SpotifyUserTopTracksResponse;
import com.musical.collage.musicalcollage.exception.BadRequestException;
import jakarta.servlet.http.HttpServletRequest;
import java.util.List;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Configuration
public class CollageResourcesService {

  private static String lastFMApiKey;

  private final WebClient lastFMClient;
  private final WebClient spotifyWebClient;
  private final HttpServletRequest httpServletRequest;

  public CollageResourcesService(
    @Qualifier("lastFMWebClient") WebClient lastFMClient,
    @Qualifier("spotifyWebClient") WebClient spotifyWebClient,
    HttpServletRequest httpServletRequest
  ) {
    this.lastFMClient = lastFMClient;
    this.spotifyWebClient = spotifyWebClient;
    this.httpServletRequest = httpServletRequest;
  }

  @Value("${last-fm-api-key}")
  public void setLastFMKey(String key) {
    lastFMApiKey = key;
  }

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
      limit *
      limit //5x5, 10x10...
    );
  }

  private <T> T getUserLastFMTopMusicData(
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
          lastFMRequestParams.size()
        )
      )
      .accept(MediaType.APPLICATION_JSON)
      .retrieve()
      .bodyToMono(convertToClass)
      .doOnError(error -> {
        error.printStackTrace();
        throw new BadRequestException(
          "Error getting collage data! Try again later."
        );
      })
      .block();
  }

  private <T> T getUserSpotifyTopMusicData(
    String url,
    SpotifyRequestParams spotifyRequestParams,
    Class<T> convertToClass
  ) {
    return spotifyWebClient
      .get()
      .uri(uriBuilder ->
        uriBuilder
          .path(url)
          .queryParam(
            "limit",
            spotifyRequestParams.size() * spotifyRequestParams.size()
          )
          .queryParam("offset", 0)
          .queryParam("time_range", spotifyRequestParams.period())
          .build()
      )
      .header("Authorization", httpServletRequest.getHeader("spotify-token"))
      .accept(MediaType.APPLICATION_JSON)
      .retrieve()
      .bodyToMono(convertToClass)
      .doOnError(error -> {
        error.printStackTrace();
        throw new BadRequestException(
          "Error getting collage data! Try again later."
        );
      })
      .block();
  }

  public SpotifyUserTopTracksResponse getUserSpotifyTopTracks(
    SpotifyRequestParams spotifyRequestParams
  ) {
    final String TOP_TRACKS_URL = "/v1/me/top/tracks";
    return this.getUserSpotifyTopMusicData(
        TOP_TRACKS_URL,
        spotifyRequestParams,
        SpotifyUserTopTracksResponse.class
      );
  }

  public LastFMUserTopAlbumsResponse getUserLastFMTopAlbums(
    LastFMRequestParams lastFMRequestParams
  ) {
    final String TOP_ALBUM_URL_PARAM = "user.gettopalbums";
    return this.getUserLastFMTopMusicData(
        TOP_ALBUM_URL_PARAM,
        lastFMRequestParams,
        LastFMUserTopAlbumsResponse.class
      );
  }

  public LastFMUserTopTracksResponse getUserLastFMTopTracks(
    LastFMRequestParams lastFMRequestParams
  ) {
    final String TOP_TRACKS_URL_PARAM = "user.gettoptracks";
    return this.getUserLastFMTopMusicData(
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
