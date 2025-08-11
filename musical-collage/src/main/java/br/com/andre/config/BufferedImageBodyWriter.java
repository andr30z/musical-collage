package br.com.andre.config;

import jakarta.ws.rs.Produces;
import jakarta.ws.rs.ext.Provider;
import jakarta.ws.rs.ext.MessageBodyWriter;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.MultivaluedMap;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

@Provider
@Produces({ "image/png", "image/jpeg" }) // Specify the supported media types
public class BufferedImageBodyWriter implements MessageBodyWriter<BufferedImage> {

    @Override
    public boolean isWriteable(Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType) {
        return type == BufferedImage.class;
    }

    @Override
    public long getSize(BufferedImage bufferedImage, Class<?> type, Type genericType, Annotation[] annotations,
            MediaType mediaType) {
        // Deprecated in JAX-RS 2.0, return -1
        return -1;
    }

    @Override
    public void writeTo(BufferedImage bufferedImage, Class<?> type, Type genericType, Annotation[] annotations,
            MediaType mediaType, MultivaluedMap<String, Object> httpHeaders, OutputStream entityStream)
            throws IOException {
        String format = "png"; // Default format
        if (mediaType.equals(MediaType.valueOf("image/jpeg"))) {
            format = "jpeg";
        }
        ImageIO.write(bufferedImage, format, entityStream);
    }
}