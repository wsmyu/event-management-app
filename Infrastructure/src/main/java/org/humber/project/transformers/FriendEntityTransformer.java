package org.humber.project.transformers;

import org.humber.project.domain.Friend;
import org.humber.project.entities.FriendEntity;

import java.util.List;
import java.util.stream.Collectors;

public class FriendEntityTransformer {
    public static Friend transformToFriend(FriendEntity friendEntity) {
        Friend friend = new Friend();
        friend.setFriendUserId(friendEntity.getFriendUserId());
        friend.setFriendId(friendEntity.getFriendId());
        return friend;
    }

    public static FriendEntity transformToFriendEntity(Friend friend) {
        FriendEntity friendEntity = new FriendEntity();
        friendEntity.setFriendUserId(friend.getFriendUserId());
        friendEntity.setFriendId(friend.getFriendId());
        return friendEntity;
    }

    public static List<Friend> transformToFriends(List<FriendEntity> entities) {
        return entities.stream()
                .map(FriendEntityTransformer::transformToFriend)
                .collect(Collectors.toList());
    }
}
