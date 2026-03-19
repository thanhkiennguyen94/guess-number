package com.example.guessnumberservice.service.impl;

import com.example.guessnumberservice.exception_handler.exception.InvalidDataException;
import com.example.guessnumberservice.exception_handler.exception.NotFoundException;
import com.example.guessnumberservice.exception_handler.exception.SystemErrorException;
import com.example.guessnumberservice.model.User;
import com.example.guessnumberservice.repository.UserRepository;
import com.example.guessnumberservice.response.GuessResponse;
import com.example.guessnumberservice.util.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.ThreadLocalRandom;

@Service
@RequiredArgsConstructor
public class GameTransactionService {

    private final UserRepository userRepository;
    private final MessageUtil messageUtil;

    @Transactional
    public GuessResponse doGuess(int userGuess) {
        // Chống sinh ra các bug vì config sai dù IDE cảnh báo nhưng ko được xóa
        if (ConfigGame.RANDOM_FROM >= ConfigGame.RANDOM_TO ||
                (ConfigGame.RANDOM_TO - ConfigGame.RANDOM_FROM) < 1) {
            throw new SystemErrorException("Random config không hợp lệ: RANDOM_FROM < RANDOM_TO");
        }
        String username = FuncHelper.getUsernameByToken();
        if (username == null) {
            throw new NotFoundException(messageUtil.getMessage(ConstMessageError.User.USERNAME_NOT_FOUND));
        }
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new NotFoundException(messageUtil.getMessage(ConstMessageError.User.USERNAME_NOT_FOUND)));

        if (user.getTurns() <= 0) {
            throw new InvalidDataException(messageUtil.getMessage(ConstMessageError.Game.NO_TURN));
        }

        user.setTurns(user.getTurns() - 1);
        double winRate = (double) ConfigGame.WIN_RATE / 100;
        // Cach hieu 1 nguoi choi doan dung so va co 5% random tu may tinh tuc la P = 0.2 * 0.05 = 1% co hoi chien thang
//        int randomNumber = ThreadLocalRandom.current().nextInt(1, 6);
//        boolean win = (randomNumber == userGuess) && Math.random() < winRate;

        // Cách hiểu 2 người chơi đoán số nào ko quan trọng, quan trọng là NHÂN PHẨM
        boolean win = ThreadLocalRandom.current().nextDouble() < winRate;
        int randomNumber;
        if (win) {
            randomNumber = userGuess;
        } else {
            do {
                randomNumber = ThreadLocalRandom.current().nextInt(ConfigGame.RANDOM_FROM, ConfigGame.RANDOM_TO + 1);
            } while (randomNumber == userGuess);
        }
        if (win) {
            user.setScore(user.getScore() + 1);
        }
        userRepository.save(user);
        return new GuessResponse(
                win,
                randomNumber,
                user.getTurns(),
                user.getScore()
        );
    }
}
