package org.zerock.sb.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BoardListDTO {

    private Long bno;
    private String title;
    private String writer;
    private LocalDateTime regDate;
    private long replyCount;
    //기본 자료형은 0이라는 값을 갖기때문에..
}
