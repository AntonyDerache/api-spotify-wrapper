package ippon.intern.spotifywrapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Nested;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import ippon.intern.constant.RoutesTemplate;
import ippon.intern.spotifywrapper.model.AccessToken;
import ippon.intern.spotifywrapper.services.SpotifyService.SpotifyService;

@SpringBootTest
@AutoConfigureMockMvc
public class SpotifyServiceTest {
    private SpotifyService spotifyService;

    @MockBean
    private RestTemplate restTemplate;

    @BeforeEach
    private void init() {
        spotifyService = new SpotifyService(restTemplate);
    }

    @Test
    public void requestSpotifyWithoutAccessToken() {
        String URL = "https://api.spotify.com/v1/search?q=Travis&type=album";

        when(restTemplate
                .exchange(eq(URL), eq(HttpMethod.GET), any(), eq(String.class)))
                .thenReturn(ResponseEntity.ok("{albums: {name: DylanProd,total: 1}}"));
        assertThrows(
                NullPointerException.class,
                () -> spotifyService.searchItemsSpotify(RoutesTemplate.SEARCH, "Travis", "album"));
    }

    @Nested
    class NestedAccessTokenDependent {

        @BeforeEach
        private void init() {
            when(restTemplate
                .postForEntity(eq("https://accounts.spotify.com/api/token"), any(), eq(AccessToken.class)))
                .thenReturn(ResponseEntity.ok(new AccessToken("BQA2AWUcfNj2DDOtPLrngI5HYmRmJTE5aKJ8QexrscewhsfCDPOxHEED0MA2T1Y3B36Cgs4H514BvBL9")));
        }

        @Test
        public void searchForAnItem() {
            String URL = "https://api.spotify.com/v1/search?q=Travis&type=album";
            String expectedOutput = "{albums: {name: DylanProd,total: 1}}";

            when(restTemplate
                    .exchange(eq(URL), eq(HttpMethod.GET), any(), eq(String.class)))
                    .thenReturn(ResponseEntity.ok(expectedOutput));
            ResponseEntity<String> response = spotifyService.searchItemsSpotify(RoutesTemplate.SEARCH, "Travis",
                    "album");
            assertEquals(response.getBody(), "{albums: {name: DylanProd,total: 1}}");
        }

        @Test
        public void getAnArtist() {
            String URL = "https://api.spotify.com/v1/search?q=Travis&type=album,tracks,playlist,artist";
            String expectedOutput = "{albums: {name: DylanProd,total: 1}}";
            when(restTemplate
                    .exchange(eq(URL), eq(HttpMethod.GET), any(), eq(String.class)))
                    .thenReturn(ResponseEntity.ok(expectedOutput));
            ResponseEntity<String> response = spotifyService.searchItemsSpotify(RoutesTemplate.SEARCH, "Travis",
                    "album,tracks,playlist,artist");
            assertEquals(response.getBody(), "{albums: {name: DylanProd,total: 1}}");
        }

        @Test
        public void getRecommendations() {
            String URL = "https://api.spotify.com/v1/recommendations?limit=20&seed_genres=classical";
            String expectedOutput = "{ tracks: { name: toto}, seed: { name: classical } }";
            when(restTemplate
                    .exchange(eq(URL), eq(HttpMethod.GET), any(), eq(String.class)))
                    .thenReturn(ResponseEntity.ok(expectedOutput));
            ResponseEntity<String> response = spotifyService.getRecommendations(RoutesTemplate.RECOMMENDATIONS,
                    "genres", "classical");
            assertEquals(response.getBody(), "{ tracks: { name: toto}, seed: { name: classical } }");
        }

        @Test
        public void getAlbums() {
            String URL = "https://api.spotify.com/v1/albums/4aawyAB9vmqN3uQ7FjRGTy";
            String expectedOutput = "{ id: toto, name: 'toto's album' }";
            when(restTemplate
                    .exchange(eq(URL), eq(HttpMethod.GET), any(), eq(String.class)))
                    .thenReturn(ResponseEntity.ok(expectedOutput));
            ResponseEntity<String> response = spotifyService.getAlbums(RoutesTemplate.ALBUMS, "4aawyAB9vmqN3uQ7FjRGTy");
            assertEquals(response.getBody(), "{ id: toto, name: 'toto's album' }");
        }

        @Test
        public void getArtist() {
            String URL = "https://api.spotify.com/v1/artists/0TnOYISbd1XYRBk9myaseg";
            String expectedOutput = "{ id: toto, name: Orelsan }";
            when(restTemplate
                    .exchange(eq(URL), eq(HttpMethod.GET), any(), eq(String.class)))
                    .thenReturn(ResponseEntity.ok(expectedOutput));
            ResponseEntity<String> response = spotifyService.getArtists(RoutesTemplate.ARTISTS,
                    "0TnOYISbd1XYRBk9myaseg");
            assertEquals(response.getBody(), "{ id: toto, name: Orelsan }");
        }

        @Test
        public void getArtistAlbums() {
            String URL = "https://api.spotify.com/v1/artists/0TnOYISbd1XYRBk9myaseg/albums";
            String expectedOutput = "{ id: toto, name: Orelsan }";
            when(restTemplate
                    .exchange(eq(URL), eq(HttpMethod.GET), any(), eq(String.class)))
                    .thenReturn(ResponseEntity.ok(expectedOutput));
            ResponseEntity<String> response = spotifyService.getArtistAlbums(RoutesTemplate.ARTISTS_ALBUMS,
                    "0TnOYISbd1XYRBk9myaseg");
            assertEquals(response.getBody(), "{ id: toto, name: Orelsan }");
        }

        @Test
        public void getArtistTopTracks() {
            String URL = "https://api.spotify.com/v1/artists/0TnOYISbd1XYRBk9myaseg/top-tracks?market=FR";
            String expectedOutput = "{ id: toto, name: Orelsan }";
            when(restTemplate
                    .exchange(eq(URL), eq(HttpMethod.GET), any(), eq(String.class)))
                    .thenReturn(ResponseEntity.ok(expectedOutput));
            ResponseEntity<String> response = spotifyService.getArtistTopTracks(RoutesTemplate.ARTISTS_TOP_TRACKS,
                    "0TnOYISbd1XYRBk9myaseg");
            assertEquals(response.getBody(), "{ id: toto, name: Orelsan }");
        }
    }
}
