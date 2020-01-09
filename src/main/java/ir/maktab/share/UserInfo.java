package ir.maktab.share;


import ir.maktab.entities.db1.enums.RoleType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserInfo {

    private Long id;

    private String firstName;

    private String lastName;

    private String fullName = firstName+" " +lastName;

    @Enumerated(EnumType.STRING)
    private RoleType roleType;

    private String city;

    public UserInfo(String fullName) {
        this.fullName = fullName;
    }
}
