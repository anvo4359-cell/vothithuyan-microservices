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
            // Danh mục món
            Category catTea = categoryRepository.save(new Category(null, "Trà sữa truyền thống", "Các loại trà sữa đậm đà hương vị", true));
            Category catFruit = categoryRepository.save(new Category(null, "Trà trái cây tươi", "Trà kết hợp hoa quả nhiệt đới tươi mát", true));
            Category catSpecial = categoryRepository.save(new Category(null, "Món đặc biệt signature", "Bộ sưu tập trà sữa sáng tạo độc quyền", true));

            // Danh sách món
            Product milkTea = productRepository.save(new Product(null, "Trà sữa Oolong Nướng", "Trà Oolong đậm vị thơm mùi caramel", new BigDecimal("35000"), "https://images.unsplash.com/photo-1558857563-b37cf52db0f1?w=500", true, catTea));
            Product fruitTea = productRepository.save(new Product(null, "Trà Đào Cam Sả", "Trà đào thơm nồng cùng sả tươi và cam vàng", new BigDecimal("40000"), "https://images.unsplash.com/photo-1513558161293-cdaf765ed2fd?w=500", true, catFruit));
            Product bobaMilkTea = productRepository.save(new Product(null, "Trà sữa Trân Châu Đường Đen", "Sữa tươi thanh trùng cùng trân châu nấu đường đen dẻo dai", new BigDecimal("45000"), "https://images.unsplash.com/photo-1572490122747-3968b75cc699?w=500", true, catSpecial));
            Product jasmineTea = productRepository.save(new Product(null, "Trà Xanh Lài Sữa", "Trà hoa nhài thanh mát thơm ngát cùng sữa béo", new BigDecimal("32000"), "https://images.unsplash.com/photo-1544787219-7f47ccb76574?w=500", true, catTea));
            Product lycheeTea = productRepository.save(new Product(null, "Trà Vải Hoa Hồng", "Trà hoa hồng quý phái kết hợp trái vải mọng nước", new BigDecimal("42000"), "https://images.unsplash.com/photo-1595981267035-7b04ca84a82d?w=500", true, catFruit));

            // Công thức định lượng pha chế (phục vụ bếp KDS)
            recipeItemRepository.save(new RecipeItem(null, "Cốt trà Oolong", 150.0, "ml", "Ủ nóng 90 độ C", milkTea));
            recipeItemRepository.save(new RecipeItem(null, "Bột sữa non béo", 35.0, "gram", "Khuấy tan hoàn toàn", milkTea));
            recipeItemRepository.save(new RecipeItem(null, "Nước đường mía", 25.0, "ml", "Mức ngọt chuẩn 100%", milkTea));

            recipeItemRepository.save(new RecipeItem(null, "Cốt trà đen", 120.0, "ml", "Lọc bã sạch", fruitTea));
            recipeItemRepository.save(new RecipeItem(null, "Đào miếng ngâm", 2.0, "lát", "Cắt làm đôi", fruitTea));
            recipeItemRepository.save(new RecipeItem(null, "Nước cốt cam tươi", 30.0, "ml", "Vắt lấy nước cốt", fruitTea));

            recipeItemRepository.save(new RecipeItem(null, "Sữa tươi thanh trùng", 180.0, "ml", "Giữ lạnh ở 4 độ C", bobaMilkTea));
            recipeItemRepository.save(new RecipeItem(null, "Sốt đường đen", 30.0, "ml", "Rưới quanh thành ly tạo vệt", bobaMilkTea));

            // Danh sách topping
            toppingRepository.save(new Topping(null, "Trân châu hoàng kim", new BigDecimal("8000"), true));
            toppingRepository.save(new Topping(null, "Thạch củ năng ngũ sắc", new BigDecimal("10000"), true));
            toppingRepository.save(new Topping(null, "Kem cheese béo mặn", new BigDecimal("12000"), true));
            toppingRepository.save(new Topping(null, "Pudding trứng mịn", new BigDecimal("9000"), true));
        }
    }
}
