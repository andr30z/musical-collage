package com.musical.collage.musicalcollage.service;

import com.musical.collage.musicalcollage.config.WebClientConfig;
import com.musical.collage.musicalcollage.dto.LastFMUserTopAlbumsResponse;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Service
@Log4j2
public class CollageService {

  public LastFMUserTopAlbumsResponse generateCollage() {
    var genericResponse = WebClientConfig
      .getUserTopAlbumsRequest("andr30z")
      .retrieve()
      .bodyToMono(LastFMUserTopAlbumsResponse.class)
      .block();
    return genericResponse;
  }
}
