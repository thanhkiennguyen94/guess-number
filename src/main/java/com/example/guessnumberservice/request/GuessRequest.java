package com.example.guessnumberservice.request;

import com.example.guessnumberservice.util.ConfigGame;
import com.example.guessnumberservice.util.ValidationConstants;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

@Getter
@Setter
public class GuessRequest {
    @Min(value = ConfigGame.RANDOM_FROM, message = "{guess.min}")
    @Max(value = ConfigGame.RANDOM_TO, message = "{guess.max}")
    private int number;
}