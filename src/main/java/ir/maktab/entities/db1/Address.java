package ir.maktab.entities.db1;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table

public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;

    @Column
    private String state;
    @Column
    private String city;
    @Column
    private String street;
    @Column
    private String alley;
    @Column
    private String postalCode;

    @OneToOne
    private User user;

}
