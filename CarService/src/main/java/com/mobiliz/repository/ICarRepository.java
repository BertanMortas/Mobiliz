package com.mobiliz.repository;

import com.mobiliz.repository.entity.Car;
import com.mobiliz.repository.entity.view.CarFindAllView;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ICarRepository extends MongoRepository<Car,String> {

    // todo id is for development delete after
    @Query(value = "{'status': 'ACTIVE'}", fields = "{'_id':1, 'plate': 1, 'tag': 1, 'companyId': 1}")
    List<CarFindAllView> findActiveCarFields();
}
