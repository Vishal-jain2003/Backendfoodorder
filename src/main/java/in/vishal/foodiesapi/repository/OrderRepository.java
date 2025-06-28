package in.vishal.foodiesapi.repository;

import in.vishal.foodiesapi.entity.OrderEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.*;


public interface OrderRepository extends MongoRepository<OrderEntity, String> {

  List<OrderEntity> findByUserId(String userId);

  Optional<OrderEntity>findByRazorpayOrderId(String razorpayOrderId);

}
