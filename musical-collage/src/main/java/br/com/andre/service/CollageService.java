package br.com.andre.service;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;

import br.com.andre.dto.CollageData;
import br.com.andre.dto.lastfm.LastFMRequestParams;
import br.com.andre.exception.HttpError;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.extern.slf4j.Slf4j;

@ApplicationScoped
@Slf4j
public class CollageService {

  private final CollageResourcesService collageResourcesService;

  public CollageService(CollageResourcesService collageResourcesService) {
    this.collageResourcesService = collageResourcesService;
  }

  public BufferedImage generateLastFMAlbumsCollage(
    LastFMRequestParams lastFMRequestParams
  ) {
    CollageData lastFMCollageData = CollageData.toCollageData(
      this.collageResourcesService.getUserLastFMTopAlbums(lastFMRequestParams).await().indefinitely(),
      lastFMRequestParams.size()
    );
    log.info(
      "Generating lastFM ALBUMS collage for user: {}, with the following links: {}",
      lastFMRequestParams.user(),
      lastFMCollageData.getImagesLinks()
    );
    return this.createMusicalCollage(
        lastFMCollageData,
        lastFMRequestParams.size()
      );
  }

  public BufferedImage generateLastFMTracksCollage(
    LastFMRequestParams lastFMRequestParams
  ) {
    CollageData lastFMCollageData = CollageData.toCollageData(
      this.collageResourcesService.getUserLastFMTopTracks(lastFMRequestParams).await().indefinitely(),
      lastFMRequestParams.size()
    );
    log.info(
      "Generating lastFM TRACKS collage for user: {}, with the following links: {}",
      lastFMRequestParams.user(),
      lastFMCollageData.getImagesLinks()
    );
    return this.createMusicalCollage(
        lastFMCollageData,
        lastFMRequestParams.size()
      );
  }

  private BufferedImage createMusicalCollage(
    CollageData collageData,
    int size
  ) {
    final int COLLAGE_ALBUM_COVER_AVERAGE_SIZE = 174;
    final int COLLAGE_WIDTH =
      COLLAGE_ALBUM_COVER_AVERAGE_SIZE * size, COLLAGE_HEIGHT =
      COLLAGE_ALBUM_COVER_AVERAGE_SIZE * size;

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
        COLLAGE_ALBUM_COVER_AVERAGE_SIZE
      );
    } catch (Exception e) {
      e.printStackTrace();
    }
     throw new HttpError("Error generating musical collage! Try again later.");
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
