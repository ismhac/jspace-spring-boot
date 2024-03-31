package com.ismhac.jspace.repository;

import com.ismhac.jspace.model.File;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileRepository extends JpaRepository<File, Integer> {
}
