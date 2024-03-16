package com.ismhac.jspace.model;

import com.ismhac.jspace.model.primaryKey.OAuth2InfoId;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tbl_oauth2_info")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OAuth2Info {
    @EmbeddedId
    OAuth2InfoId id;

    String name;
    String email;
    String picture;
}
