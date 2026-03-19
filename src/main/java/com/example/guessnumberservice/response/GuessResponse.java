package com.example.guessnumberservice.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class GuessResponse {
    private boolean win;
    private int correctNumber;
    private int remainingTurns;
    private int score;
}
