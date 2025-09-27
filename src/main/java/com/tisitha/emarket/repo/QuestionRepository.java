package com.tisitha.emarket.repo;

import com.tisitha.emarket.model.Question;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface QuestionRepository extends JpaRepository<Question,Long> {

    Page<Question> findAllByProductIdAndAnswerIsNotNull(UUID productId, Pageable pageable);

    Page<Question> findAllByProductIdAndAnswerIsNull(UUID productId, Pageable pageable);

    Page<Question> findAllByProductVendorProfileEmailAndAnswerIsNull(String email, Pageable pageable);

    Optional<Question> findByIdAndProductVendorProfileEmail(Long questionId, String email);
}
