package org.humber.project.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="friends")
public class FriendEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "friend_id")
    private Long friendId;

    @JoinColumn(name = "user_id")
    @ManyToOne
    private UserEntity userEntity;

    @JoinColumn(name = "friend_user_id")
    @ManyToOne
    private UserEntity friendUserEntity;

    @Column(name = "pending")
    private boolean pending;
}
