package com.mobiliz.repository;

import com.mobiliz.repository.entity.CarAuth;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ICarAuthRepository extends JpaRepository<CarAuth,Long> {

}
