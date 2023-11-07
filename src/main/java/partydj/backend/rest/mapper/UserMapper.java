package partydj.backend.rest.mapper;

import org.springframework.stereotype.Component;
import partydj.backend.rest.domain.User;
import partydj.backend.rest.domain.enums.UserType;
import partydj.backend.rest.domain.request.SaveUserRequest;
import partydj.backend.rest.domain.response.UserResponse;

import java.util.ArrayList;

@Component
public class UserMapper {
    public UserResponse mapUserToUserResponse(final User user) {
        return UserResponse.builder()
                .id(user.getId())
                .email(user.getEmail())
                .username(user.getUsername())
                .build();
    }

    public User mapUserRequestToUser(final SaveUserRequest userRequest) {
        return User.builder()
                .email(userRequest.getEmail())
                .username(userRequest.getUsername())
                .password(userRequest.getPassword())
                .addedTracks(new ArrayList<>())
                .userType(UserType.NORMAL)
                .build();
    }
}