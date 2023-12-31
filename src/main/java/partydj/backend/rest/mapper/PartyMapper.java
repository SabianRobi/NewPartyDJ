package partydj.backend.rest.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import partydj.backend.rest.entity.Party;
import partydj.backend.rest.entity.response.PartyResponse;
import partydj.backend.rest.entity.response.SpotifyDeviceIdResponse;

@Component
public class PartyMapper {

    @Autowired
    UserMapper userMapper;

    @Autowired
    TrackMapper trackMapper;

    public PartyResponse mapPartyToPartyResponse(final Party party) {
        return PartyResponse.builder()
                .id(party.getId())
                .name(party.getName())
                .tracksInQueue(party.getTracksInQueue().stream().map(track -> trackMapper.mapTrackToTrackInQueueResponse(track)).toList())
//                .previousTracks(party.getPreviousTracks().stream().map(track -> trackMapper.mapTrackToTrackInQueue(track)).toList())
                .participants(party.getParticipants().stream().map(user -> userMapper.mapUserToUserInPartyResponse(user)).toList())
                .build();
    }

    public SpotifyDeviceIdResponse mapPartyToSpotifyDeviceId(final Party party) {
        return new SpotifyDeviceIdResponse(party.getSpotifyDeviceId());
    }
}
