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
        // Set the userId from the path variable
        friend.setUserId(userId);

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


    @DeleteMapping("/{friendId}/delete")
    public ResponseEntity<String> deleteFriend(@PathVariable Long userId, @PathVariable Long friendId) {
        friendService.deleteFriend(friendId);
        return ResponseEntity.ok().body("Friend with ID " + friendId + " deleted successfully for user ID " + userId);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Friend>> getAllFriends(@PathVariable Long userId) {
        List<Friend> allFriends = friendService.getAllFriends();
        return ResponseEntity.ok().body(allFriends);
    }
}
