package com.codewithcaleb.springbootmutliplefileupload.controller;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FileResponse {
    private String fileName;
    private String url;
}
