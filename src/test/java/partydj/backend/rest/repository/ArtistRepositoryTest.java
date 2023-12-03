package partydj.backend.rest.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import partydj.backend.rest.domain.Artist;
import partydj.backend.rest.domain.TrackInQueue;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class ArtistRepositoryTest {
    @Autowired
    private ArtistRepository artistRepository;

    @Autowired
    TestEntityManager entityManager;

    private Artist artist;

    @BeforeEach
    void init() {
        artist = Artist.builder().name("name").tracks(new HashSet<>()).build();
    }

    @Test
    public void givenNewArtist_whenSave_thenSuccess() {
        Artist savedArtist = artistRepository.save(artist);

        assertThat(entityManager.find(Artist.class, savedArtist.getId())).isEqualTo(artist);
    }

    @Test
    public void givenArtist_whenDelete_thenSuccess() {
        entityManager.persist(artist);

        artistRepository.delete(artist);

        assertThat(entityManager.find(Artist.class, artist.getId())).isNull();
    }

    @Test
    public void givenNewArtists_whenSaveAll_thenSuccess() {
        Artist artist2 = Artist.builder().name("name2").tracks(new HashSet<>()).build();

        Iterable<Artist> artists = artistRepository.saveAll(Set.of(artist, artist2));

        Artist savedArtist1 = entityManager.find(Artist.class, artist.getId());
        Artist savedArtist2 = entityManager.find(Artist.class, artist2.getId());
        assertThat(artists).containsAll(Set.of(savedArtist1, savedArtist2)).hasSize(2);
    }

    @Test
    public void givenArtists_whenFindAllByNameInList_thenSuccess() {
        final List<String> names = List.of("name1", "name2", "name3");
        final Artist artist2 = Artist.builder().name("name2").tracks(new HashSet<>()).build();
        entityManager.persist(artist);
        entityManager.persist(artist2);

        HashSet<Artist> artists = artistRepository.findAllByNameIn(names);

        assertThat(artists).contains(artist2).doesNotContain(artist);
    }

    @Test
    public void givenArtists_whenFindAllByTracksContainingTrack_thenSuccess() {
        final TrackInQueue track = TrackInQueue.builder().title("title").uri("uri").coverUri("coverUri").build();
        final Artist artist2 = Artist.builder().name("name2").tracks(Set.of(track)).build();
        entityManager.persist(track);
        entityManager.persist(artist);
        entityManager.persist(artist2);

        HashSet<Artist> artists = artistRepository.findAllByTracksContaining(track);

        assertThat(artists).contains(artist2).doesNotContain(artist);
    }
}