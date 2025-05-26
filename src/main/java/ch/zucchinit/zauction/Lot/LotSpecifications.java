package ch.zucchinit.zauction.Lot;

import ch.zucchinit.zauction.Category.CategoryService;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public class LotSpecifications {

    static Specification<Lot> isFinished() {
        return (root, query, cb) -> cb.isNull(root.get("awardDate"));
    }

    static Specification<Lot> hasCategoryId(CategoryService categoryService, Long categoryId) {
        return (root, query, cb) -> {
            if (categoryId == null) return null;
            else {
                List<Long> ids = categoryService.getChildIds(categoryId, null);
                return cb.or(
                        cb.equal(root.get("category").get("id"), categoryId),
                        root.get("category").get("id").in(ids)
                );
            }
        };
    }

    static Specification<Lot> multiFieldSearch(String keyword) {
        return (root, query, cb) -> {
            if (keyword == null || keyword.isBlank()) return null;

            String likePattern = "%" + keyword.toLowerCase() + "%";
            return cb.or(
                    cb.like(cb.lower(root.get("name")), likePattern),
                    cb.like(cb.lower(root.get("description")), likePattern),
                    cb.like(cb.lower(root.get("location")), likePattern),
                    cb.like(cb.toString(root.get("id")), likePattern)
            );
        };
    }
}
