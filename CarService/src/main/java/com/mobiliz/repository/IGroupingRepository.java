package com.mobiliz.repository;

import com.mobiliz.repository.entity.Grouping;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IGroupingRepository extends MongoRepository<Grouping,String> {

}
