package com.project.hiuni.domain.record.coverletter.repository;

import com.project.hiuni.domain.record.coverletter.entity.CoverLetter;
import com.project.hiuni.domain.user.entity.User;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface CoverLetterRepository extends JpaRepository<CoverLetter, Long> {

  List<CoverLetter> findAllByUser(User user);

  @Modifying
  @Query("delete from CoverLetter c where c.user = :user")
  void deleteAllByUser(User user);

}
