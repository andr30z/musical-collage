package com.musical.collage.musicalcollage.enums;

import java.util.Map;

public enum ImageSizeTypes {
    SMALL,
    MEDIUM,
    LARGE,
    EXTRALARGE;

    public static ImageSizeTypes getImageSizeType(String imageSizeType) {
        Map<String, ImageSizeTypes> map = Map.ofEntries(
                Map.entry("small", SMALL),
                Map.entry("medium", MEDIUM),
                Map.entry("large", LARGE),
                Map.entry("extralarge", EXTRALARGE));
        return map.get(imageSizeType);
    }
}
