package com.codewithcaleb.springbootmutliplefileupload.services;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.util.stream.Stream;

public interface IFileUploadService {
    //Helps us initialize the directory we are going to save our files when app gets started
    public void init();

    //Save our File
    public void save(MultipartFile file);

    Resource getFileByName(String fileName);

    public void deleteAll();
    Stream<Path> loadAllFiles();

}
