package com.codewithcaleb.springbootmutliplefileupload.services.Impl;

import com.codewithcaleb.springbootmutliplefileupload.services.IFileUploadService;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.stream.Stream;


@Service
public class FileUploadService implements IFileUploadService {
    private final Path rootDir = Paths.get("/uploads");

    @Override
    public void init() {
        try {
            //checking whether the directory exists & creating it if it does not exist
            File tempDir = new File(rootDir.toUri());
            boolean dirExists = tempDir.exists();
            if (!dirExists) {
                //files is very important
                //in creating and even moving of files into a directory
                Files.createDirectory(rootDir);
            }
        } catch (IOException e) {
            throw new RuntimeException("Error creating Directory");
        }
    }

    @Override
    public void save(MultipartFile file) {

        try {
            //it is going to save the file by the FileName
            Files.copy(file.getInputStream(), this.rootDir.resolve(Objects.requireNonNull(file.getOriginalFilename())));
        } catch (IOException e) {
            throw new RuntimeException("Error Uploading Files");
        }
    }

    @Override
    public Resource getFileByName(String fileName) {
        try {
            Path filePath = rootDir.resolve(fileName);
            UrlResource resource = new UrlResource(filePath.toUri());
            if (resource.exists() && resource.isReadable()) {
                return resource;
            } else {
                throw new RuntimeException("could not read the file");
            }
        } catch (MalformedURLException e) {
            throw new RuntimeException("Error" + e.getMessage());
        }

    }

    @Override
    public void deleteAll() {
        FileSystemUtils.deleteRecursively(rootDir.toFile());
    }

    @Override
    public Stream<Path> loadAllFiles() {
        try{
            return Files.walk(this.rootDir,1)
                    .filter(path -> !path.equals(this.rootDir))
                    .map(this.rootDir :: relativize);

        } catch (IOException e) {
            throw new RuntimeException("COULD NOT LOAD FILES");
        }
    }
}
