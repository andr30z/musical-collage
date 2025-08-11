package br.com.andre.model;


import br.com.andre.enums.ImageSizeTypes;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ImageSource {
    private ImageSizeTypes imageSizeTypes;
    private String url;
}
