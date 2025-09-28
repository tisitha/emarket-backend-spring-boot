package com.tisitha.emarket.service;

import org.springframework.web.multipart.MultipartFile;

public interface SupabaseService {

    String upload(String folderName,MultipartFile file);

    void deleteFile(String urlPath);

}
