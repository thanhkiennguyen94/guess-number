package com.example.guessnumberservice.util;

public class ConstMessageError {
    private ConstMessageError() {
        throw new UnsupportedOperationException("Utility class");
    }

    public static class Auth {
        public static final String LOGIN_INVALID = "login.invalid";
    }

    public static class User {
        public static final String DUPLICATE_USERNAME = "username.exists";
        public static final String USERNAME_NOT_FOUND = "username.not.found";
    }

    public static class Game {
        public static final String NO_TURN = "game.no.turn";
    }
}
