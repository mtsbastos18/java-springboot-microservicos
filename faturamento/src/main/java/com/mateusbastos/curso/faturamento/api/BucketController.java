package com.mateusbastos.curso.faturamento.api;

import com.mateusbastos.curso.faturamento.bucket.BucketFile;
import com.mateusbastos.curso.faturamento.bucket.BucketService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.Objects;

@RestController
@RequestMapping("/bucket")
@RequiredArgsConstructor
public class BucketController {

    private final BucketService bucketService;

    @PostMapping
    public ResponseEntity<String> uploadFile(@RequestParam("file")MultipartFile file) {
        try (InputStream inputStream = file.getInputStream()) {
            MediaType type = MediaType.parseMediaType(Objects.requireNonNull(file.getContentType()));
            var bucketFile = new BucketFile(file.getOriginalFilename(), inputStream, type, file.getSize());
            bucketService.upload(bucketFile);
            return ResponseEntity.ok("Arquivo enviado com sucesso");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Erro ao fazer upload do arquivo: " + e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<String> getFileUrl(@RequestParam String filename) {
        try {
            String url = bucketService.getUrl(filename);
            return ResponseEntity.ok(url);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Erro ao obter URL do arquivo: " + e.getMessage());
        }
    }


}
