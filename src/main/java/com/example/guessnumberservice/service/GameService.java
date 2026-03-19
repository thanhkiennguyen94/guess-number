package com.example.guessnumberservice.service;

import com.example.guessnumberservice.response.GuessResponse;
import org.springframework.stereotype.Service;

@Service
public interface GameService {
    GuessResponse guess(int userGuess);
}
