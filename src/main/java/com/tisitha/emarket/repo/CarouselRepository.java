package com.tisitha.emarket.repo;

import com.tisitha.emarket.model.Carousel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CarouselRepository extends JpaRepository<Carousel,Long> {
}
