package org.zerock.sb.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.zerock.sb.entitiy.Reply;

import java.util.List;

public interface ReplyRepository extends JpaRepository<Reply, Long> {

    List<Reply> findReplyByBoard_BnoOrderByRno(Long bno);

    @Query("select r from Reply r where r.board.bno = :bno")
    Page<Reply> getListByBno(Long bno, Pageable pageable); // 파라미터가 페이지어블이면 리턴타입은 항상 페이지라는거!

    @Query("select count(r) from Reply r where r.board.bno= :bno")
    int getReplyCountOfBoard(Long bno);

}
