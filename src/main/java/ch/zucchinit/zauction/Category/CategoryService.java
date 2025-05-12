package ch.zucchinit.zauction.Category;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public List<Category> findAll() {
        return categoryRepository.findByParentIsNull();
    }

    public List<Long> getChildIds(Long categoryId, List<Long> ids) {
        if (ids == null) ids = new ArrayList<>();

        for (Category child : categoryRepository.findById(categoryId).orElseGet(Category::new).getChildren()) {
            ids.add(child.getId());
            getChildIds(child.getId(), ids);
        }

        return ids;
    }

    public List<Category> getReverseCategories(Long categoryId) {
        List<Category> categories = new ArrayList<>();
        Category category = categoryRepository.findById(categoryId).orElse(null);

        while (category != null) {
            categories.add(category);
            category = category.getParent();
        }

        Collections.reverse(categories);

        return categories;
    }

}
