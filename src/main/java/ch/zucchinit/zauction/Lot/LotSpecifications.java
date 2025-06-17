package ch.zucchinit.zauction.Lot;

import ch.zucchinit.zauction.Auction.Auction;
import ch.zucchinit.zauction.Category.CategoryService;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class LotSpecifications {
    public static Specification<Lot> orderByDatesDesc() {
        return (root, query, cb) -> {
            query.distinct(true);
            query.orderBy(
                    cb.desc(root.get("transferDate")),
                    cb.desc(root.get("closeDate")),
                    cb.desc(root.get("publishDate")),
                    cb.desc(root.get("creationDate"))
            );

            return cb.conjunction();
        };
    }

    public static Specification<Lot> orderByPublishDate() {
        return (root, query, cb) -> {
            query.distinct(true);
            query.orderBy(cb.desc(root.get("id")));

            return cb.conjunction();
        };
    }

    static Specification<Lot> isBuyer(Long userId) {
        return (root, query, cb) -> {
            query.distinct(true);

            Join<Lot, Auction> auctionsJoin = root.join("auctions", JoinType.LEFT);
            return cb.or(
                    cb.equal(root.get("buyerUser").get("id"), userId),
                    cb.equal(auctionsJoin.get("user").get("id"), userId)
            );
        };
    }

    static Specification<Lot> isSeller(Long userId) {
        return (root, query, cb) -> cb.equal(root.get("sellerUser").get("id"), userId);
    }

    static Specification<Lot> isAvailable() {
        LocalDateTime now = LocalDateTime.now();
        return (root, query, cb) -> cb.and(
                cb.isNotNull(root.get("publishDate")),
                cb.lessThanOrEqualTo(root.get("publishDate"), now),
                cb.or(
                        cb.isNull(root.get("closeDate")),
                        cb.greaterThan(root.get("closeDate"), now)
                )
        );
    }

    static Specification<Lot> hasCategoryId(CategoryService categoryService, Long categoryId) {
        return (root, query, cb) -> {
            List<Long> ids = categoryService.getChildIds(categoryId, null);
            return cb.or(
                    cb.equal(root.get("category").get("id"), categoryId),
                    root.get("category").get("id").in(ids)
            );
        };
    }

    static Specification<Lot> multiFieldSearch(String keyword) {
        return (root, query, cb) -> {
            if (keyword.isBlank()) return null;

            String likePattern = "%" + keyword.toLowerCase() + "%";
            return cb.or(
                    cb.like(cb.lower(root.get("name")), likePattern),
                    cb.like(cb.lower(root.get("description")), likePattern),
                    cb.like(cb.lower(root.get("location")), likePattern),
                    cb.like(cb.toString(root.get("id")), likePattern)
            );
        };
    }

    static Specification<Lot> betweenMaxAuctionPrice(BigDecimal min, BigDecimal max) {
        return (root, query, cb) -> {
            if (min != null && max != null) return cb.between(root.get("lastPrice"), min, max);
            else if (min != null) return cb.greaterThanOrEqualTo(root.get("lastPrice"), min);
            else if (max != null) return cb.lessThanOrEqualTo(root.get("lastPrice"), max);
            return null;
        };
    }
}