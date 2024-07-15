package ippon.intern.spotifywrapper.services.SpotifyService;

import java.util.HashMap;
import java.util.Map;

import ippon.intern.spotifywrapper.configuration.HeaderConfiguration;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Service
public class SpotifyService {
    private final String baseURL = "https://api.spotify.com/v1/";
    private final RestTemplate restTemplate;
    private final HeaderConfiguration headers;

    public SpotifyService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
        this.headers = new HeaderConfiguration(restTemplate);
    }

    public ResponseEntity<String> searchItemsSpotify(String resourcePath, String q, String type) {
        Map<String, String> queryParams = new HashMap<>();
        queryParams.put("q", q);
        queryParams.put("type", type);
        queryParams.put("limit", "10");
        String urlTemplate = buildUrl(baseURL + resourcePath, queryParams);
        HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(this.headers.getHeader());
        return restTemplate.exchange(urlTemplate, HttpMethod.GET, entity, String.class);
    }

    public ResponseEntity<String> getRecommendations(String resourcePath, String seedType, String genres) {
        Map<String, String> queryParams = new HashMap<>();
        queryParams.put("limit", "20");
        queryParams.put("seed_" + seedType, genres);
        String urlTemplate = buildUrl(baseURL + resourcePath, queryParams);
        HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(this.headers.getHeader());
        return restTemplate.exchange(urlTemplate, HttpMethod.GET, entity, String.class);
    }

    public ResponseEntity<String> getAlbums(String resourcePath, String albumId) {
        String urlTemplate = buildUrl(baseURL + resourcePath + "/" + albumId);
        HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(this.headers.getHeader());
        return restTemplate.exchange(urlTemplate, HttpMethod.GET, entity, String.class);
    }

    public ResponseEntity<String> getArtists(String resourcePath, String artistId) {
        String urlTemplate = buildUrl(baseURL + resourcePath + "/" + artistId);
        HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(this.headers.getHeader());
        return restTemplate.exchange(urlTemplate, HttpMethod.GET, entity, String.class);
    }

    public ResponseEntity<String> getArtistAlbums(String resourcePath, String artistId) {
        String urlTemplate = buildUrl(baseURL + String.format(resourcePath, artistId));
        HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(this.headers.getHeader());
        return restTemplate.exchange(urlTemplate, HttpMethod.GET, entity, String.class);
    }

    public ResponseEntity<String> getArtistTopTracks(String resourcePath, String artistId) {
        Map<String, String> queryParams = new HashMap<>();
        queryParams.put("market", "FR");
        String urlTemplate = buildUrl(baseURL + String.format(resourcePath, artistId), queryParams);
        HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(this.headers.getHeader());
        return restTemplate.exchange(urlTemplate, HttpMethod.GET, entity, String.class);
    }

    private String buildUrl(String URL, Map<String, String> queryParams) {
        UriComponentsBuilder urlTemplate = UriComponentsBuilder.fromHttpUrl(URL);

        queryParams.forEach(urlTemplate::queryParam);
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
