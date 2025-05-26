package ch.zucchinit.zauction.Auction;

import ch.zucchinit.zauction.Lot.Lot;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface AuctionRepository extends JpaRepository<Auction, Long> {
    List<Auction> findByLotAndDateAfterOrderByDateDesc(Lot lot, LocalDateTime date);
    boolean existsAuctionByLotAndPriceGreaterThanEqual(Lot lot, BigDecimal price);
}
