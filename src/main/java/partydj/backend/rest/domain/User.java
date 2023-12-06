package partydj.backend.rest.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import partydj.backend.rest.domain.enums.PartyRole;
import partydj.backend.rest.domain.enums.UserType;

import java.util.Set;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Email
    @Column(unique = true)
    private String email;

    @NotBlank
    @Column(unique = true)
    private String username;

    @NotBlank
    private String password;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "varchar(255) default 'NORMAL'")
    private UserType userType;

    @Enumerated(EnumType.STRING)
    private PartyRole partyRole;

    @EqualsAndHashCode.Exclude
    @OneToOne(cascade = CascadeType.REMOVE, orphanRemoval = true)
    private SpotifyCredential spotifyCredential;

    @EqualsAndHashCode.Exclude
    @ManyToOne
    private Party party;

    @EqualsAndHashCode.Exclude
    @NotNull
    @OneToMany
    private Set<TrackInQueue> addedTracks;

    public void addAddedTrack(final TrackInQueue track) {
        addedTracks.add(track);
    }

    public void removeAddedTrack(final TrackInQueue track) {
        addedTracks.remove(track);
    }
}
