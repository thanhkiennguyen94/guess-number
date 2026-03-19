package com.example.guessnumberservice.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class LeaderboardResponse {
    private String username;
    private int score;
}
