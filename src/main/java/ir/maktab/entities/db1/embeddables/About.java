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

public class About {

    @Column
    private String title;

    @Column
    private String brief;

    @Column
    private String content;

}
