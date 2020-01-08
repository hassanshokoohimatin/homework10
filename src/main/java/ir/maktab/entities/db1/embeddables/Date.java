package ir.maktab.entities.db1.embeddables;


import lombok.*;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor

@Embeddable

public class Date {

    @Column
    private String createDate;

    @Column
    private String lastUpdateDate;

    @Column
    private String publishDate;
}
