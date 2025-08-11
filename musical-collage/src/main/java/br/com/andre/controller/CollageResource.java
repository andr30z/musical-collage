package br.com.andre.controller;

import java.awt.image.BufferedImage;

import br.com.andre.dto.lastfm.LastFMRequestParams;
import br.com.andre.service.CollageService;
import jakarta.validation.Valid;
import jakarta.ws.rs.BeanParam;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;

@Path("/api/v1/collage")
public class CollageResource {

  private final CollageService collageService;

  public CollageResource(CollageService collageService) {
    this.collageService = collageService;
  }

  @GET
  @Path("/albums/lastfm")
  public BufferedImage generateLastFMAlbumsCollage(
      @Valid @BeanParam LastFMRequestParams lastFMRequestParams) {
    return this.collageService.generateLastFMAlbumsCollage(lastFMRequestParams);
  }

  @GET
  @Path("/tracks/lastfm")
  public BufferedImage generateLastFMTracksCollage(
      @Valid @BeanParam LastFMRequestParams lastFMRequestParams) {
    return this.collageService.generateLastFMTracksCollage(lastFMRequestParams);
  }
}
