package ch.zucchinit.zauction.Lot;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class LotService {

    private final LotRepository lotRepository;

    public LotService(LotRepository lotRepository) {
        this.lotRepository = lotRepository;
    }

    public LotDTO.LotDetails findById(Long id) {
        Lot lot = this.lotRepository.findById(id).orElseThrow(); // TODO add exception

        String[] medias = new String[0]; // TODO replace with s3 urls
        BigDecimal lastPrice = lot.getInitialPrice().add(BigDecimal.valueOf(100)); // TODO replace with correct last price
        return new LotDTO.LotDetails(lot.getId(), lot.getName(), lot.getDescription(), lot.getLocation(), lot.getInitialPrice(), lastPrice, medias, lot.getOpenDate(), lot.getAwardDate(), lot.getCloseDate(), lot.getCategory());
    }

    static Specification<Lot> hasCategoryId(Long categoryId) {
        return (root, query, cb) -> categoryId == null ? null : cb.equal(root.get("category").get("id"), categoryId);
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
        Specification<Lot> spec = Specification.where(hasCategoryId(categoryId)).and(multiFieldSearch(search));
        Pageable pageable = PageRequest.of(page, take);

        Page<Lot> lots = this.lotRepository.findAll(spec, pageable);
        LotDTO.LotThumbnail[] thumbnails = lots.getContent().stream().map(lot -> {
            BigDecimal lastPrice = lot.getInitialPrice().add(BigDecimal.valueOf(100)); // TODO replace with correct last price

            // TODO replace with first s3 url
            String thumbnail = "https://images.unsplash.com/photo-1745905932716-431e50eac74b?q=80&w=1974&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D";

            return new LotDTO.LotThumbnail(lot.getId(), lot.getName(), lot.getLocation(), lot.getInitialPrice(), lastPrice, thumbnail);
        }).toArray(LotDTO.LotThumbnail[]::new);

        return new LotDTO.LotPaginatedThumbnail(thumbnails, page, take, lots.getNumberOfElements());
    }
}