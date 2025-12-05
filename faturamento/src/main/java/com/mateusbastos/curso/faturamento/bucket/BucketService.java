package com.mateusbastos.curso.faturamento.bucket;

import com.mateusbastos.curso.faturamento.config.props.MinioProps;
import io.minio.GetPresignedObjectUrlArgs;
import io.minio.MinioClient;
import io.minio.http.Method;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
@RequiredArgsConstructor
public class BucketService {

    private final MinioClient bucketClient;
    private final MinioProps bucketProps;

    public void upload(BucketFile bucketFile) {
        try {
            bucketClient.putObject(
                    io.minio.PutObjectArgs.builder()
                            .bucket(bucketProps.getBucketName())
                            .object(bucketFile.name())
                            .stream(bucketFile.inputStream(), bucketFile.size(), -1)
                            .contentType(bucketFile.mediaType().toString())
                            .build()
            );
        } catch (Exception e) {
            throw new RuntimeException("Erro ao enviar arquivo para o bucket", e);
        }
    }

    public String getUrl(String objectName) {
        try {
            var objectUrl = GetPresignedObjectUrlArgs.builder()
                    .method(Method.GET)
                    .bucket(bucketProps.getBucketName())
                    .object(objectName)
                    .expiry(1, TimeUnit.HOURS)
                    .build();

            return bucketClient.getPresignedObjectUrl(objectUrl);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
