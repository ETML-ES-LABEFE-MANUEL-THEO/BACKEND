package ch.zucchinit.zauction;

import ch.zucchinit.zauction.Category.Category;
import ch.zucchinit.zauction.Category.CategoryRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LoadTestDatabase {

    @Bean
    CommandLineRunner loadCategories(CategoryRepository categoryRepository) {
        return args -> {
            Category it = categoryRepository.save(new Category("Informatique", null));
            Category computer = categoryRepository.save(new Category("Ordinateurs", it));
            categoryRepository.save(new Category("Écrans", computer));
            categoryRepository.save(new Category("Périphériques", it));
            categoryRepository.save(new Category("Portables", it));
            Category jewelry = categoryRepository.save(new Category("Bijoux", null));
            categoryRepository.save(new Category("Bagues", jewelry));
            categoryRepository.save(new Category("Colliers", jewelry));
            Category cars = categoryRepository.save(new Category("Voitures", null));
            categoryRepository.save(new Category("Luxe", cars));
            categoryRepository.save(new Category("Sport", cars));
        };
    }
}
