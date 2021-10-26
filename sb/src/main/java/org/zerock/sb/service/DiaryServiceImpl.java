package org.zerock.sb.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.zerock.sb.dto.*;
import org.zerock.sb.entitiy.Board;
import org.zerock.sb.entitiy.Diary;
import org.zerock.sb.repository.DiaryRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Log4j2
public class DiaryServiceImpl implements DiaryService{

    private final ModelMapper modelMapper;

    private final DiaryRepository diaryRepository;

    @Override
    public Long register(DiaryDTO dto) {

        Diary diary = modelMapper.map(dto, Diary.class);

        log.info(diary);
        log.info(diary.getTags());
        log.info(diary.getPictures());

        diaryRepository.save(diary);

        return diary.getDno();
    }

    @Override
    public DiaryDTO read(Long dno) {

        Optional<Diary> optionalDiary = diaryRepository.findById(dno);

        Diary diary = optionalDiary.orElseThrow();

        DiaryDTO dto = modelMapper.map(diary, DiaryDTO.class);

        return dto;
    }

    @Override
    public PageResponseDTO<DiaryDTO> getList(PageRequestDTO pageRequestDTO) {

        Pageable pageable = PageRequest.of(pageRequestDTO.getPage()-1, pageRequestDTO.getSize(), Sort.by("dno").descending());

        Page<Diary> result = diaryRepository.findAll(pageable);

        long totalCount = result.getTotalElements();

        List<DiaryDTO> dtoList = result.get().map(diary -> modelMapper.map(diary, DiaryDTO.class)).collect(Collectors.toList());

        return new PageResponseDTO<>(pageRequestDTO, (int)totalCount, dtoList);
    }

    @Override
    public PageResponseDTO<DiaryListDTO> getListWithFavorite(PageRequestDTO pageRequestDTO) {

        Pageable pageable = PageRequest.of(pageRequestDTO.getPage()-1, pageRequestDTO.getSize(), Sort.by("dno").descending());

        Page<Object[]> result = diaryRepository.findWithFavoriteCount(pageable);

        long totalCount = result.getTotalElements();

        List<DiaryListDTO> dtolist = result.get().map(objects -> {
         Object[] arr = (Object[]) objects;
         Diary diary = (Diary) arr[0];
         long totalScore = (long)arr[1];

         log.info("-----------------------------------");
         log.info(diary);
         log.info(totalScore);

         DiaryListDTO diaryListDTO = modelMapper.map(diary, DiaryListDTO.class);
         diaryListDTO.setTotalScore((int)totalScore);

         log.info(diaryListDTO);
         log.info("==========================");
         return diaryListDTO;

        }).collect(Collectors.toList());

        return new PageResponseDTO<>(pageRequestDTO, (int)totalCount, dtolist);
    }

    @Override
    public void modify(DiaryDTO diraryDTO) {
        Optional<Diary> result = diaryRepository.findById(diraryDTO.getDno());

        if(result.isEmpty()){
            throw new RuntimeException("Not Found");
        }
        Diary diary = result.get();
        diary.setTitle(diary.getTitle());
        diary.setContent(diary.getContent());
        diary.setTags(diary.getTags());
        diary.setPictures(diary.getPictures());

        diaryRepository.save(diary);
    }

    @Override
    public void remove(Long dno) { diaryRepository.deleteById(dno); }
}
