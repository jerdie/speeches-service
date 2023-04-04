package com.gerard.speech.repository.search;

import com.gerard.speech.model.domain.Speech;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

@RequiredArgsConstructor
public class SpeechSpecificationBuilder {

    private final List<SearchCriteria> criteriaList;

    public Specification<Speech> build() {
        Specification<Speech> result = new SpeechSpecification(criteriaList.get(0));
        for (SearchCriteria criteria : criteriaList.subList(1, criteriaList.size())) {
            result = Specification.where(result).and(new SpeechSpecification(criteria));
        }
        return result;
    }


}
