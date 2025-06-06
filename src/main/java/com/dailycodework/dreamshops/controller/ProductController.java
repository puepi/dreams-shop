package com.dailycodework.dreamshops.controller;

import com.dailycodework.dreamshops.dto.ProductDto;
import com.dailycodework.dreamshops.exceptions.ResourceNotFoundException;
import com.dailycodework.dreamshops.model.Product;
import com.dailycodework.dreamshops.request.AddProductRequest;
import com.dailycodework.dreamshops.response.ApiResponse;
import com.dailycodework.dreamshops.service.product.ProductService;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("${api.prefix}/products")
public class ProductController {
    private final ProductService productService;

    @GetMapping("/all")
    public ResponseEntity<ApiResponse> getAllProducts(){
        List<Product> products=productService.getAllProducts();
        List<ProductDto> dto=productService.getConvertedProducts(products);
        return ResponseEntity.ok(new ApiResponse("Success",dto));
    }

    @GetMapping("/{productId}")
    public ResponseEntity<ApiResponse> getProductById(@PathVariable Long productId){
        try{
            Product product=productService.getProductById(productId);
            ProductDto dto=productService.convertToDto(product);
            return ResponseEntity.ok(new ApiResponse("success",dto));
        }catch(ResourceNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage(),null));
        }
    }

    @PostMapping("/add")
    public ResponseEntity<ApiResponse> addProduct(@RequestBody AddProductRequest product){
        try {
            Product theProduct=productService.addProduct(product);
            return ResponseEntity.ok(new ApiResponse("success:product added",theProduct));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse("Failed",null));
        }
    }

    @PutMapping("/{productId}")
    public ResponseEntity<ApiResponse> updateProduct(AddProductRequest productRequest,@PathVariable Long productId){
        try{
            Product product=productService.getProductById(productId);
            ProductDto dto=productService.convertToDto(product);
            return ResponseEntity.ok(new ApiResponse("Success:product update",dto));
        }catch(ResourceNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage(),null));
        }
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<ApiResponse> deleteProductById(@PathVariable Long productId){
        try{
            productService.deleteProductById(productId);
            return ResponseEntity.ok(new ApiResponse("Success: product deleted",null));
        }catch(ResourceNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage(),null));
        }
    }

    @GetMapping("/brand-name")
    public ResponseEntity<ApiResponse> getProductByBrandAndName(@RequestParam String brandName,@RequestParam String name){
        try {
            List<Product> products=productService.getProductsByBrandAndName(brandName,name);
            List<ProductDto> dto=productService.getConvertedProducts(products);
            return ResponseEntity.ok(new ApiResponse("Success",dto));
        } catch (Exception e) {
            return  ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(),null));
        }
    }

    @GetMapping("/category-brand")
    public ResponseEntity<ApiResponse> getProductByCategoryAndBrand(@RequestParam String category,@RequestParam String brand){
        try {
            List<Product> products=productService.getProductsByCategoryAndBrand(category,brand);
            List<ProductDto> dto=productService.getConvertedProducts(products);
            return ResponseEntity.ok(new ApiResponse("Success",dto));
        } catch (Exception e) {
            return  ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(),null));
        }
    }

    @GetMapping("/name")
    public ResponseEntity<ApiResponse> getProductByName(@RequestParam String name){
        try {
            List<Product> products=productService.getProductsByName(name);
            List<ProductDto> dto=productService.getConvertedProducts(products);
            return ResponseEntity.ok(new ApiResponse("Success",products));
        } catch (Exception e) {
            return  ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(),null));
        }
    }

    @GetMapping("/category")
    public ResponseEntity<ApiResponse> getProductByCategory(@RequestParam String category){
        try{
            List<Product> products=productService.getProductsByCategory(category);
            List<ProductDto> dto=productService.getConvertedProducts(products);
            return ResponseEntity.ok(new ApiResponse("Success:",products));
        }catch(Exception e){
            return  ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(),null));
        }
    }

    @GetMapping("/brand")
    public ResponseEntity<ApiResponse> getProductsByBrand(@RequestParam String brand){
        try{
            List<Product> products=productService.getProductsByBrand(brand);
            List<ProductDto> dto=productService.getConvertedProducts(products);
            if(dto==null)
                throw new ResourceNotFoundException("No product found");
            return ResponseEntity.ok(new ApiResponse("Success",dto));
        }catch(Exception e){
            return  ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(),null));
        }
    }

    @GetMapping("/count/brand-name")
    public ResponseEntity<ApiResponse> countProductsByBrandAndName(@RequestParam String brand,@RequestParam String name){
        try {
            Long count=productService.countProductsByBrandAndName(brand,name);
            return ResponseEntity.ok(new ApiResponse("Product count",count));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(),null));
        }
    }
}
