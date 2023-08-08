package ippon.intern.spotifywrapper.services.SpotifyService;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import ippon.intern.spotifywrapper.accessTokenService.AccessTokenService;
import ippon.intern.spotifywrapper.model.AccessToken;

@Service
public class SpotifyService {
    private final String baseURL = "https://api.spotify.com/v1/";
    private final AccessTokenService accessTokenService;
    private final RestTemplate restTemplate;

    public SpotifyService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
        this.accessTokenService = new AccessTokenService(restTemplate);
    }

    public ResponseEntity<String> searchItemsSpotify(String resourcePath, String q, String type) {
        HttpHeaders headers = buildHeader(MediaType.APPLICATION_JSON_VALUE);
        Map<String, String> queryParams = new HashMap<String, String>();
        queryParams.put("q", q);
        queryParams.put("type", type);
        String urlTemplate = buildUrl(baseURL + resourcePath, queryParams);
        HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(headers);
        return restTemplate.exchange(urlTemplate, HttpMethod.GET, entity, String.class);
    }

    public ResponseEntity<String> getRecommendations(String resourcePath, String seedType, String genres) {
        HttpHeaders headers = buildHeader(MediaType.APPLICATION_JSON_VALUE);
        Map<String, String> queryParams = new HashMap<String, String>();
        queryParams.put("limit", "20");
        queryParams.put("seed_" + seedType, genres);
        String urlTemplate = buildUrl(baseURL + resourcePath, queryParams);
        HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(headers);
        return restTemplate.exchange(urlTemplate, HttpMethod.GET, entity, String.class);
    }

    public ResponseEntity<String> getAlbums(String resourcePath, String albumId) {
        HttpHeaders headers = buildHeader(MediaType.APPLICATION_JSON_VALUE);
        String urlTemplate = buildUrl(baseURL + resourcePath + "/" + albumId);
        HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(headers);
        return restTemplate.exchange(urlTemplate, HttpMethod.GET, entity, String.class);
    }

    public ResponseEntity<String> getArtists(String resourcePath, String artistId) {
        HttpHeaders headers = buildHeader(MediaType.APPLICATION_JSON_VALUE);
        String urlTemplate = buildUrl(baseURL + resourcePath + "/" + artistId);
        HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(headers);
        return restTemplate.exchange(urlTemplate, HttpMethod.GET, entity, String.class);
    }

    public ResponseEntity<String> getArtistAlbums(String resourcePath, String artistId) {
        HttpHeaders headers = buildHeader(MediaType.APPLICATION_JSON_VALUE);
        String urlTemplate = buildUrl(baseURL + String.format(resourcePath, artistId));
        HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(headers);
        return restTemplate.exchange(urlTemplate, HttpMethod.GET, entity, String.class);
    }

    public ResponseEntity<String> getArtistTopTracks(String resourcePath, String artistId) {
        HttpHeaders headers = buildHeader(MediaType.APPLICATION_JSON_VALUE);
        Map<String, String> queryParams = new HashMap<String, String>();
        queryParams.put("market", "FR");
        String urlTemplate = buildUrl(baseURL + String.format(resourcePath, artistId), queryParams);
        HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(headers);
        return restTemplate.exchange(urlTemplate, HttpMethod.GET, entity, String.class);
    }

    private HttpHeaders buildHeader(String mediaType) {
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

    private String buildUrl(String URL, Map<String, String> queryParams) {
        UriComponentsBuilder urlTemplate = UriComponentsBuilder.fromHttpUrl(URL);

        queryParams.forEach((key, value) -> urlTemplate.queryParam(key, value));
        return urlTemplate
                .encode()
                .toUriString();
    }

    private String buildUrl(String URL) {
        return UriComponentsBuilder.fromHttpUrl(URL)
                .encode()
                .toUriString();
    }
}
