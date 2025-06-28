package in.vishal.foodiesapi.service;

import in.vishal.foodiesapi.entity.CartEntity;
import in.vishal.foodiesapi.io.CartRequest;
import in.vishal.foodiesapi.io.CartResponse;
import in.vishal.foodiesapi.repository.CartRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;


@Service
@AllArgsConstructor
public class CartServiceImpl implements CartService{

    private final CartRepository cartRepository;
    private final UserService userService;
    @Override
    public CartResponse addToCart(CartRequest request) {
        String loggedInUserId = userService.findByUserId();
        Optional<CartEntity>cartOptional =  cartRepository.findByUserId(loggedInUserId);
      CartEntity cart =   cartOptional.orElseGet(()->new CartEntity(loggedInUserId,new HashMap<>()));
      Map<String,Integer> cartItems = cart.getItems();
      cartItems.put(request.getFoodId(),cartItems.getOrDefault(request.getFoodId(),0)+1);
      cart.setItems(cartItems);
      cart = cartRepository.save(cart);
        return convertToResponse(cart);



    }

    @Override
    public CartResponse getCart() {
        String loggedInUserId = userService.findByUserId();
        CartEntity entity = cartRepository.findByUserId(loggedInUserId)
                .orElse(new CartEntity(null,loggedInUserId,new HashMap<>()));
        return convertToResponse(entity);

    }

    @Override
    public void clearCart() {
        String loggedInUserId = userService.findByUserId();
        cartRepository.deleteByUserId(loggedInUserId);
    }

    @Override
    public CartResponse removeFromCart(CartRequest request) {
        String loggedInUserId = userService.findByUserId();
    CartEntity entity =   cartRepository.findByUserId(loggedInUserId)
              .orElseThrow(() -> new RuntimeException("Cart not found for user: " ));
        Map<String, Integer> items = entity.getItems();
        if (items.containsKey(request.getFoodId())) {
            int quantity = items.get(request.getFoodId());
            if (quantity > 1) {
                items.put(request.getFoodId(), quantity - 1);
            } else {
                items.remove(request.getFoodId());
            }
            entity = cartRepository.save(entity);

        } else {
            throw new RuntimeException("Item not found in cart");
        }
       return convertToResponse(entity);


    }

    private CartResponse convertToResponse(CartEntity cart){
       return CartResponse.builder()
                .id(cart.getId())
                .userId(cart.getUserId())
                .items(cart.getItems())
                .build();

    }
}
