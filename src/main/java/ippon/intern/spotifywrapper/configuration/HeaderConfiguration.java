package ippon.intern.spotifywrapper.configuration;

import ippon.intern.spotifywrapper.model.AccessToken;
import org.springframework.http.HttpHeaders;
import ippon.intern.spotifywrapper.accessTokenService.AccessTokenService;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class HeaderConfiguration {
    private final AccessTokenService accessTokenService;

    public HeaderConfiguration(RestTemplate restTemplate) {
        this.accessTokenService = new AccessTokenService(restTemplate);
    }

    public HttpHeaders getHeader() {
        return this.buildHeader();
    }

    private HttpHeaders buildHeader() {
        String mediaType = MediaType.APPLICATION_JSON_VALUE;
        HttpHeaders headers = new HttpHeaders();
        AccessToken token;

        if (accessTokenService.isTokenExpired()) {
            accessTokenService.setNewAccessToken();
        }
        token = accessTokenService.getToken();
        headers.add("Authorization", "Bearer " + token.access_token());
        headers.set(HttpHeaders.ACCEPT, mediaType);
        return headers;
    }
}
