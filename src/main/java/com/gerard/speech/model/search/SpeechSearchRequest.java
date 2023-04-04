package com.gerard.speech.model.search;

import com.gerard.speech.repository.search.SearchCriteria;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class SpeechSearchRequest {
    private List<SearchCriteria> criteriaList;

    private int pageNumber;
    private int pageSize;

}
