package org.humber.project.services.impl;

import org.humber.project.domain.Friend;
import org.humber.project.entities.FriendEntity;
import org.humber.project.repositories.FriendJPARepository;
import org.humber.project.repositories.UserJPARepository;
import org.humber.project.services.FriendJPAService;
import org.humber.project.transformers.FriendEntityTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class FriendJPAServiceImpl implements FriendJPAService {

    private final FriendJPARepository friendJPARepository;
    private final UserJPARepository userJPARepository;
    private final FriendEntityTransformer friendEntityTransformer;

    @Autowired
    public FriendJPAServiceImpl(FriendJPARepository friendJPARepository, UserJPARepository userJPARepository, FriendEntityTransformer friendEntityTransformer) {
        this.friendJPARepository = friendJPARepository;
        this.userJPARepository = userJPARepository;
        this.friendEntityTransformer = friendEntityTransformer;
    }

    @Override
    public Friend addFriend(Friend friend) {
        try {
            FriendEntity friendEntity = friendEntityTransformer.transformToFriendEntity(friend, userJPARepository);
            FriendEntity savedEntity = friendJPARepository.save(friendEntity);
            return FriendEntityTransformer.transformToFriend(savedEntity);
        } catch (Exception e) {
            // Handle the exception
            return null;
        }
    }

    @Override
    public void deleteFriend(Long friendId) {
        Optional<FriendEntity> friendEntityOptional = friendJPARepository.findById(friendId);
        friendEntityOptional.ifPresentOrElse(
                friendJPARepository::delete,
                () -> {
                    // Handle the case when friendEntity is not present
                }
        );
    }

    @Override
    public void acceptFriendRequest(Long friendId) {
        Optional<FriendEntity> friendEntityOptional = friendJPARepository.findById(friendId);
        friendEntityOptional.ifPresent(friendEntity -> {
            friendEntity.setPending(false); // Mark the friend request as accepted
            friendJPARepository.save(friendEntity);
        });
    }

    @Override
    public List<Friend> getAllFriends(Long userId) {
        List<FriendEntity> friendEntities = friendJPARepository.findAll();
        return friendEntities.stream()
                .filter(friendEntity -> (friendEntity.getUserEntity().getUserId().equals(userId)
                        || friendEntity.getFriendUserEntity().getUserId().equals(userId))
                        && !friendEntity.isPending()) // Filter friends based on userId and pending status
                .map(FriendEntityTransformer::transformToFriend)
                .collect(Collectors.toList());
    }

    @Override
    public List<Friend> getAllFriendRequests(Long userId) {
        List<FriendEntity> friendEntities = friendJPARepository.findAll();
        return friendEntities.stream()
                .filter(friendEntity ->
                        friendEntity.getFriendUserEntity().getUserId().equals(userId)
                                && friendEntity.isPending()) // Filter friend requests based on friendUserEntity.userId and pending status
                .map(FriendEntityTransformer::transformToFriend)
                .collect(Collectors.toList());
    }

    @Override
    public boolean areFriends(Long userId1, Long userId2) {
        List<FriendEntity> friendEntities = friendJPARepository.findAll();
        return friendEntities.stream()
                .anyMatch(friendEntity ->
                        (friendEntity.getUserEntity().getUserId().equals(userId1) && friendEntity.getFriendUserEntity().getUserId().equals(userId2))
                                || (friendEntity.getUserEntity().getUserId().equals(userId2) && friendEntity.getFriendUserEntity().getUserId().equals(userId1))
                                && !friendEntity.isPending());
    }

}
