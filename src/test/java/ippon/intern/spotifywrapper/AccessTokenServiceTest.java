package ippon.intern.spotifywrapper;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.eq;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import ippon.intern.spotifywrapper.model.AccessToken;
import ippon.intern.spotifywrapper.accessTokenService.AccessTokenService;

@SpringBootTest
public class AccessTokenServiceTest {
    private AccessTokenService accessTokenService;

    @MockBean
    private RestTemplate restTemplate;

    @BeforeEach
    public void init() {
        accessTokenService = new AccessTokenService(restTemplate);
    }

    @Test
    public void getNewAccessToken() {
        when(restTemplate
            .postForEntity(eq("https://accounts.spotify.com/api/token"), any(), eq(AccessToken.class)))
            .thenReturn(ResponseEntity.ok(new AccessToken("BQA2AWUcfNj2DDOtPLrngI5HYmRmJTE5aKJ8QexrscewhsfCDPOxHEED0MA2T1Y3B36Cgs4H514BvBL9")));
        accessTokenService.setNewAccessToken();
        AccessToken token = accessTokenService.getToken();
        Boolean isExpired = accessTokenService.isTokenExpired();
        assertSame("BQA2AWUcfNj2DDOtPLrngI5HYmRmJTE5aKJ8QexrscewhsfCDPOxHEED0MA2T1Y3B36Cgs4H514BvBL9", token.access_token());
        assertFalse(isExpired);
    }
}
