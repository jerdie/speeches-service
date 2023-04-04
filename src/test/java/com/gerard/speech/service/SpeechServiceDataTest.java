package com.gerard.speech.service;

import com.gerard.speech.mappers.SpeechMapper;
import com.gerard.speech.model.domain.Speech;
import com.gerard.speech.model.search.SpeechSearchRequest;
import com.gerard.speech.repository.SpeechRepository;
import com.gerard.speech.repository.search.SearchCriteria;
import com.gerard.speech.repository.search.SearchOperation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class SpeechServiceDataTest {

    SpeechService speechService;

    @Autowired
    SpeechRepository speechRepository;

    @BeforeEach
    void setup() {
        var speech1 = Speech.builder().author("test1").content("test content").date(LocalDate.now()).keywords("test keyword").build();
        speechRepository.save(speech1);

        var speech2 = Speech.builder().author("test2").content("test content2").date(LocalDate.now()).keywords("test speech keyword2").build();
        speechRepository.save(speech2);

        var speech3 = Speech.builder().author("test3").content("test content3").date(LocalDate.now()).keywords("test speech keyword3").build();
        speechRepository.save(speech3);

        var speech4 = Speech.builder().author("test4").content("test content4").date(LocalDate.now().minusDays(1)).keywords("test speech keyword4").build();
        speechRepository.save(speech4);

        speechService = new SpeechService(speechRepository, Mappers.getMapper(SpeechMapper.class));
    }

    @Test
    void search_contains() {
        var criteria = List.of(
                SearchCriteria.builder().key("author").operation(SearchOperation.CONTAINS).value("test").build()
        );
        var speechSearchRequest = SpeechSearchRequest.builder().criteriaList(criteria).pageSize(10).build();
        var result = speechService.search(speechSearchRequest);
        List<Speech> resultData = result.toList();
        assertEquals(4, resultData.size());
    }

    @Test
    void search_equals() {
        var criteria = List.of(
                SearchCriteria.builder().key("author").operation(SearchOperation.CONTAINS).value("test3").build()
        );
        var speechSearchRequest = SpeechSearchRequest.builder().criteriaList(criteria).pageSize(10).build();
        var result = speechService.search(speechSearchRequest);
        List<Speech> resultData = result.toList();
        assertEquals(1, resultData.size());
        assertEquals("test content3", resultData.get(0).getContent());
    }

    @Test
    void search_greater_than_or_equal_to_date() {
        var criteria = List.of(
                SearchCriteria.builder()
                        .key("date")
                        .operation(SearchOperation.GREATER_THAN_OR_EQUAL)
                        .value(LocalDate.now().toString()).build()
        );
        var speechSearchRequest = SpeechSearchRequest.builder().criteriaList(criteria).pageSize(10).build();
        var result = speechService.search(speechSearchRequest);
        List<Speech> resultData = result.toList();
        assertEquals(3, resultData.size());
        boolean isTest4Present = resultData.stream().anyMatch(speech -> speech.getAuthor().equals("test4"));
        assertFalse(isTest4Present);
    }

    @Test
    void search_less_than_or_equal_to_date() {
        var criteria = List.of(
                SearchCriteria.builder()
                        .key("date")
                        .operation(SearchOperation.LESS_THAN_OR_EQUAL)
                        .value(LocalDate.now().minusDays(1).toString()).build()
        );
        var speechSearchRequest = SpeechSearchRequest.builder().criteriaList(criteria).pageSize(10).build();
        var result = speechService.search(speechSearchRequest);
        List<Speech> resultData = result.toList();
        assertEquals(1, resultData.size());
        boolean isTest4Present = resultData.stream().anyMatch(speech -> speech.getAuthor().equals("test4"));
        assertTrue(isTest4Present);
    }

    @Test
    void search_with_multiple_criteria() {
        var criteria = List.of(
                SearchCriteria.builder().key("author").operation(SearchOperation.CONTAINS).value("test").build(),
                SearchCriteria.builder().key("date").operation(SearchOperation.GREATER_THAN_OR_EQUAL).value(LocalDate.now().toString()).build(),
                SearchCriteria.builder().key("keywords").operation(SearchOperation.CONTAINS).value("speech").build(),
                SearchCriteria.builder().key("content").operation(SearchOperation.CONTAINS).value("content").build()
        );
        var speechSearchRequest = SpeechSearchRequest.builder().criteriaList(criteria).pageSize(10).build();
        var result = speechService.search(speechSearchRequest);
        List<Speech> resultData = result.toList();
        assertEquals(2, resultData.size());
        assertEquals("test2", resultData.get(0).getAuthor());
        assertEquals("test3", resultData.get(1).getAuthor());
    }
}