package com.ismhac.jspace.repository;

import com.ismhac.jspace.model.OAuth2Info;
import com.ismhac.jspace.model.primaryKey.OAuth2InfoId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OAuth2InfoRepository extends JpaRepository<OAuth2Info, OAuth2InfoId> {
}
