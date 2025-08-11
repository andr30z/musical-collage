package br.com.andre.model;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MusicalCollageSource {
    private String name;
    private List<ImageSource> images;
}
