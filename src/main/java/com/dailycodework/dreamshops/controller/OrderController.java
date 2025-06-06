package com.dailycodework.dreamshops.controller;

import com.dailycodework.dreamshops.dto.OrderDto;
import com.dailycodework.dreamshops.exceptions.AlreadyExistsException;
import com.dailycodework.dreamshops.exceptions.ResourceNotFoundException;
import com.dailycodework.dreamshops.model.Orders;
import com.dailycodework.dreamshops.response.ApiResponse;
import com.dailycodework.dreamshops.service.order.IOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("${api.prefix}/orders")
public class OrderController {

    private final IOrderService orderService;

    @PostMapping()
    public ResponseEntity<ApiResponse> createOrder(@RequestParam Long userId){
        try {
            Orders order=orderService.placeOrder(userId);
            OrderDto dto=orderService.convertToDto(order);
            return ResponseEntity.ok(new ApiResponse("Order Success",dto));
        } catch (ResourceNotFoundException | AlreadyExistsException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage(),null));
        } catch(Exception e){
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<ApiResponse> getOrderById(@PathVariable Long orderId){
        try{
            OrderDto order=orderService.getOrder(orderId);
            return ResponseEntity.ok(new ApiResponse("Order found",order));
        }catch(ResourceNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage(),null));
        }catch(Exception e){
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<ApiResponse> getOrdersByUserId(@PathVariable Long userId){
        try{
            List<OrderDto> order=orderService.getUserOrders(userId);
            return ResponseEntity.ok(new ApiResponse("Success Orders",order));
        }catch(ResourceNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage(),null));
        }catch(Exception e){
            return ResponseEntity.internalServerError().build();
        }
    }
}
