package br.com.andre.service;

import java.util.List;

import org.eclipse.microprofile.config.inject.ConfigProperty;

import br.com.andre.config.LastFMClient;
import br.com.andre.dto.lastfm.LastFMRequestParams;
import br.com.andre.dto.lastfm.LastFMUserTopAlbumsResponse;
import br.com.andre.dto.lastfm.LastFMUserTopTracksResponse;
import br.com.andre.exception.HttpError;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.core.buffer.Buffer;
import io.vertx.mutiny.ext.web.client.HttpRequest;
import io.vertx.mutiny.ext.web.client.HttpResponse;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.core.Response;
import lombok.extern.slf4j.Slf4j;

@ApplicationScoped
@Slf4j
public class CollageResourcesService {

  private final LastFMClient lastFMClient;

  @ConfigProperty(name = "last-fm-api-key", defaultValue = "")
  private String lastFMApiKey;

  public CollageResourcesService(LastFMClient lastFMClient) {
    this.lastFMClient = lastFMClient;
  }

  // user=andr30z&api_key=200102117c119f76c7e47a8cd22971e7&format=json

  private HttpRequest<Buffer> createPathVariables(
      HttpRequest<Buffer> request,
      String user,
      String method,
      String period,
      int limit) {

    log.warn("PARAMS user: {}, method: {}, period: {}, limit: {}, api_key: {}", user, method, period, limit,
        this.lastFMApiKey);

    return request.addQueryParam("user", user)
        .addQueryParam("api_key", lastFMApiKey)
        .addQueryParam("format", "json")
        .addQueryParam("period", period)
        .addQueryParam("method", method)
        .addQueryParam("limit", String.valueOf(limit * limit));

  }

  private <T> Uni<T> getUserTopMusicData(
      String uri,
      LastFMRequestParams lastFMRequestParams,
      Class<T> convertToClass) {
    return createPathVariables(
        lastFMClient
            .getLastFMClient()
            .get("/2.0"),
        lastFMRequestParams.user(),
        uri,
        lastFMRequestParams.period(),
        lastFMRequestParams.size())
        .send()
        .onItem().transform(response -> {
          if (response.statusCode() != 200) {
            throw new HttpError("Error getting collage data! Try again later.", Response.Status.INTERNAL_SERVER_ERROR);
          }

          return response.bodyAsJson(convertToClass);
        });
  }

  public Uni<LastFMUserTopAlbumsResponse> getUserLastFMTopAlbums(
      LastFMRequestParams lastFMRequestParams) {
    final String TOP_ALBUM_URL_PARAM = "user.gettopalbums";
    return this.getUserTopMusicData(
        TOP_ALBUM_URL_PARAM,
        lastFMRequestParams,
        LastFMUserTopAlbumsResponse.class);
  }

  public Uni<LastFMUserTopTracksResponse> getUserLastFMTopTracks(
      LastFMRequestParams lastFMRequestParams) {
    final String TOP_TRACKS_URL_PARAM = "user.gettoptracks";
    return this.getUserTopMusicData(
        TOP_TRACKS_URL_PARAM,
        lastFMRequestParams,
        LastFMUserTopTracksResponse.class);
  }

  public Uni<HttpResponse<Buffer>> downloadImageFromLink(String imageLink) {
    return lastFMClient.getLastFMClient().getAbs(imageLink).send().onFailure().invoke(
        error -> new HttpError("Error getting collage data for the following link: " + imageLink + "! Try again later.",
            Response.Status.INTERNAL_SERVER_ERROR));
  }

  public List<byte[]> downloadAllImagesFromLinks(List<String> imageLinks) {
    List<Uni<HttpResponse<Buffer>>> requests = imageLinks.stream()
        .map(this::downloadImageFromLink)
        .toList();
    return Uni.join().all(requests).andFailFast().await().indefinitely()
        .stream().map(item -> item.body().getBytes()).toList();
  }
}
