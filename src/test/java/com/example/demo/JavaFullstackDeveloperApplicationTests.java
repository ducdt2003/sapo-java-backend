package com.example.demo;

import com.example.demo.entity.FlashSaleOrder;
import com.example.demo.entity.Product;
import com.example.demo.entity.User;
import com.example.demo.repository.FlashSaleOrderRepository;
import com.example.demo.repository.ProductRepository;
import com.example.demo.repository.UserRepository;
import com.github.javafaker.Faker;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Random;

@SpringBootTest
class JavaFullstackDeveloperApplicationTests {

	@Autowired
	private ProductRepository productRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private FlashSaleOrderRepository orderRepository;

	Faker faker = new Faker();
	Random random = new Random();

	@Test
	void fakeData() {

		// Tạo 100 user
		for (int i = 0; i < 100; i++) {
			User user = new User();

			if (i < 10) {
				user.setUsername("test_user_" + i);
			} else {
				user.setUsername(faker.name().username());
			}

			userRepository.save(user);
		}

		// Tạo 10 sản phẩm flash sale (tổng 500 stock)
		for (int i = 0; i < 10; i++) {
			Product product = new Product();
			product.setName(faker.commerce().productName());
			product.setPrice(Double.parseDouble(faker.commerce().price()));
			product.setStock(50);
			product.setFlashSale(true);

			productRepository.save(product);
		}

		// 90 user đầu mua mỗi người 1 sản phẩm
		for (long userId = 1; userId <= 90; userId++) {

			FlashSaleOrder order = new FlashSaleOrder();

			order.setUserId(userId);
			order.setProductId((long) (random.nextInt(10) + 1)); // 10 product
			order.setQuantity(1); // mỗi user mua 1

			orderRepository.save(order);
		}
	}
}
