package org.humber.project.controllers;

import org.humber.project.domain.Friend;
import org.humber.project.services.FriendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users/{userId}/friends")
public class FriendController {
    private final FriendService friendService;

    @Autowired
    public FriendController(FriendService friendService) {
        this.friendService = friendService;
    }

    @PostMapping("/add")
    public ResponseEntity<Friend> addFriend(@PathVariable Long userId, @RequestBody Friend friend) {
        // Log the received userId and friendUserId for verification
        System.out.println("Received userId: " + userId);
        System.out.println("Received friendUserId: " + friend.getFriendUserId());

        // Set the userId from the path variable
        friend.setUserId(userId);
        friend.setPending(true);

        // Set the friendUserId from the request body
        Long friendUserId = friend.getFriendUserId();
        friend.setFriendUserId(friendUserId);

        // Log the friend object to check its values
        System.out.println("Received Friend object: " + friend);

        // Call the service to add the friend
        Friend addedFriend = friendService.addFriend(friend);

        // Return the response with the added friend
        return ResponseEntity.status(HttpStatus.CREATED).body(addedFriend);
    }

    @PutMapping("/{friendId}/accept")
    public ResponseEntity<String> acceptFriendRequest(@PathVariable Long userId, @PathVariable Long friendId) {
        friendService.acceptFriendRequest(friendId);
        return ResponseEntity.ok().body("Friend request accepted successfully for user ID " + userId);
    }



    @DeleteMapping("/{friendId}/delete")
    public ResponseEntity<String> deleteFriend(@PathVariable Long userId, @PathVariable Long friendId) {
        friendService.deleteFriend(friendId);
        return ResponseEntity.ok().body("Friend with ID " + friendId + " deleted successfully for user ID " + userId);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Friend>> getAllFriends(@PathVariable Long userId) {
        List<Friend> allFriends = friendService.getAllFriends(userId);
        return ResponseEntity.ok().body(allFriends);
    }

    @GetMapping("/requests")
    public ResponseEntity<List<Friend>> getAllFriendRequests(@PathVariable Long userId) {
        List<Friend> friendRequests = friendService.getAllFriendRequests(userId);
        return ResponseEntity.ok().body(friendRequests);
    }
}
