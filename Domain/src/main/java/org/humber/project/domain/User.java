package org.humber.project.domain;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User {
    private Long userId;
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private String email;

}
