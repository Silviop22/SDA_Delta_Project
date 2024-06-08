package com.ecommerce.ecommerce.services;

import com.ecommerce.ecommerce.dto.OrderDTO;
import com.ecommerce.ecommerce.dto.OrderResponse;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public interface OrderService {

    OrderDTO placeOrder (String emailId, Long cartId, String paymentMethod);

    OrderDTO getOder(String emailId, Long orderId);

    List<OrderDTO> getOrderByUser(String emailId);

    OrderResponse getAllOrders(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder);

    OrderDTO updateOrder(String emailId, Long orderId, String orderStatus);

}
