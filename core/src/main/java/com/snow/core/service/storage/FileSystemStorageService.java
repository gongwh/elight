package com.snow.core.service.storage;

import com.snow.lib.FileUtil;
import lombok.extern.slf4j.Slf4j;
import net.coobird.thumbnailator.Thumbnails;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Arrays;
import java.util.stream.Stream;

@Service
@Slf4j
public class FileSystemStorageService implements StorageService {

    private final Path rootLocation;

    private int compressWidth;

    private int compressHeight;

    private boolean isImageCompress;

    @Autowired
    public FileSystemStorageService(StorageProperties properties) {
        this.rootLocation = Paths.get(properties.getUploadDir());
        this.isImageCompress = properties.isImageCompress();
        this.compressWidth = properties.getCompressWidth();
        this.compressHeight = properties.getCompressHeight();
    }

    @Override
    public String store(MultipartFile file) {
        String fileName = FileUtil.getDateAsRelativePath(file.getOriginalFilename());

        try {
            if (file.isEmpty()) {
                throw new StorageException("Failed to store empty file " + fileName);
            }
            if (fileName.contains("..")) {
                // This is a security check
                throw new StorageException(
                        "Cannot store file with relative path outside current directory "
                                + fileName);
            }
            Path parentDir = this.rootLocation.resolve(fileName).getParent();
            if (!parentDir.toFile().exists())
                Files.createDirectories(parentDir);
            if (isImageCompress && FilenameUtils.isExtension(fileName, Arrays.asList("jpg", "jpeg", "png"))) {
                Thumbnails.of(file.getInputStream()).size(compressWidth, compressHeight).toFile(this.rootLocation.resolve(fileName).toString());
            } else {
                Files.write(this.rootLocation.resolve(fileName), file.getBytes(), StandardOpenOption.CREATE);
            }
            return fileName.replaceAll("\\\\", "/");
        } catch (IOException e) {
            throw new StorageException("Failed to store file " + fileName, e);
        }
    }

    @Override
    public Stream<Path> loadAll() {
        try {
            return Files.walk(this.rootLocation, 1)
                    .filter(path -> !path.equals(this.rootLocation))
                    .map(path -> this.rootLocation.relativize(path));
        } catch (IOException e) {
            throw new StorageException("Failed to read stored files", e);
        }
    }

    @Override
    public Path load(String filename) {
        return rootLocation.resolve(filename);
    }

    @Override
    public Resource loadAsResource(String filename) {
        try {
            Path file = load(filename);
            Resource resource = new UrlResource(file.toUri());
            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new StorageFileNotFoundException(
                        "Could not read file: " + filename);

            }
        } catch (MalformedURLException e) {
            throw new StorageFileNotFoundException("Could not read file: " + filename, e);
        }
    }

    @Override
    public void deleteAll() {
        FileSystemUtils.deleteRecursively(rootLocation.toFile());
    }

    @Override
    public void init() {
        try {
            Files.createDirectories(rootLocation);
        } catch (IOException e) {
            throw new StorageException("Could not initialize storage", e);
        }
    }
}
