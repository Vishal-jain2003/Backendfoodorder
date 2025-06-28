package in.vishal.foodiesapi.service;

import in.vishal.foodiesapi.io.FoodRequest;
import in.vishal.foodiesapi.io.FoodResponse;
import in.vishal.foodiesapi.util.CloudinaryImage;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface FoodService {

    CloudinaryImage uploadImage(MultipartFile file) throws IOException;

    FoodResponse addFood(FoodRequest request, MultipartFile file) ;

    List<FoodResponse> readFoods();

    FoodResponse readFood(String id);

    boolean deleteFile(String publicId);

    void deleteFood(String id);
}
