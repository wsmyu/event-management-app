package org.humber.project.controllers;

import org.humber.project.domain.Friend;
import org.humber.project.services.FriendJPAService;
import org.humber.project.services.FriendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/friends")
public class FriendController {
    private final FriendService friendService;

    @Autowired
    public FriendController(FriendService friendService) {
        this.friendService = friendService;
    }

    @PostMapping("/add")
    public ResponseEntity<Friend> addFriend(@RequestBody Friend friend) {
        Friend addedFriend = friendService.addFriend(friend);
        return ResponseEntity.status(HttpStatus.CREATED).body(addedFriend);
    }

    @DeleteMapping("/{friendId}/delete")
    public ResponseEntity<String> deleteFriend(@PathVariable Long friendId) {
        friendService.deleteFriend(friendId);
        return ResponseEntity.ok().body("Friend with ID " + friendId + " deleted successfully");
    }

    @GetMapping("/all")
    public ResponseEntity<List<Friend>> getAllFriends() {
        List<Friend> allFriends = friendService.getAllFriends();
        return ResponseEntity.ok().body(allFriends);
    }
}