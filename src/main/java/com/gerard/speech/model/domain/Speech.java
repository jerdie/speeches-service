package com.gerard.speech.model.domain;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Speech {
    @Id
    @GenericGenerator(name = "SPEECH_ID_GENERATOR", strategy = "sequence", parameters = {
            @Parameter(name = "sequence_name", value = "speech_sequence"),
            @Parameter(name = "allocationSize", value = "1")
    })
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Lob
    private String content;
    private String author;
    private String keywords;
    private LocalDate date;
}
