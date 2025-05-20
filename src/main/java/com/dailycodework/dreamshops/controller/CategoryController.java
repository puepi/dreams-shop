package com.dailycodework.dreamshops.controller;

import com.dailycodework.dreamshops.exceptions.AlreadyExistsException;
import com.dailycodework.dreamshops.exceptions.ResourceNotFoundException;
import com.dailycodework.dreamshops.model.Category;
import com.dailycodework.dreamshops.response.ApiResponse;
import com.dailycodework.dreamshops.service.category.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("${api.prefix}/categories")
public class CategoryController {
    private final CategoryService categoryService;

    @GetMapping("/all")
    public ResponseEntity<ApiResponse> getAllCategories(){
        try{
            List<Category> categories=categoryService.getAllCategories();
            return ResponseEntity.ok(new ApiResponse("Found",categories));
        }catch(Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body( new ApiResponse("Error",HttpStatus.INTERNAL_SERVER_ERROR));
        }
    }

    @PostMapping("/add")
    public ResponseEntity<ApiResponse> addCategory(@RequestBody Category category){
        try{
            Category newCategory=categoryService.addCategory(category);
            return ResponseEntity.ok(new ApiResponse("Added category successfully",newCategory));
        }catch(AlreadyExistsException e){
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new ApiResponse(e.getMessage(),null));
        }catch(Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse("Error",HttpStatus.INTERNAL_SERVER_ERROR));
        }
    }

    @GetMapping("/{categoryId}")
    public ResponseEntity<ApiResponse> getCategoryById(@PathVariable Long categoryId){
        try{
            Category category=categoryService.getCategoryById(categoryId);
            return ResponseEntity.ok(new ApiResponse("Found",category));
        }catch(ResourceNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage(),null));
        }
    }

    @GetMapping("/{categoryName}/get")
    public ResponseEntity<ApiResponse> getCategoryByName(@PathVariable String categoryName){
        try{
            Category category=categoryService.getCategoryByName(categoryName);
            return ResponseEntity.ok(new ApiResponse("Found",category));
        }catch(ResourceNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage(),null));
        }
    }

    @DeleteMapping("/{categoryId}/delete")
    public ResponseEntity<ApiResponse> deleteCategoryBidId(@PathVariable Long categoryId){
        try{
            categoryService.deleteCategoryById(categoryId);
            return ResponseEntity.ok(new ApiResponse("Deleted successfully",null));
        }catch(ResourceNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage(),null));
        }
    }

    @DeleteMapping("/{categoryId}/update")
    public ResponseEntity<ApiResponse> updateCategory(@PathVariable Long categoryId, Category category){
        try{
            Category updatedCategory=categoryService.updateCategory(category,categoryId);
            return ResponseEntity.ok(new ApiResponse("Update successfully",updatedCategory));
        }catch(ResourceNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage(),null));
        }
    }
}
