package com.gerard.speech.mappers;

import com.gerard.speech.model.SpeechDto;
import com.gerard.speech.model.domain.Speech;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface SpeechMapper {
    SpeechDto toDto(Speech speech);

    Speech toEntity(SpeechDto speechDto);

    List<SpeechDto> toListDto(List<Speech> speeches);

    @Mapping(target = "id", ignore = true)
    void bind(SpeechDto speechDto, @MappingTarget Speech speech);
}
