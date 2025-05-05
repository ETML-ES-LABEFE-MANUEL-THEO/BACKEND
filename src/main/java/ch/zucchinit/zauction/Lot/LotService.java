package ch.zucchinit.zauction.Lot;

import ch.zucchinit.zauction.Category.CategoryService;
import ch.zucchinit.zauction.Exceptions.ResourceNotFound;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

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
        return new LotDTO.LotDetails(
                lot.getId(),
                lot.getName(),
                lot.getDescription(),
                lot.getLocation(),
                lot.getInitialPrice(),
                lot.getLastPrice(),
                lot.getMedias(),
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
        LotDTO.LotThumbnail[] thumbnails = lots.getContent().stream().map(lot -> new LotDTO.LotThumbnail(lot.getId(), lot.getName(), lot.getLocation(), lot.getInitialPrice(), lot.getLastPrice(), lot.getMedias().get(0))).toArray(LotDTO.LotThumbnail[]::new);

        return new LotDTO.LotPaginatedThumbnail(thumbnails, page, take, lots.getTotalElements());
    }
}