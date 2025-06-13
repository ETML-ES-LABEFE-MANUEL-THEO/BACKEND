package ch.zucchinit.zauction.Configurations;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "s3")
@Getter
@Setter
public class S3Configuration {
    private String bucket;
    private String region;
    private String accessKey;
    private String secretKey;
}
