package org.zerock.sb.entitiy;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString(exclude = "board")
public class Reply {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long rno;

    private String replyText;

    private String replyer;

    @ManyToOne(fetch = FetchType.LAZY)
    private Board board;

    @CreationTimestamp
    private LocalDateTime replyDate;

    public void setText(String text){
        this.replyText = text;
    }


}
