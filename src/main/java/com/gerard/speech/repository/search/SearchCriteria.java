package com.gerard.speech.repository.search;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class SearchCriteria {
    private String key;
    private SearchOperation operation;
    private String value;
}
