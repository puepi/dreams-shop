package com.dailycodework.dreamshops.service.order;

import com.dailycodework.dreamshops.dto.OrderDto;
import com.dailycodework.dreamshops.exceptions.ResourceNotFoundException;
import com.dailycodework.dreamshops.model.Cart;
import com.dailycodework.dreamshops.model.Orders;
import com.dailycodework.dreamshops.model.OrderItem;
import com.dailycodework.dreamshops.model.Product;
import com.dailycodework.dreamshops.repository.OrderRepository;
import com.dailycodework.dreamshops.repository.ProductRepository;
import com.dailycodework.dreamshops.service.cart.CartService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService implements IOrderService{

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final CartService cartService;
    private final ModelMapper modelMapper;

    @Override
    public Orders placeOrder(Long userId) {
        Cart cart=cartService.getCartByUserId(userId);
        Orders order=createOrder(cart);
        Set<OrderItem> orderItemList=createOrderItems(order,cart);
        order.setOrderItems(orderItemList);
        order.setTotalAmount(calculateTotalAmount(orderItemList));
        Orders savedOrder=orderRepository.save(order);
        cartService.clearCart(cart.getId());
        return savedOrder;
    }

    @Override
    public OrderDto getOrder(Long orderId) {
        Orders order= orderRepository.findById(orderId)
                .orElseThrow(()->new ResourceNotFoundException("Order not found"));
        return convertToDto(order);
    }


    private BigDecimal calculateTotalAmount(Set<OrderItem> orderItemList){
        return orderItemList.stream()
                .map(item-> item.getPrice()
                        .multiply(new BigDecimal(item.getQty())))
                .reduce(BigDecimal.ZERO,BigDecimal::add);
    }

    private Orders createOrder(Cart cart){
        Orders order=new Orders();
        order.setUser(cart.getUser());
        order.setOrderDate(LocalDateTime.now());
        return orderRepository.save(order);
    }


    private Set<OrderItem> createOrderItems(Orders order, Cart cart){
//        Cart cart=cartService.getCart(cartId);
//        Order order=getOrder(orderId);
//        order.setOrderItems(cart.getCartItems().stream()
//                .map(cartItem->
//                        new OrderItem(cartItem.getProduct(),cartItem.getTotalPrice(),cartItem.getQty())).collect(Collectors.toSet()));
//        return order.getOrderItems();
        return cart.getCartItems().stream()
                .map(cartItem -> {
                    Product product=cartItem.getProduct();
                    product.setInventory(product.getInventory() - cartItem.getQty());
                    productRepository.save(product);
                    return new OrderItem(
                            order,
                            product,
                            cartItem.getTotalPrice(),
                            cartItem.getQty()
                    );
                }).collect(Collectors.toSet());
    }

     @Override
     public List<OrderDto> getUserOrders(Long userId) {
         return orderRepository.findAllByUserId(userId)
                 .stream().map(this::convertToDto).toList();
    }

    private OrderDto convertToDto(Orders order){
        return modelMapper.map(order,OrderDto.class);
    }

}
