package in.vishal.foodiesapi.repository;

import in.vishal.foodiesapi.entity.FoodEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FoodRepository extends MongoRepository <FoodEntity, String> {


}
