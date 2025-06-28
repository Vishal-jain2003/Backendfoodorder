package in.vishal.foodiesapi.util;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CloudinaryImage {
    private String imageUrl;
    private String publicId;
}
