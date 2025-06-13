package ch.zucchinit.zauction.Configurations;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

@Component
@ConfigurationProperties(prefix = "security")
@Getter
@Setter
public class APIConfiguration {
    private List<String> corsOrigin;
    private String apiHeader;
    private String apiSecret;
    private String cookieName;
    private Integer cookieValidity;
}
