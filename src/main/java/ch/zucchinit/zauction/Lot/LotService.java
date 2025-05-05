package ch.zucchinit.zauction.Lot;

import ch.zucchinit.zauction.Category.CategoryRepository;
import ch.zucchinit.zauction.Category.CategoryService;
import ch.zucchinit.zauction.Exceptions.ResourceNotFound;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class LotService {

    private final LotRepository lotRepository;
    private final CategoryService categoryService;

    public LotService(LotRepository lotRepository, CategoryService categoryRepository) {
        this.lotRepository = lotRepository;
        this.categoryService = categoryRepository;
    }

    public LotDTO.LotDetails findById(Long id) {
        Lot lot = this.lotRepository.findById(id).orElseThrow(ResourceNotFound::new);
        List<String> medias = new ArrayList<>(); // TODO replace with s3 urls

        return new LotDTO.LotDetails(
                lot.getId(),
                lot.getName(),
                lot.getDescription(),
                lot.getLocation(),
                lot.getInitialPrice(),
                lot.getLastPrice(),
                medias,
                lot.getOpenDate(),
                lot.getAwardDate(),
                lot.getCloseDate(),
                lot.getCategory(),
                lot.getAuctions());
    }

    static Specification<Lot> isFinished() {
        return (root, query, cb) -> cb.isNull(root.get("awardDate"));
    }

    Specification<Lot> hasCategoryId(Long categoryId) {
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

    public LotDTO.LotPaginatedThumbnail findByPageWithCategoryAndSearch(Integer page, Integer take, Long categoryId, String search) {
        Specification<Lot> spec = Specification.where(hasCategoryId(categoryId)).and(multiFieldSearch(search)).and(isFinished());
        Pageable pageable = PageRequest.of(page, take);

        Page<Lot> lots = this.lotRepository.findAll(spec, pageable);
        LotDTO.LotThumbnail[] thumbnails = lots.getContent().stream().map(lot -> {
            // TODO replace with first s3 url
            String thumbnail = "https://images.unsplash.com/photo-1745905932716-431e50eac74b?q=80&w=1974&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D";

            return new LotDTO.LotThumbnail(lot.getId(), lot.getName(), lot.getLocation(), lot.getInitialPrice(), lot.getLastPrice(), thumbnail);
        }).toArray(LotDTO.LotThumbnail[]::new);

        return new LotDTO.LotPaginatedThumbnail(thumbnails, page, take, lots.getTotalElements());
    }
}