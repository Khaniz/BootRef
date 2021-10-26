package org.zerock.sb.repository;

import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.zerock.sb.entitiy.Board;
import org.zerock.sb.entitiy.Reply;

import java.util.List;
import java.util.stream.IntStream;

@SpringBootTest
@Log4j2
public class ReplyRepositoryTests {

    @Autowired
    private ReplyRepository replyRepository;

    @Test
    public void insert200(){

        IntStream.rangeClosed(1,200).forEach(i -> {
            Long bno = (long)(200 - ( i % 5)); // 200, 199

            int replyCount = (i % 5); // 0, 1, 2, 3, 4

            IntStream.rangeClosed(0, replyCount).forEach(j -> {

                Board board = Board.builder().bno(bno).build();

                Reply reply = Reply.builder()
                        .replyText("Reply.....")
                        .replyer("replyer..")
                        .board(board)
                        .build();

                replyRepository.save(reply);

            });

        });
    }

    @Test
    public void testRead(){
        Long rn = 1L;

        Reply reply = replyRepository.findById(rn).get();

        log.info(reply);
    }

    @Test
    public void testByBno(){
        Long bno = 200L;

        List<Reply> replyList
                = replyRepository.findReplyByBoard_BnoOrderByRno(bno);

        replyList.forEach(reply -> log.info(reply));
    }

    @Test
    public void testListOfBoard() {

        Pageable pageable = PageRequest.of(0,10, Sort.by("rno").descending());

        Page<Reply> result = replyRepository.getListByBno(197L, pageable);

        log.info(result.getTotalElements());

        result.get().forEach(reply -> log.info(reply));

    }

    @Test
    public void testCountOfBoard(){
        Long bno = 195L;

        //120
        int count = replyRepository.getReplyCountOfBoard(bno);

        int lastPage = (int)(Math.ceil(count/10.0));

        // 121/10.0 => 12 * 10 limit 110, 120
//        int lastPageNum = (int)(Math.ceil(count / (double)10));
//
//        int lastEnd = lastPageNum * 10;
//        int lastStart = lastEnd - 10;

        //0부터 시작하는 페이지번호, 사이즈, 소트
        Pageable pageable = PageRequest.of(lastPage <= 0? 0 : lastPage -1, 10);

        Page<Reply> result = replyRepository.getListByBno(bno, pageable);

        log.info("total:"+ result.getTotalElements());
        log.info("..." + result.getTotalPages());

        result.get().forEach(reply -> {
            log.info(reply);
        });
    }

}
