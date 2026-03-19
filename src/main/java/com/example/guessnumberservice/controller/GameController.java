package com.example.guessnumberservice.controller;

import com.example.guessnumberservice.request.GuessRequest;
import com.example.guessnumberservice.response.ApiResponse;
import com.example.guessnumberservice.response.GuessResponse;
import com.example.guessnumberservice.response.LeaderboardResponse;
import com.example.guessnumberservice.response.UserInfo;
import com.example.guessnumberservice.service.GameService;
import com.example.guessnumberservice.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/game")
@RequiredArgsConstructor
public class GameController {

    private final GameService gameService;
    private final UserService userService;

    @PostMapping("/guess")
    public ApiResponse<GuessResponse> guess(@RequestBody @Valid GuessRequest request) {
        return ApiResponse.success(gameService.guess(request.getNumber()));
    }

    @GetMapping("/leaderboard")
    public ApiResponse<List<LeaderboardResponse>> leaderboard() {
        return ApiResponse.success(userService.getLeaderboard());
    }

    @GetMapping("/leaderboard/me")
    public ApiResponse<UserInfo> getInfo() {
        return ApiResponse.success(userService.getInfo());
    }
}