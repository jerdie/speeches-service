package com.gerard.speech.model;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SpeechDto {
    private Long id;
    private String content;
    private String author;
    private String keywords;
    private LocalDate date;
}
