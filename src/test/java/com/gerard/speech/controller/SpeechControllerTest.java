package com.gerard.speech.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gerard.speech.model.SpeechDto;
import com.gerard.speech.service.SpeechService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureRestDocs
@WebMvcTest(value = SpeechController.class)
@ExtendWith(RestDocumentationExtension.class)
class SpeechControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    SpeechService speechService;

    @Test
    void add() throws Exception {
        // Replace with restdocs
        SpeechDto speechDto = getSpeechData();
        SpeechDto speechDtoWithId = getSpeechData();
        speechDtoWithId.setId(10L);
        when(speechService.add(any(SpeechDto.class)))
                .thenReturn(speechDtoWithId);
        String json = objectMapper.writeValueAsString(speechDto);

        mockMvc.perform(RestDocumentationRequestBuilders.post("/api/speeches")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(jsonPath("$.id").value(10))
                .andExpect(status().isCreated());
    }

    @Test
    void list() throws Exception {
        SpeechDto speechDto1 = SpeechDto.builder().build();
        SpeechDto speechDto2 = SpeechDto.builder().build();
        when(speechService.list()).thenReturn(List.of(speechDto1, speechDto2));

        mockMvc.perform(RestDocumentationRequestBuilders.get("/api/speeches"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));
    }

    @Test
    void get() throws Exception {
        SpeechDto speechDto1 = SpeechDto.builder().id(1L)
                .content("Lengthy content")
                .author("Test author")
                .keywords("test keywords in content")
                .date(LocalDate.now())
                .build();
        when(speechService.get(anyLong())).thenReturn(speechDto1);

        mockMvc.perform(RestDocumentationRequestBuilders.get("/api/speeches/{id}", 1L))
                .andExpect(status().isOk())
                .andDo(document("api/speeches",
                        pathParameters(
                                parameterWithName("id").description("id field of speech")
                        ),
                        responseFields(
                                fieldWithPath("id").type("Long").description("Speech id"),
                                fieldWithPath("content").type("String").description("Speech content"),
                                fieldWithPath("author").type("String").description("Author"),
                                fieldWithPath("keywords").type("String").description("Subject area keywords"),
                                fieldWithPath("date").type("LocalDate").description("Speech date")
                        )));
    }

    @Test
    void update() throws Exception {
        SpeechDto speechDto = getSpeechData();
        SpeechDto speechDtoWithId = getSpeechData();
        speechDtoWithId.setId(10L);
        when(speechService.update(anyLong(), any(SpeechDto.class)))
                .thenReturn(speechDtoWithId);
        String json = objectMapper.writeValueAsString(speechDto);

        mockMvc.perform(RestDocumentationRequestBuilders.put("/api/speeches/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(jsonPath("$.id").value(10))
                .andExpect(status().isOk());
    }

    SpeechDto getSpeechData() {
        return SpeechDto.builder()
                .content("I could not have blamed you for being the first to lose heart if I, your commander, had not shared in your exhausting marches and your perilous campaigns; it would have been natural enough if you had done all the work merely for others to reap the reward. But it is not so. You and I, gentlemen, have shared the labour and shared the danger, and the rewards are for us all. The conquered territory belongs to you; from your ranks the governors of it are chosen; already the greater part of its treasure passes into your hands, and when all Asia is overrun, then indeed I will go further than the mere satisfaction of our ambitions: the utmost hopes of riches or power which each one of you cherishes will be far surpassed, and whoever wishes to return home will be allowed to go, either with me or without me. I will make those who stay the envy of those who return.")
                .author("Alexander")
                .keywords("commander ambitions perilous campaigns")
                .date(LocalDate.now())
                .build();
    }
}