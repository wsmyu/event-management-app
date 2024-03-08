package org.humber.project.transformers;

import org.humber.project.domain.Friend;
import org.humber.project.entities.FriendEntity;

import java.util.List;
import java.util.stream.Collectors;

public final class FriendEntityTransformer {

    public static FriendEntity transformToFriendEntity(Friend friend) {
        FriendEntity friendEntity = new FriendEntity();
        friendEntity.setFriendId(friend.getFriendId());
        friendEntity.setUserId(friend.getUserId());
        friendEntity.setFriendUserId(friend.getFriendUserId());
        return friendEntity;
    }

    public static Friend transformToFriend(FriendEntity friendEntity) {
        return Friend.builder()
                .friendId(friendEntity.getFriendId())
                .userId(friendEntity.getUserId())
                .friendUserId(friendEntity.getFriendUserId())
                .build();
    }

    public static List<Friend> transformToFriends(List<FriendEntity> entities) {
        return entities.stream()
                .map(FriendEntityTransformer::transformToFriend)
                .collect(Collectors.toList());
    }
}
