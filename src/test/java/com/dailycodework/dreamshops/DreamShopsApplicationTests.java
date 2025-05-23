package com.dailycodework.dreamshops;

import com.dailycodework.dreamshops.model.Category;
import com.dailycodework.dreamshops.model.Image;
import com.dailycodework.dreamshops.request.AddProductRequest;
import com.dailycodework.dreamshops.service.cart.CartItemService;
import com.dailycodework.dreamshops.service.cart.CartService;
import lombok.Getter;
import lombok.Setter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import test.Apple;
import test.Color;
import test.Dish;
import test.Letter;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class DreamShopsApplicationTests {
	
	@Autowired
	private CartService cartService;
	@Autowired
	private CartItemService cartItemService;

	private List<Dish> menu = Arrays.asList(
			new Dish("pork", false, 800, Dish.Type.MEAT),
			new Dish("beef", false, 700, Dish.Type.MEAT),
			new Dish("chicken", false, 400, Dish.Type.MEAT),
			new Dish("french fries", true, 530, Dish.Type.OTHER),
			new Dish("rice", true, 350, Dish.Type.OTHER),
			new Dish("season fruit", true, 120, Dish.Type.OTHER),
			new Dish("pizza", true, 550, Dish.Type.OTHER),
			new Dish("prawns", false, 300, Dish.Type.FISH),
			new Dish("salmon", false, 450, Dish.Type.FISH) );

	@Test
	void contextLoads() {
	}

	@Test
	public void testFilterApples(){
		List<Apple> inventory =List.of(
				Apple.builder()
						.color(Color.GREEN)
						.weight(189)
						.build()
		);
		System.out.println("result = " + Apple.filterApples(inventory,Apple::isGreenApple));
		System.out.println("result = " + Apple.filterApples(inventory,
				(Apple a) -> a.getWeight() < 80 || a.getColor().equals(Color.RED)
		));
	}

	@Test
	public void anotherTest(){
		Comparator<Apple> byWeight=new Comparator<Apple>() {
			@Override
			public int compare(Apple o1, Apple o2) {
				return o1.getWeight() - o2.getWeight();
			}
		};
		Comparator<Apple> byWeight2=(Apple a1,Apple a2)-> a1.getWeight() - a2.getWeight();
	}

	@Test
	public void testFunctions(){
		Function<String,String> addHeader= Letter::addHeader;
		Function<String,String> transformationPipeline=addHeader.andThen(Letter::checkSpelling).andThen(Letter::addFooter);
	}

	@Test
	public void whenCreatesEmptyOptional_thenCorrect(){
		Optional<String> empty=Optional.empty();
		assertFalse(empty.isPresent());
	}

	@Test()
	public void givenNonNull_whenCreatesNullable_thenCorrect() {
		String name = "Mandela";
		Optional<String> opt=Optional.ofNullable(name);
		assertTrue(opt.isPresent());
	}

	@Test
	public void givenOptional_whenIfPresentWorks_thenCorrect(){
		Optional<String> opt=Optional.of("Mandel");
		opt.ifPresent(name-> System.out.println(name.length()));
	}

	@Test
	public void whenOrElseWorks_thenCorrec(){
		String nullName=null;
		String name=Optional.ofNullable("Puepi").orElse("john");
		assertEquals("Puepi",name);
	}

	@Test
	public  void testImage(){
		Image image=new Image();
		System.out.println("image id = " + image.getId());
	}

	@Test
	public void printThreeHighCaloricDishNames(){
		List<String> names=menu.stream()
				.filter(dish->dish.getCalories() > 300)
				.map(Dish::getName)
				.limit(3)
				.collect(toList());
		System.out.println("names = " + names);
	}

	@Test
	public void testAddProduct(){
		Set<Integer> set=new HashSet<>(Arrays.asList(1,8,9,10));
		System.out.println("set = " + set);
	}
	
	
//	@Test
//	public void testInitializeCart(){
//		Long id=cartService.initializeNewCart();
//		System.out.println("id = " + id);
//	}

	@Test
	public void testAddItemToCart(){
//		cartItemService.addItemToCart(1L,3L,3);

	}
}
