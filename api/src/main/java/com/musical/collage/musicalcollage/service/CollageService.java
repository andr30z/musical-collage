package com.musical.collage.musicalcollage.service;

import com.musical.collage.musicalcollage.dto.CollageData;
import com.musical.collage.musicalcollage.dto.lastfm.LastFMRequestParams;
import com.musical.collage.musicalcollage.dto.spotify.SpotifyRequestParams;
import com.musical.collage.musicalcollage.exception.BadRequestException;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;
import javax.imageio.ImageIO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class CollageService {

  private final CollageResourcesService collageResourcesService;
  private static final int LASTFM_COLLAGE_ALBUM_COVER_AVERAGE_SIZE = 174;
  private static final int SPOTIFY_COLLAGE_ALBUM_COVER_AVERAGE_SIZE = 174;

  public CollageService(CollageResourcesService collageResourcesService) {
    this.collageResourcesService = collageResourcesService;
  }

  public BufferedImage generateLastFMAlbumsCollage(
    LastFMRequestParams lastFMRequestParams
  ) {
    CollageData lastFMCollageData = CollageData.toCollageData(
      this.collageResourcesService.getUserLastFMTopAlbums(lastFMRequestParams),
      lastFMRequestParams.size()
    );
    log.info(
      "Generating lastFM ALBUMS collage for user: {}, with the following links: {}",
      lastFMRequestParams.user(),
      lastFMCollageData.getImagesLinks()
    );
    return this.createMusicalCollage(
        lastFMCollageData,
        LASTFM_COLLAGE_ALBUM_COVER_AVERAGE_SIZE,
        lastFMRequestParams.size()
      );
  }

  public BufferedImage generateLastFMTracksCollage(
    LastFMRequestParams lastFMRequestParams
  ) {
    CollageData lastFMCollageData = CollageData.toCollageData(
      this.collageResourcesService.getUserLastFMTopTracks(lastFMRequestParams),
      lastFMRequestParams.size()
    );
    log.info(
      "Generating lastFM TRACKS collage for user: {}, with the following links: {}",
      lastFMRequestParams.user(),
      lastFMCollageData.getImagesLinks()
    );
    return this.createMusicalCollage(
        lastFMCollageData,
        LASTFM_COLLAGE_ALBUM_COVER_AVERAGE_SIZE,
        lastFMRequestParams.size()
      );
  }

  public BufferedImage generateSpotifyTracksCollage(
    SpotifyRequestParams spotifyRequestParams
  ) {
    CollageData spotifyCollageData = CollageData.toCollageData(
      this.collageResourcesService.getUserSpotifyTopTracks(spotifyRequestParams),
      spotifyRequestParams.size()
    );
    log.info(
      "Generating Spotify TRACKS collage with the following links: {}",
      spotifyCollageData.getImagesLinks()
    );
    return this.createMusicalCollage(
        spotifyCollageData,
        SPOTIFY_COLLAGE_ALBUM_COVER_AVERAGE_SIZE,
        spotifyRequestParams.size()
      );
  }

  private BufferedImage createMusicalCollage(
    CollageData collageData,
    int collageItemCoverAverageSize,
    int collageGridQuantity
  ) {
    final int COLLAGE_WIDTH =
      collageItemCoverAverageSize * collageGridQuantity, COLLAGE_HEIGHT =
      collageItemCoverAverageSize * collageGridQuantity;

    BufferedImage collage = new BufferedImage(
      COLLAGE_WIDTH,
      COLLAGE_HEIGHT,
      BufferedImage.TYPE_INT_RGB
    );
    try {
      List<byte[]> allImagesDownloaded =
        this.collageResourcesService.downloadAllImagesFromLinks(
            collageData.getImagesLinks()
          );
      return mergeCollageImages(
        allImagesDownloaded,
        collage,
        collageItemCoverAverageSize
      );
    } catch (Exception e) {
      e.printStackTrace();
    }
    throw new BadRequestException(
      "Error generating musical collage! Try again later."
    );
  }

  private BufferedImage mergeCollageImages(
    List<byte[]> collageImages,
    BufferedImage collage,
    int collageImageSize
  ) throws IOException {
    int x = 0, y = 0;
    Graphics collageGraphics = collage.getGraphics();

    for (byte[] albumCover : collageImages) {
      ByteArrayInputStream inputStream = new ByteArrayInputStream(albumCover);

      BufferedImage albumCoverImage = ImageIO.read(inputStream);
      collageGraphics.drawImage(albumCoverImage, x, y, null);
      x += collageImageSize;
      if (x >= collage.getWidth()) {
        x = 0;
        y += albumCoverImage.getHeight();
      }
      inputStream.close();
    }

    return collage;
  }
}
