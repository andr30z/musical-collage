package br.com.andre.utils;

import java.util.List;

import br.com.andre.model.MusicalCollageSource;



public class ConvertMusicalListToDefaultList {
    public static List<MusicalCollageSource> convertMusicalListToDefaultList(
            List<Object> listOfInfos) {
        return listOfInfos.stream().map(element -> {
            var collageSource = new MusicalCollageSource();
            // BeanUtils.copyProperties(element, collageSource);
            return collageSource;
        }).toList();
    }
}
