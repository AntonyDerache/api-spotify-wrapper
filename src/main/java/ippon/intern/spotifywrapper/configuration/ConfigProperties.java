package ippon.intern.spotifywrapper.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties
public class ConfigProperties {
    public static String clientId;
    public static String clientSecret;

    @Autowired
    public ConfigProperties(@Value("${env.clientid}") String clientId, @Value("${env.clientsecret}") String clientSecret) {
        ConfigProperties.clientId = clientId;
        ConfigProperties.clientSecret = clientSecret;
    }
}