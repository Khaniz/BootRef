package org.zerock.sb.repository.search;

import com.querydsl.jpa.JPQLQuery;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.zerock.sb.entitiy.Diary;
import org.zerock.sb.entitiy.QDiary;
import org.zerock.sb.entitiy.QDiaryPicture;
import org.zerock.sb.entitiy.QFavorite;

@Log4j2
public class DiarySearchImpl extends QuerydslRepositorySupport implements DiarySearch{

    public DiarySearchImpl(){
        super(Diary.class);
    }

    @Override
    public Page<Object[]> getSearchList(Pageable pageable) {

        log.info("getSearchList...............");

        QDiary qDiary = QDiary.diary; // Entity만 생성할수 있음
        QFavorite qFavorite = QFavorite.favorite;
        QDiaryPicture qDiaryPicture = new QDiaryPicture("picture");

        JPQLQuery<Diary> query = from(qDiary);

        query.leftJoin(qFavorite).on(qFavorite.diary.eq(qDiary));
        query.leftJoin(qDiary.pictures, qDiaryPicture);
        query.groupBy(qDiary);

        query.select(qDiary.dno, qDiary.title, qDiaryPicture, qFavorite.score.sum());

        getQuerydsl().applyPagination(pageable, query);

        log.info(query);

        query.fetch();

        return null;
    }
}
