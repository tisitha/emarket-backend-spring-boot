package com.tisitha.emarket.service;

import com.tisitha.emarket.exception.CorruptFileException;
import com.tisitha.emarket.exception.SupabaseUploadingErrorException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Objects;
import java.util.UUID;

@Service
public class SupabaseServiceImp implements SupabaseService {

    private final String supabaseUrl;

    private final String bucket;

    private final RestClient restClient;

    public SupabaseServiceImp(RestClient.Builder restClientBuilder,
                              @Value("${supabase.url}") String supabaseUrl,
                              @Value("${SUPABASE_BUCKET}") String bucket,
                              @Value("${SUPABASE_API_KEY}") String apiKey) {

        this.supabaseUrl = supabaseUrl;
        this.bucket = bucket;
        this.restClient = restClientBuilder
                .baseUrl(supabaseUrl)
                .defaultHeader("Authorization", "Bearer " + apiKey)
                .build();
    }

    public String upload(String folderName,MultipartFile file) {

        String filename = folderName+"/"+ UUID.randomUUID() +file.getOriginalFilename();

        ResponseEntity<String> response = restClient.post()
                    .uri( "/storage/v1/object/" + bucket + "/" + filename)
                    .contentType(MediaType.parseMediaType(Objects.requireNonNull(file.getContentType())))
                    .body(fileToByte(file))
                    .retrieve()
                    .toEntity(String.class);

        if (response.getStatusCode() == HttpStatus.OK) {
            return getPublicUrl(filename);
        } else {
            throw new SupabaseUploadingErrorException("Supebase service failed."+ response);
        }
    }

    @Override
    public void deleteFile(String urlPath) {
        String filename = getFileName(urlPath);
        ResponseEntity<String> response = restClient.delete().uri( "/storage/v1/object/" + bucket + "/" + filename)
                .retrieve()
                .toEntity(String.class);
        if (response.getStatusCode() != HttpStatus.OK) {
            throw new SupabaseUploadingErrorException("Supebase service failed."+ response);
        }
    }

    private String getPublicUrl(String fileName) {
        return supabaseUrl + "/storage/v1/object/public/" + bucket + "/" + fileName;
    }

    private byte[] fileToByte(MultipartFile file){
        try {
            return file.getBytes();
        } catch (IOException e) {
            throw new CorruptFileException(e.getMessage());
        }
    }

    private String getFileName(String urlPath) {
        String prefix = supabaseUrl + "/storage/v1/object/public/" + bucket + "/" ;
        if (urlPath.startsWith(prefix)) {
            return urlPath.substring(prefix.length());
        }
        return urlPath;
    }

}
