package org.humber.project.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "feedback")
public class FeedbackEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "feedback_id")
    private Long feedbackId;

    @JoinColumn(name = "user_id")
    @ManyToOne
    private UserEntity userEntity;

    @Column(name = "rating")
    private int rating;

    @Column(name = "message", length = 1000)
    private String message;

    @Column(name = "timestamp")
    private Date timestamp;

}
