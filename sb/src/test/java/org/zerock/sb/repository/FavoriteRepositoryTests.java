package org.zerock.sb.repository;

import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.zerock.sb.entitiy.Diary;
import org.zerock.sb.entitiy.Favorite;
import org.zerock.sb.entitiy.Member;

import java.util.stream.IntStream;

@SpringBootTest
@Log4j2
public class FavoriteRepositoryTests {

    @Autowired
    FavoriteRepository repository;

    @Test
    public void insertFavorite(){

        IntStream.rangeClosed(1,100).forEach(i->{

            Long dno = (long)(100 + (i % 3));
            String mid = "user"+i;

            Diary diary = Diary.builder().dno(dno).build();

            Member member = Member.builder().mid(mid).build();

            Favorite favorite = Favorite.builder()
                    .member(member).diary(diary).score(1).build();

            repository.save(favorite);
        });

    }

}
