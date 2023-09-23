package com.musical.collage.musicalcollage.dto.spotify;

import java.util.List;

import com.musical.collage.musicalcollage.dto.CollageImage;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public class SpotifyTrack {

  public record Album(List<CollageImage> images) {}

  private Album album;

  public Album getAlbum() {
    return album;
  }

  public void setAlbum(Album album) {
    this.album = album;
  }
}
