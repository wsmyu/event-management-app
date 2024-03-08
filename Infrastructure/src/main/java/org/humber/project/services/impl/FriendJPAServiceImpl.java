package org.humber.project.services.impl;

import org.humber.project.domain.Friend;
import org.humber.project.entities.FriendEntity;
import org.humber.project.repositories.FriendJPARepository;
import org.humber.project.services.FriendJPAService;
import org.humber.project.transformers.FriendEntityTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FriendJPAServiceImpl implements FriendJPAService {

    private final FriendJPARepository friendJPARepository;

    @Autowired
    public FriendJPAServiceImpl(FriendJPARepository friendJPARepository) {
        this.friendJPARepository = friendJPARepository;
    }

    @Override
    public Friend addFriend(Friend friend) {
        FriendEntity friendEntity = FriendEntityTransformer.transformToFriendEntity(friend);
        FriendEntity savedEntity = friendJPARepository.save(friendEntity);
        return FriendEntityTransformer.transformToFriend(savedEntity);
    }

    @Override
    public void deleteFriend(Long friendId) {
        Optional<FriendEntity> friendEntityOptional = friendJPARepository.findById(friendId);
        friendEntityOptional.ifPresent(friendJPARepository::delete);
        // Add handling for the case when friendEntity is not present
    }

    @Override
    public List<Friend> getAllFriends() {
        List<FriendEntity> friendEntities = friendJPARepository.findAll();
        return FriendEntityTransformer.transformToFriends(friendEntities);
    }
}
