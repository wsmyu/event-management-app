package org.humber.project.services.impl;

import org.humber.project.domain.Friend;
import org.humber.project.services.FriendJPAService;
import org.humber.project.services.FriendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FriendServiceImpl implements FriendService {
    private final FriendJPAService friendJPAService;

    @Autowired
    public FriendServiceImpl(FriendJPAService friendJPAService) {
        this.friendJPAService = friendJPAService;
    }

    @Override
    public Friend addFriend(Friend friend) {
        return friendJPAService.addFriend(friend);
    }

    @Override
    public void deleteFriend(Long friendId) {
        friendJPAService.deleteFriend(friendId);
    }

    @Override
    public List<Friend> getAllFriends() {
        return friendJPAService.getAllFriends();
    }
}