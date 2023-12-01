package partydj.backend.rest.domain;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.nio.file.Files;

import static org.assertj.core.api.Assertions.assertThat;

public class SpotifyCredentialTest {
    @Test
    @SneakyThrows
    public void shouldSerialize() {
        final SpotifyCredential spotifyCredential = SpotifyCredential.builder()
                .id(1)
                .state("state")
                .token("token")
                .refreshToken("refreshToken")
                .owner(null)
                .build();
        final ObjectMapper objectMapper = new ObjectMapper();
        final String actual = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(spotifyCredential);

        final File jsonFile = ResourceUtils.getFile("classpath:spotifyCredential.json");
        final String expected = Files.readString(jsonFile.toPath());

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @SneakyThrows
    void shouldDeserialize() {
        final ObjectMapper objectMapper = new ObjectMapper();
        final SpotifyCredential actual = objectMapper.readValue(
                ResourceUtils.getFile("classpath:spotifyCredential.json"), SpotifyCredential.class);

        final SpotifyCredential expected = SpotifyCredential.builder()
                .id(1)
                .state("state")
                .token("token")
                .refreshToken("refreshToken")
                .owner(null)
                .build();

        assertThat(actual).isEqualTo(expected);
    }
}