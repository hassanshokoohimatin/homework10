package ir.maktab.entities.db1;


import ir.maktab.entities.db1.embeddables.About;
import ir.maktab.entities.db1.embeddables.Date;
import ir.maktab.entities.db1.enums.IsPublished;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table

public class Article {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;

    @Embedded
    private About about;

    @Embedded
    private Date date;

    @Column
    @Enumerated(EnumType.STRING)
    private IsPublished isPublished;

    @ManyToOne
    private User user;

    @ManyToOne
    private Category category;


    @ManyToMany
    @JoinTable(name = "article_tag" , joinColumns = {@JoinColumn(name = "article_id")} , inverseJoinColumns = {@JoinColumn(name = "tag_id")})
    private List<Tag> tags = new ArrayList<>();

    @Override
    public String toString() {
        System.out.print("Article{"+ id +'}' + "tags : ");
        if (tags.size()!=0) {
            for (Tag t : tags) {
                System.out.printf("%s\t\t", t.getTitle());
            }
        }else
            System.out.print("no tags");
        return
                ", title : " + about.getTitle() +
                        ", content : " + about.getContent() +
                        ", createDate : " + date.getCreateDate() +
                        ", publishDate : " + date.getPublishDate() +
                        ", isPublished : " + isPublished +
                        ", user=" + user.getUsername() +
                        ", category=" + category.getTitle() +
                        '}';
    }
}
