package com.musical.collage.musicalcollage.enums;

import java.util.Map;

public enum PlatformTypes {
  LAST_FM,
  SPOTIFY;

  public static PlatformTypes getPlatform(String platformName) {
    Map<String, PlatformTypes> map = Map.ofEntries(
      Map.entry(LAST_FM.name(), LAST_FM),
      Map.entry(SPOTIFY.name(), SPOTIFY)
    );
    var platform = map.get(platformName);
    return platform;
  }
}
