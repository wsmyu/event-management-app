package org.humber.project.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Friend {
    private Long friendId;
    private Long userId;
    private Long friendUserId;
    private boolean pending;
}
