package com.gerard.speech.controller;

import com.gerard.speech.model.SpeechDto;
import com.gerard.speech.model.search.SearchResponse;
import com.gerard.speech.model.search.SpeechSearchRequest;
import com.gerard.speech.service.SpeechService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/speeches")
public class SpeechController {

    private final SpeechService speechService;

    @GetMapping
    public List<SpeechDto> list() {
        return speechService.list();
    }

    @GetMapping("{id}")
    public SpeechDto get(@PathVariable Long id) {
        return speechService.get(id);
    }

    @PostMapping
    public ResponseEntity<SpeechDto> add(@RequestBody SpeechDto speechDto) {
        return new ResponseEntity<>(speechService.add(speechDto), HttpStatus.CREATED);
    }

    @PutMapping("{id}")
    public SpeechDto update(@PathVariable Long id, @RequestBody SpeechDto speechDto) {
        return speechService.update(id, speechDto);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        speechService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("search")
    public ResponseEntity<SearchResponse> search(@RequestBody SpeechSearchRequest searchDto) {
        SearchResponse searchResponse = new SearchResponse(speechService.search(searchDto).toList());
        return new ResponseEntity<>(searchResponse, HttpStatus.OK);
    }
}
