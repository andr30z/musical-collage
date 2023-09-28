package com.musical.collage.musicalcollage.dto;

import com.musical.collage.musicalcollage.dto.lastfm.LastFMUserTopAlbumsResponse;
import com.musical.collage.musicalcollage.dto.lastfm.LastFMUserTopTracksResponse;
import com.musical.collage.musicalcollage.dto.spotify.SpotifyUserTopTracksResponse;

import java.util.List;
import java.util.stream.Stream;

public class CollageData {

  private final List<String> imagesLinks;

  public CollageData(List<String> imagesLinks) {
    this.imagesLinks = imagesLinks;
  }

  public List<String> getImagesLinks() {
    return imagesLinks;
  }

  record ImageUrls(List<CollageImage> images) {}

  public static CollageData toCollageData(
    LastFMUserTopTracksResponse lastFMUserTopTracksResponse,
    int size
  ) {
    return new CollageData(
      mapToListOfImageUrls(
        lastFMUserTopTracksResponse
          .getTopTracks()
          .getTrack()
          .stream()
          .map(track -> new ImageUrls(track.getImage())),
        size
      )
    );
  }

  
  public static CollageData toCollageData(
    LastFMUserTopAlbumsResponse lastFMUserTopAlbumsResponse,
    int size
  ) {
    return new CollageData(
      mapToListOfImageUrls(
        lastFMUserTopAlbumsResponse
          .getTopAlbums()
          .getAlbum()
          .stream()
          .map(album -> new ImageUrls(album.getImage())),
        size
      )
    );
  }

  
  public static CollageData toCollageData(
    SpotifyUserTopTracksResponse spotifyUserTopTracksResponse,
    int size
  ) {
    return new CollageData(
      mapToListOfImageUrls(
        spotifyUserTopTracksResponse
          .getTracks()
          .stream()
          .map(track -> new ImageUrls(track.getAlbum().images())),
        size
      )
    );
  }

  private static List<String> mapToListOfImageUrls(
    Stream<ImageUrls> imageUrlsStream,
    int size
  ) {
    return imageUrlsStream
      .filter(imageUrls ->
        imageUrls
          .images()
          .stream()
          .filter(image -> !image.getImageLink().isEmpty())
          .count() >
        0
      )
      .limit(size * size) //10x10, 20x20, 50x50
      .map(image -> {
        CollageImage largerstImageOrDefault = image
          .images()
          .stream()
          .filter(imageUrl -> imageUrl.getSize().equals("large") || imageUrl.getSize().equals("320"))
          .findFirst()
          .orElse(image.images().get(0));

        //Removing image extension. If I dont do this I get a 404 error when downloading the image
        return largerstImageOrDefault
          .getImageLink()
          .replace(".gif", "")
          .replace(".jpg", "")
          .replace(".png", "");
      })
      .toList();
  }
}
