package in.vishal.foodiesapi.service;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import in.vishal.foodiesapi.entity.FoodEntity;
import in.vishal.foodiesapi.io.FoodRequest;
import in.vishal.foodiesapi.io.FoodResponse;
import in.vishal.foodiesapi.repository.FoodRepository;
import in.vishal.foodiesapi.util.CloudinaryImage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class FoodServiceImplementation implements FoodService {

    @Autowired
    private Cloudinary cloudinary;

    private final FoodRepository foodRepository;

    public FoodServiceImplementation(FoodRepository foodRepository) {
        this.foodRepository = foodRepository;
    }

    @Override
    public CloudinaryImage uploadImage(MultipartFile file) throws IOException {
        Map uploadResult = cloudinary.uploader().upload(file.getBytes(), ObjectUtils.emptyMap());
        String imageUrl = uploadResult.get("secure_url").toString();
        String publicId = uploadResult.get("public_id").toString();
        return new CloudinaryImage(imageUrl, publicId);
    }

    @Override
    public FoodResponse addFood(FoodRequest request, MultipartFile file) {
        try {
            FoodEntity newFoodEntity = convertToEntity(request);
//            String imageUrl = uploadImage(file); // 👈 Must handle IOException
//            newFoodEntity.setImageUrl(imageUrl);
            CloudinaryImage cloudinaryImage = uploadImage(file);
            newFoodEntity.setImageUrl(cloudinaryImage.getImageUrl());
            newFoodEntity.setPublicId(cloudinaryImage.getPublicId());

            newFoodEntity = foodRepository.save(newFoodEntity);
            return convertToResponse(newFoodEntity);
        } catch (IOException e) {
            throw new RuntimeException("Failed to upload image", e);
        }

    }

    @Override
    public List<FoodResponse> readFoods() {
       List<FoodEntity> databaseEntries =  foodRepository.findAll();
         return databaseEntries.stream()
                 .map(object->convertToResponse(object)).collect(Collectors.toList());


    }



    @Override
    public FoodResponse readFood(String id) {
       FoodEntity existingfood =  foodRepository.findById(id).orElseThrow(()-> new RuntimeException("Food not found with id: " + id));
       return convertToResponse(existingfood);
    }

    @Override
    public boolean deleteFile(String publicId) {
        try {
            Map result = cloudinary.uploader().destroy(publicId, ObjectUtils.emptyMap());
            return "ok".equals(result.get("result"));
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }


    @Override
    public void deleteFood(String id) {
        FoodEntity food = foodRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Food not found with id: " + id));

        boolean deleted = deleteFile(food.getPublicId());
        if (!deleted) {
            throw new RuntimeException("Failed to delete image from Cloudinary.");
        }

        foodRepository.deleteById(id);
    }


    private FoodResponse convertToResponse(FoodEntity entity) {
        return FoodResponse.builder()
                .id(entity.getId())
                .name(entity.getName())
                .description(entity.getDescription())
                .category(entity.getCategory())
                .price(entity.getPrice())
                .imageUrl(entity.getImageUrl())
                .publicId(entity.getPublicId())

                .build();
    }

    private FoodEntity convertToEntity(FoodRequest request) {
       return  FoodEntity.builder()
                .name(request.getName())
                .description(request.getDescription())
                .category(request.getCategory())
                .price(request.getPrice())
                .build();

    }


}
