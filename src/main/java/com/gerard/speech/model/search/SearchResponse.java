package com.gerard.speech.model.search;

import com.gerard.speech.model.domain.Speech;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
public class SearchResponse {
    private List<Speech> data;
}
