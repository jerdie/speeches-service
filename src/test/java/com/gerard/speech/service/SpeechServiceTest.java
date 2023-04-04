package com.gerard.speech.service;

import com.gerard.speech.mappers.SpeechMapper;
import com.gerard.speech.model.SpeechDto;
import com.gerard.speech.model.domain.Speech;
import com.gerard.speech.repository.SpeechRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SpeechServiceTest {

    SpeechService speechService;

    @Mock
    SpeechRepository speechRepository;

    @BeforeEach
    void setup() {
        speechService = new SpeechService(speechRepository, Mappers.getMapper(SpeechMapper.class));
    }

    @Test
    void add() {
        SpeechDto speechDto = SpeechDto.builder().author("Test").build();
        doAnswer((invocation -> {
            Speech speech = invocation.getArgument(0);
            speech.setId(1L);
            return speech;
        })).when(speechRepository).save(any(Speech.class));

        speechDto = speechService.add(speechDto);

        assertEquals(1L, speechDto.getId());
        assertEquals("Test", speechDto.getAuthor());
    }

    @Test
    void update() {
        SpeechDto speechDto = SpeechDto.builder()
                .author("test updated")
                .keywords("test keywords updated")
                .content("content updated")
                .date(LocalDate.now().plusDays(1)).build();
        Speech speech = Speech.builder()
                .author("test")
                .keywords("test keywords")
                .content("content")
                .date(LocalDate.now()).build();
        when(speechRepository.findById(1L)).thenReturn(Optional.of(speech));
        doAnswer(invocation -> invocation.getArgument(0)).when(speechRepository).save(speech);

        SpeechDto result = speechService.update(1L, speechDto);

        assertEquals(speechDto.getAuthor(), result.getAuthor());
        assertEquals(speechDto.getKeywords(), result.getKeywords());
        assertEquals(speechDto.getContent(), result.getContent());
        assertEquals(speechDto.getDate(), result.getDate());
    }

    @Test
    void delete(){
        speechService.delete(1L);
        verify(speechRepository, times(1)).deleteById(anyLong());
    }

}