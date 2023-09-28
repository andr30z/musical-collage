package com.musical.collage.musicalcollage.utils;

import java.util.List;

import org.springframework.beans.BeanUtils;

import com.musical.collage.musicalcollage.model.MusicalCollageSource;

public class ConvertMusicalListToDefaultList {
    public static List<MusicalCollageSource> convertMusicalListToDefaultList(
            List<Object> listOfInfos) {
        return listOfInfos.stream().map(element -> {
            var collageSource = new MusicalCollageSource();
            BeanUtils.copyProperties(element, collageSource);
            return collageSource;
        }).toList();
    }
}
