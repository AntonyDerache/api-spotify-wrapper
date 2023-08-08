package ippon.intern.spotifywrapper;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import ippon.intern.spotifywrapper.configuration.ConfigProperties;

@SpringBootApplication
@EnableConfigurationProperties(ConfigProperties.class)
public class SpotifyWrapperApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpotifyWrapperApplication.class, args);
	}
}
