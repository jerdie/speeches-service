package com.gerard.speech.service;

import com.gerard.speech.mappers.SpeechMapper;
import com.gerard.speech.model.SpeechDto;
import com.gerard.speech.model.search.SpeechSearchRequest;
import com.gerard.speech.model.domain.Speech;
import com.gerard.speech.repository.SpeechRepository;
import com.gerard.speech.repository.search.SpeechSpecificationBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SpeechService {

    private final SpeechRepository speechRepository;

    private final SpeechMapper speechMapper;

    public List<SpeechDto> list() {
        return speechMapper.toListDto(speechRepository.findAll());
    }

    public SpeechDto get(Long id) {
        return speechMapper.toDto(speechRepository.findById(id).orElseThrow());
    }


    public SpeechDto add(SpeechDto speechDto) {
        Speech speech = speechMapper.toEntity(speechDto);
        speech = speechRepository.save(speech);
        return speechMapper.toDto(speech);
    }

    public SpeechDto update(Long id, SpeechDto speechDto) {
        Speech speech = speechRepository.findById(id).orElseThrow();
        speechMapper.bind(speechDto, speech);
        return speechMapper.toDto(speechRepository.save(speech));
    }

    public void delete(Long id){
        speechRepository.deleteById(id);
    }

    public Page<Speech> search(SpeechSearchRequest searchRequest){
        SpeechSpecificationBuilder specificationBuilder = new SpeechSpecificationBuilder(searchRequest.getCriteriaList());
        Pageable page = PageRequest.of(searchRequest.getPageNumber(), searchRequest.getPageSize());
        return speechRepository.findAll(specificationBuilder.build(), page);
    }
}
