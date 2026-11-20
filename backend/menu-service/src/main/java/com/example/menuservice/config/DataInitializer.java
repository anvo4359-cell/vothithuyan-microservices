package com.example.menuservice.config;

import com.example.menuservice.entity.Category;
import com.example.menuservice.entity.Product;
import com.example.menuservice.entity.RecipeItem;
import com.example.menuservice.entity.Topping;
import com.example.menuservice.repository.CategoryRepository;
import com.example.menuservice.repository.ProductRepository;
import com.example.menuservice.repository.RecipeItemRepository;
import com.example.menuservice.repository.ToppingRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;

@Configuration
public class DataInitializer implements CommandLineRunner {

    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;
    private final RecipeItemRepository recipeItemRepository;
    private final ToppingRepository toppingRepository;

    public DataInitializer(CategoryRepository categoryRepository,
                           ProductRepository productRepository,
                           RecipeItemRepository recipeItemRepository,
                           ToppingRepository toppingRepository) {
        this.categoryRepository = categoryRepository;
        this.productRepository = productRepository;
        this.recipeItemRepository = recipeItemRepository;
        this.toppingRepository = toppingRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        if (categoryRepository.count() == 0) {
            // Seed Categories
            Category catTea = categoryRepository.save(new Category(null, "Trà sữa truyền thống", "Các loại trà sữa đậm đà hương vị", true));
            Category catFruit = categoryRepository.save(new Category(null, "Trà trái cây tươi", "Trà kết hợp hoa quả nhiệt đới tươi mát", true));

            // Seed Products
            Product milkTea = productRepository.save(new Product(null, "Trà sữa Oolong Nướng", "Trà Oolong đậm vị thơm mùi caramel", new BigDecimal("35000"), "https://images.unsplash.com/photo-1558857563-b37cf52db0f1", true, catTea));
            Product fruitTea = productRepository.save(new Product(null, "Trà Đào Cam Sả", "Trà đào thơm nồng cùng sả tươi và cam vàng", new BigDecimal("40000"), "https://images.unsplash.com/photo-1513558161293-cdaf765ed2fd", true, catFruit));

            // Seed Recipes (Công thức pha chế cho KDS bếp)
            recipeItemRepository.save(new RecipeItem(null, "Cốt trà Oolong", 150.0, "ml", "Ủ nóng 90 độ C", milkTea));
            recipeItemRepository.save(new RecipeItem(null, "Bột sữa non béo", 35.0, "gram", "Khuấy tan hoàn toàn", milkTea));
            recipeItemRepository.save(new RecipeItem(null, "Nước đường mía", 25.0, "ml", "Tương đương mức ngọt chuẩn 100%", milkTea));

            recipeItemRepository.save(new RecipeItem(null, "Cốt trà đen", 120.0, "ml", "Lọc bã sạch", fruitTea));
            recipeItemRepository.save(new RecipeItem(null, "Đào miếng ngâm", 2.0, "lát", "Cắt làm đôi", fruitTea));
            recipeItemRepository.save(new RecipeItem(null, "Nước cốt cam tươi", 30.0, "ml", "Vắt lấy nước cốt", fruitTea));

            // Seed Toppings
            toppingRepository.save(new Topping(null, "Trân châu hoàng kim", new BigDecimal("8000"), true));
            toppingRepository.save(new Topping(null, "Thạch củ năng ngũ sắc", new BigDecimal("10000"), true));
            toppingRepository.save(new Topping(null, "Kem cheese béo mặn", new BigDecimal("12000"), true));
        }
    }
}
