package com.gdsc.backend.service;

import com.gdsc.backend.entity.Diary;
import com.gdsc.backend.http.request.DiaryRequest;
import com.gdsc.backend.http.response.DiaryContentResponse;
import com.gdsc.backend.repository.DiaryRepository;
import com.gdsc.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class DiaryService {
    private final UserRepository userRepository;
    private final DiaryRepository diaryRepository;

    @Autowired
    public DiaryService(UserRepository userRepository, DiaryRepository diaryRepository) {
        this.userRepository = userRepository;
        this.diaryRepository=diaryRepository; }

    public List<DiaryContentResponse> findDiariesContent(Integer year, Integer month) {
        UUID userId = UUID.fromString(SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString());
        LocalDate date = LocalDate.of(year, month, 1);
        List<Diary> test = diaryRepository.findDiariesByUsersAndDateBetween(userRepository.getById(userId), date, date.withDayOfMonth(date.lengthOfMonth()));
        return test.stream().map(this::makeResponse).collect(Collectors.toList());
    }

    @Transactional
    public Diary findDiary(UUID id){
        return diaryRepository.getById(id);
    }

    public Diary save(DiaryRequest diaryRequest) {
        return diaryRepository.save(
                Diary.builder()
                        .title(diaryRequest.getTitle())
                        .emotion(diaryRequest.getEmotion())
                        .content(diaryRequest.getContent())
                        .build());
    }

    public void deleteById(UUID idx) {
        diaryRepository.deleteById(idx);
    }

    private DiaryContentResponse makeResponse(Diary diary) {
        return DiaryContentResponse.of(diary);
    }

}
