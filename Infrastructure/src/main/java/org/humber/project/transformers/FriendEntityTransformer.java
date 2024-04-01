package org.humber.project.transformers;

import org.humber.project.domain.Friend;
import org.humber.project.entities.FriendEntity;
import org.humber.project.entities.UserEntity;
import org.humber.project.repositories.UserJPARepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class FriendEntityTransformer {


    public FriendEntity transformToFriendEntity(Friend friend, UserJPARepository userJPARepository) {
        FriendEntity friendEntity = new FriendEntity();
        UserEntity userEntity = userJPARepository.findById(friend.getUserId()).orElse(null);
        UserEntity friendUserEntity = userJPARepository.findById(friend.getFriendUserId()).orElse(null);

        friendEntity.setFriendId(friend.getFriendId());
        friendEntity.setUserEntity(userEntity);
        friendEntity.setFriendUserEntity(friendUserEntity);
        friendEntity.setPending(true); // Set pending to true by default
        return friendEntity;
    }

    public static Friend transformToFriend(FriendEntity friendEntity) {
        return Friend.builder()
                .friendId(friendEntity.getFriendId())
                .userId(friendEntity.getUserEntity().getUserId())
                .friendUserId(friendEntity.getFriendUserEntity().getUserId())
                .pending(friendEntity.isPending()) // Map pending attribute from FriendEntity to Friend
                .build();
    }

    public static List<Friend> transformToFriends(List<FriendEntity> entities) {
        return entities.stream()
                .map(FriendEntityTransformer::transformToFriend)
                .collect(Collectors.toList());
    }
}
