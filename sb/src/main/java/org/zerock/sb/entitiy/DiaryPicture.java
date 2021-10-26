package org.zerock.sb.entitiy;

import lombok.*;

import javax.persistence.Embeddable;

@Embeddable
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode(of = "uuid")
public class DiaryPicture implements Comparable<DiaryPicture>{

    private String uuid;
    private String fileName;
    private String savePath;
    private int idx;

    @Override
    public int compareTo(DiaryPicture o) {
        return this.idx - o.idx ;
    }
}
