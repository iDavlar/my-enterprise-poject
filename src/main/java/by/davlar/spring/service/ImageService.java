package by.davlar.spring.service;

import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Optional;

@Service
public class ImageService {
    @Value("${app.media.bucket:C:\\JPr\\my-enterprise-poject\\images}")
    private String bucket;

    @SneakyThrows
    public void upload(String imagePath, InputStream content) {
        Path imageFullPath = Path.of(bucket, imagePath);

        try (content) {
            Files.createDirectories(imageFullPath.getParent());
            Files.write(
                    imageFullPath,
                    content.readAllBytes(),
                    StandardOpenOption.CREATE,
                    StandardOpenOption.TRUNCATE_EXISTING
            );
        }
    }

    @SneakyThrows
    public Optional<byte[]> get(String imagePath) {
        Path imageFullPath = Path.of(bucket, imagePath);
        return Files.exists(imageFullPath)
                ? Optional.of(Files.readAllBytes(imageFullPath))
                : Optional.empty();
    }
}
