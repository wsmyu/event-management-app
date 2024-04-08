package org.humber.project.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Date;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Feedback {
    private Long feedbackId;
    private Long userId;
    private int rating;
    private String message;
    private Date timestamp;
}
