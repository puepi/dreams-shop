package com.dailycodework.dreamshops.service.order;

import com.dailycodework.dreamshops.exceptions.ResourceNotFoundException;
import com.dailycodework.dreamshops.model.Cart;
import com.dailycodework.dreamshops.model.Order;
import com.dailycodework.dreamshops.model.OrderItem;
import com.dailycodework.dreamshops.model.Product;
import com.dailycodework.dreamshops.repository.OrderRepository;
import com.dailycodework.dreamshops.repository.ProductRepository;
import com.dailycodework.dreamshops.service.cart.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService implements IOrderService{

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final CartService cartService;

    @Override
    public Order placeOrder(Long userId) {
        Cart cart=cartService.getCartByUserId(userId);
        Order order=createOrder(cart);
        Set<OrderItem> orderItemList=createOrderItems(order,cart);
        order.setOrderItems(orderItemList);
        order.setTotalAmount(calculateTotalAmount(orderItemList));
        Order savedOrder=orderRepository.save(order);
        cartService.clearCart(cart.getId());
        return savedOrder;
    }

    @Override
    public Order getOrder(Long orderId) {
        return orderRepository.findById(orderId)
                .orElseThrow(()->new ResourceNotFoundException("Order not found")   );
    }


    private BigDecimal calculateTotalAmount(Set<OrderItem> orderItemList){
        return orderItemList.stream()
                .map(item-> item.getPrice()
                        .multiply(new BigDecimal(item.getQty())))
                .reduce(BigDecimal.ZERO,BigDecimal::add);
    }

    private Order createOrder(Cart cart){
        Order order=new Order();
        order.setUser(cart.getUser());
        order.setOrderDate(LocalDate.now());
        return orderRepository.save(order);
    }


    private Set<OrderItem> createOrderItems(Order order, Cart cart){
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
     public List<Order> getUserOrders(Long userId){
        return orderRepository.findAllByUserId(userId);
    }
}
