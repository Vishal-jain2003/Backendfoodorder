package in.vishal.foodiesapi.service;

import in.vishal.foodiesapi.io.CartRequest;
import in.vishal.foodiesapi.io.CartResponse;

public interface CartService {

    CartResponse addToCart(CartRequest request);

    CartResponse getCart();

    void clearCart();

    CartResponse removeFromCart(CartRequest request) ;
}
