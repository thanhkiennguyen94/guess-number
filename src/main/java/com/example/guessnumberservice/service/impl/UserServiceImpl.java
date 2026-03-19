package com.example.guessnumberservice.service.impl;

import com.example.guessnumberservice.exception_handler.exception.NotFoundException;
import com.example.guessnumberservice.model.User;
import com.example.guessnumberservice.repository.UserRepository;
import com.example.guessnumberservice.response.LeaderboardResponse;
import com.example.guessnumberservice.response.UserInfo;
import com.example.guessnumberservice.service.UserService;
import com.example.guessnumberservice.util.ConstMessageError;
import com.example.guessnumberservice.util.FuncHelper;
import com.example.guessnumberservice.util.MessageUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final MessageUtil messageUtil;

    @Override
    @Cacheable(value = "leaderboard", key = "'top10'", sync = true)
    public List<LeaderboardResponse> getLeaderboard() {
        return userRepository.findTop10ByOrderByScoreDesc()
                .stream()
                .map(user -> new LeaderboardResponse(
                        user.getUsername(),
                        user.getScore()
                ))
                .toList();
    }

    @Override
    public UserInfo getInfo() {
        String username = FuncHelper.getUsernameByToken();
        if (username == null) {
            throw new NotFoundException(messageUtil.getMessage(ConstMessageError.User.USERNAME_NOT_FOUND));
        }
        Optional<User> user = userRepository.findByUsername(username);
        if(user.isEmpty()){
            throw new NotFoundException(messageUtil.getMessage(ConstMessageError.User.USERNAME_NOT_FOUND));
        }
        return new UserInfo(user.get().getScore(), user.get().getTurns());
    }
}
