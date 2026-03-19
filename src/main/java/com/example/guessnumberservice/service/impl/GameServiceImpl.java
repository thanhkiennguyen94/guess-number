package com.example.guessnumberservice.service.impl;

import com.example.guessnumberservice.exception_handler.exception.SystemErrorException;
import com.example.guessnumberservice.repository.UserRepository;
import com.example.guessnumberservice.response.GuessResponse;
import com.example.guessnumberservice.service.GameService;
import com.example.guessnumberservice.util.*;
import lombok.RequiredArgsConstructor;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GameServiceImpl implements GameService {

    private final UserRepository userRepository;
    private final MessageUtil messageUtil;
    private final GameTransactionService gameTransactionService;

    @Override
    public GuessResponse guess(int userGuess) {
        return guessWithRetry(userGuess);
    }

    private GuessResponse guessWithRetry(int userGuess) {
        int maxRetry = 3;
        for (int i = 0; i < maxRetry; i++) {
            try {
                return gameTransactionService.doGuess(userGuess);
            } catch (ObjectOptimisticLockingFailureException ex) {
                if (i == maxRetry - 1) {
                    throw new RuntimeException(ConstUtils.SERVER_BUSY);
                }
            }
        }

        throw new SystemErrorException(ConstUtils.SYSTEM_ERROR_MSG);
    }


}
