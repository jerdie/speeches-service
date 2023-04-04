package com.gerard.speech.repository;

import com.gerard.speech.model.domain.Speech;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface SpeechRepository extends JpaRepository<Speech, Long>, JpaSpecificationExecutor<Speech> {

}
