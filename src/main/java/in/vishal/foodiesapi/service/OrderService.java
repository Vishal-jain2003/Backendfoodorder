package in.vishal.foodiesapi.service;

import in.vishal.foodiesapi.io.OrderRequest;
import in.vishal.foodiesapi.io.OrderResponse;
import java.util.*;

public interface OrderService {

    OrderResponse createOrderWithPayment(OrderRequest request) throws Exception;

    void verifyPayment(Map<String,String>paymentData,String status);

    // order for logged in user
    List<OrderResponse> getUserOrders();

    void removeOrder(String orderId);

    List<OrderResponse> getOrdersOfAllUsers();

   void  updateOrderStatus(String orderId, String status);




}
