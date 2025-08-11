package br.com.andre.enums;

import java.util.Map;

public enum PlatformTypes {
  LAST_FM;

  public static PlatformTypes getPlatform(String platformName) {
    Map<String, PlatformTypes> map = Map.ofEntries(
      Map.entry(LAST_FM.name(), LAST_FM)
    );
    var platform = map.get(platformName);
    return platform;
  }
}
