package org.zerock.sb.entitiy;

import lombok.*;
import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name="tbl_diary")
@Builder
@Getter
@ToString(exclude = {"tags", "pictures"})
@AllArgsConstructor
@NoArgsConstructor
public class Diary {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long dno;

    private String title;

    private String content;

    private String writer;

    @UpdateTimestamp
    private LocalDateTime modDate;

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name="tbl_diary_tag")
    @Fetch(value = FetchMode.JOIN)
    @BatchSize(size = 50)
    private Set<String> tags;

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name="tbl_diary_picture")
    @Fetch(value = FetchMode.JOIN)
    @BatchSize(size = 50)
    private Set<DiaryPicture> pictures;

    public void setTitle(String title) {
        this.title = title;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setTags(Set<String> tags) {
        this.tags = tags;
    }

    public void setPictures(Set<DiaryPicture> pictures) {
        this.pictures = pictures;
    }
}
