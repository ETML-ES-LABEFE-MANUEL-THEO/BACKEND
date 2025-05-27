package ch.zucchinit.zauction.Auction;

import ch.zucchinit.zauction.Lot.Lot;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public interface AuctionRepository extends JpaRepository<Auction, Long> {
    List<Auction> findByLotAndDateAfterOrderByDateDesc(Lot lot, LocalDateTime date);
}
