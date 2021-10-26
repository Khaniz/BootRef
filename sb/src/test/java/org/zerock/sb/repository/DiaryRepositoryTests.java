package org.zerock.sb.repository;

import com.google.common.collect.Sets;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;
import org.zerock.sb.dto.DiaryDTO;
import org.zerock.sb.entitiy.Diary;
import org.zerock.sb.entitiy.DiaryPicture;
import org.zerock.sb.service.DiaryService;

import java.util.Arrays;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@SpringBootTest
@Log4j2
public class DiaryRepositoryTests {

    @Autowired
    DiaryRepository diaryRepository;

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    DiaryService diaryService;

    @Test
    public void testInsert(){

        IntStream.rangeClosed(1,100).forEach(i -> {

            Set<String> tags = IntStream.rangeClosed(1,3).mapToObj(j -> i+"_tag_"+j).collect(Collectors.toSet());

            Set<DiaryPicture> pictures = IntStream.rangeClosed(1,3).mapToObj(j -> {
                DiaryPicture picture = DiaryPicture.builder()
                        .uuid(UUID.randomUUID().toString())
                        .savePath("2021/10/18")
                        .fileName("Img"+j+".jpg")
                        .idx(j)
                        .build();

                return picture;
            }).collect(Collectors.toSet());

            Diary diary = Diary.builder()
                    .title("sample"+i)
                    .content("samp"+i)
                    .writer("user00")
                    .tags(tags)
                    .pictures(pictures)
                    .build();

            diaryRepository.save(diary);
        });

    }

    @Test
    public void testSelectOne(){
        Long dno = 1L;

        Optional<Diary> optionalDiary = diaryRepository.findById(dno);

        Diary diary = optionalDiary.orElseThrow();

        log.info(diary);
//        log.info(diary); 기본적으로 레이지 로딩이다. 따라서 발생하는 문제 해결법 정답은 없다. 상황에 맞게 맞는 방식을 사용하는것
        //1. 연관관계 제외 시키기
        //2. 트랜잭션 걸어주기 DB와 연결 여러번 맺는것
        //3. Eager 로딩

        log.info(diary.getTags());
        log.info(diary.getPictures());
    }

    @Transactional
    @Test
    public void testPaging1(){
        Pageable pageable = PageRequest.of(0,10, Sort.by("dno").descending());

        Page<Diary> result = diaryRepository.findAll(pageable);

        result.get().forEach(diary -> {
            log.info(diary);
            log.info(diary.getTags());
            log.info(diary.getPictures());
            log.info("--------------------------");
        });
    }

    @Test
    public void testSelectOne2(){
        Long dno = 1L;

        Optional<Diary> optionalDiary = diaryRepository.findById(dno);

        Diary diary = optionalDiary.orElseThrow();

        DiaryDTO dto = modelMapper.map(diary, DiaryDTO.class);

        log.info(dto);
    }

    @Transactional(readOnly = true)
    @Test
    public void testRead(){

       Long dno = 99L;

        DiaryDTO dto = diaryService.read(dno);

        log.info(dto);

        log.info(dto.getPictures().size());

        dto.getPictures().forEach(diaryPictureDTO -> log.info(diaryPictureDTO));
    }

    @Test
    public void testSearchTag(){
        String tag = "1";
        Pageable pageable = PageRequest.of(0,10,Sort.by("dno").descending());

        Page<Diary> result = diaryRepository.searchTags(tag,pageable);

        result.get().forEach(diary -> {
            log.info(diary);
            log.info(diary.getTags());
            log.info(diary.getPictures());
            log.info("-------------------");
        });
    }

    @Test
    public void testDelete(){
        Long dno = 103L;

        diaryRepository.deleteById(dno);
    }

    @Commit
    @Transactional
    @Test
    public void testUpdate(){

        Set<String> updateTags = Sets.newHashSet("aaa","bbb","ccc");

        Set<DiaryPicture> updatePictures
                =IntStream.rangeClosed(10,15).mapToObj(i->{
                    DiaryPicture picture =
                            DiaryPicture.builder()
                                    .uuid(UUID.randomUUID().toString())
                                    .savePath("2021/10/19")
                                    .fileName("TEST"+i+".jpg")
                                    .idx(i)
                                    .build();

                    return picture;
        }).collect(Collectors.toSet());

        Optional<Diary> optionalDiary = diaryRepository.findById(102L);

        Diary diary = optionalDiary.orElseThrow();

        diary.setTitle("Upodated title 102");
        diary.setContent("Upodated contents 102");
        diary.setTags(updateTags);
        diary.setPictures(updatePictures);

        diaryRepository.save(diary);

    }

    @Test
    public void testWithFavorite(){

        Pageable pageable = PageRequest.of(0,10, Sort.by("dno").descending());

        Page<Object[]> result = diaryRepository.findWithFavoriteCount(pageable);

        for(Object[] objects : result.getContent()){
            log.info(Arrays.toString(objects));
        }

    }

//    @Test
//    public void testListHard(){
//        Pageable pageable = PageRequest.of(0,10, Sort.by("dno").descending());
//
//        diaryRepository.getSearchList(pageable);
//    }




}
