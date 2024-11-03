package com.codewithcaleb.springbootmutliplefileupload.controller;

import com.codewithcaleb.springbootmutliplefileupload.services.IFileUploadService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/files")
public class FileUploadController {

    private final IFileUploadService fileUploadService;

    public ResponseEntity<FileResponse> uploadFiles(@RequestParam("file") MultipartFile[] files){

        String message = null;
        try{
            List<String> fileNames = new ArrayList<>();
            Arrays.stream(files).forEach(file -> {
                fileUploadService.save(file);
                fileNames.add(file.getName());
            });
            message = "file(s) have been uploaded successfully";
        }catch (Exception e){

        }
    }
}
