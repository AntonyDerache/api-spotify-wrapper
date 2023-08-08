package ippon.intern.spotifywrapper.controllers.RestSpotifyController;

import org.springframework.http.HttpEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import ippon.intern.constant.RoutesTemplate;
import ippon.intern.spotifywrapper.model.Greeting;
import ippon.intern.spotifywrapper.services.SpotifyService.SpotifyService;

@CrossOrigin
@RestController
public class RestSpotifyController {
    private final SpotifyService spotifyService;

    public RestSpotifyController(RestTemplate restTemplate) {
        spotifyService = new SpotifyService(restTemplate);
    }

    @RequestMapping(value="/", method = RequestMethod.GET)
    public Greeting greeting() {
        return new Greeting("I am alive");
    }

    @RequestMapping(value="/" + RoutesTemplate.SEARCH, method = RequestMethod.GET)
    public String search(@RequestParam("q") String q, @RequestParam("type") String type) {
        HttpEntity<String> response = spotifyService.searchItemsSpotify(RoutesTemplate.SEARCH, q, type);
        return response.getBody();
    }

    @RequestMapping(value="/" + RoutesTemplate.RECOMMENDATIONS, method = RequestMethod.GET)
    public String getRecommendations(@RequestParam("seedType") String seedType, @RequestParam("genres") String genres) {
        HttpEntity<String> response = spotifyService.getRecommendations(RoutesTemplate.RECOMMENDATIONS, seedType, genres);
        return response.getBody();
    }

    @RequestMapping(value="/" + RoutesTemplate.ALBUMS + "/{albumId}", method = RequestMethod.GET)
    public String getAlbum(@PathVariable(value="albumId") String albumId) {
        HttpEntity<String> response = spotifyService.getAlbums(RoutesTemplate.ALBUMS, albumId);
        return response.getBody();
    }

    @RequestMapping(value="/" + RoutesTemplate.ARTISTS + "/{artistId}", method = RequestMethod.GET)
    public String getArtists(@PathVariable(value="artistId") String artistId) {
        HttpEntity<String> response = spotifyService.getArtists(RoutesTemplate.ARTISTS, artistId);
        return response.getBody();
    }

    @RequestMapping(value="/" + RoutesTemplate.ARTISTS + "/{artistId}/" + RoutesTemplate.ALBUMS, method = RequestMethod.GET)
    public String getArtistAlbums(@PathVariable(value="artistId") String artistId) {
        HttpEntity<String> response = spotifyService.getArtistAlbums(RoutesTemplate.ARTISTS_ALBUMS, artistId);
        return response.getBody();
    }

    @RequestMapping(value="/" + RoutesTemplate.ARTISTS + "/{artistId}/" + RoutesTemplate.TOP_TRACKS, method = RequestMethod.GET)
    public String getArtistTopTracks(@PathVariable(value="artistId") String artistId) {
        HttpEntity<String> response = spotifyService.getArtistTopTracks(RoutesTemplate.ARTISTS_TOP_TRACKS, artistId);
        return response.getBody();
    }
}
