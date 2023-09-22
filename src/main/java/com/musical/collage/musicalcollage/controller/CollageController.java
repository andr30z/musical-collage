package com.musical.collage.musicalcollage.controller;

import com.musical.collage.musicalcollage.dto.lastfm.LastFMRequestParams;
import com.musical.collage.musicalcollage.service.CollageService;
import java.awt.image.BufferedImage;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/collage")
public class CollageController {

  private final CollageService collageService;

  public CollageController(CollageService collageService) {
    this.collageService = collageService;
  }

  @GetMapping(value = "/albums/lastfm", produces = "image/png")
  public BufferedImage generateLastFMAlbumsCollage(
    LastFMRequestParams lastFMRequestParams
  ) {
    return this.collageService.generateLastFMAlbumsCollage(lastFMRequestParams);
  }

  @GetMapping(value = "/tracks/lastfm", produces = "image/png")
  public BufferedImage generateLastFMTracksCollage(
    LastFMRequestParams lastFMRequestParams
  ) {
    return this.collageService.generateLastFMAlbumsCollage(lastFMRequestParams);
  }
}
