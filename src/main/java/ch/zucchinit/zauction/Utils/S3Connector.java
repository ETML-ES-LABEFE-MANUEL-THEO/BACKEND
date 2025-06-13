package ch.zucchinit.zauction.Utils;

import ch.zucchinit.zauction.Configurations.S3Configuration;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Component
public class S3Connector {

    private final S3Configuration configuration;
    private final StaticCredentialsProvider provider;

    public S3Connector(S3Configuration configuration) {
        this.configuration = configuration;

        AwsBasicCredentials credentials = AwsBasicCredentials.create(configuration.getAccessKey(), configuration.getSecretKey());
        provider = StaticCredentialsProvider.create(credentials);
    }

    public S3Client getS3Client() {
        return S3Client.builder().credentialsProvider(provider).region(Region.of(configuration.getRegion())).build();
    }

    public String getFileExtension(String filename) {
        return Objects.requireNonNull(filename).substring(filename.lastIndexOf('.'));
    }

    public List<String> uploadFiles(Map<String, byte[]> files) {
        List<String> urls = new ArrayList<>();
        S3Client s3Client = getS3Client();

        for (Map.Entry<String, byte[]> entry : files.entrySet()) {
            s3Client.putObject(
                    PutObjectRequest.builder().bucket(configuration.getBucket()).key(entry.getKey()).build(),
                    RequestBody.fromBytes(entry.getValue())
            );

            urls.add(entry.getKey());
        }

        s3Client.close();
        return urls;
    }

    public void deleteFiles(List<String> files) {
        S3Client s3Client = getS3Client();
        List<ObjectIdentifier> toDelete = new ArrayList<>();

        for (String file : files) toDelete.add(ObjectIdentifier.builder().key(file).build());
        DeleteObjectsRequest deleteRequest = DeleteObjectsRequest.builder()
                .bucket(configuration.getBucket())
                .delete(Delete.builder().objects(toDelete).build())
                .build();

        s3Client.deleteObjects(deleteRequest);
        s3Client.close();
    }
}