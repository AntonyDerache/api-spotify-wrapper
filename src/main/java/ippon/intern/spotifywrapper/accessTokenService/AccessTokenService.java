package ippon.intern.spotifywrapper.accessTokenService;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.Base64;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import ippon.intern.spotifywrapper.configuration.ConfigProperties;
import ippon.intern.spotifywrapper.model.AccessToken;

@Service
public class AccessTokenService {
    private final String base64token;
    private final RestTemplate restTemplate;
    private static final String URL = "https://accounts.spotify.com/api/token";
    private AccessToken accessToken;
    private Timestamp currentTokenTimestamp;

    public AccessTokenService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
        String clientSecret = ConfigProperties.clientSecret;
        String clientId = ConfigProperties.clientId;
        base64token = Base64.getEncoder()
                .encodeToString((clientId + ':' + clientSecret)
                        .getBytes());
    }

    public AccessToken getToken() {
        return accessToken;
    }

    public Boolean isTokenExpired() {
        return currentTokenTimestamp == null
                || (Timestamp.from(Instant.now()).getTime() - currentTokenTimestamp.getTime()) > 3600;
    }

    public void setNewAccessToken() {
        HttpHeaders headers = buildHeaders();
        HttpEntity<MultiValueMap<String, String>> entity = buildEntity(headers);

        ResponseEntity<AccessToken> response = this.restTemplate.postForEntity(URL, entity, AccessToken.class);
        AccessToken bodyAccessToken = response.getBody();
        if (bodyAccessToken != null) {
            accessToken = bodyAccessToken;
            currentTokenTimestamp = Timestamp.from(Instant.now());
        }
    }

    private HttpHeaders buildHeaders() {
        HttpHeaders headers = new HttpHeaders();

        headers.add("Authorization", "Basic " + base64token);
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        return headers;
    }

    private HttpEntity<MultiValueMap<String, String>> buildEntity(HttpHeaders headers) {
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();

        map.add("grant_type", "client_credentials");
        return new HttpEntity<>(map, headers);
    }
}
