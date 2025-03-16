package com.dailycodework.dreamshops;

import lombok.Getter;
import lombok.Setter;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class DreamShopsApplicationTests {

	@Test
	void contextLoads() {
	}

	@Test
	void lombokWorks(){
		TestLombok test = new TestLombok();
		test.setName("Lombok fonctionne !");
		System.out.println(test.getName());
	}


}

@Getter
@Setter
class TestLombok{
	private String name;
}
