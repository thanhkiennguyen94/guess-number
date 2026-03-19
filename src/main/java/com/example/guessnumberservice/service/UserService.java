package com.example.guessnumberservice.service;

import com.example.guessnumberservice.response.LeaderboardResponse;
import com.example.guessnumberservice.response.UserInfo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {
    List<LeaderboardResponse> getLeaderboard();
    UserInfo getInfo();
}
