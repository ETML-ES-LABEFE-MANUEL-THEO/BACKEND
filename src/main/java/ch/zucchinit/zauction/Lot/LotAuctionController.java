package ch.zucchinit.zauction.Lot;

import ch.zucchinit.zauction.Auction.AuctionDTO;
import ch.zucchinit.zauction.Auction.AuctionService;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/lots/{id}/auctions")
public class LotAuctionController {
    private final LotService lotService;
    private final AuctionService auctionService;

    public LotAuctionController(LotService lotService, AuctionService auctionService) {
        this.lotService = lotService;
        this.auctionService = auctionService;
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping()
    public List<AuctionDTO.AuctionPrice> auctionsPerPeriod(@PathVariable Long id, @RequestParam(required = false) String period) {
        AuctionDTO.TimePeriod timePeriod;

        try {
            timePeriod = AuctionDTO.TimePeriod.valueOf(period.toUpperCase());
        } catch (Exception e) {
            timePeriod = AuctionDTO.TimePeriod.WEEK;
        }

        Lot lot = lotService.getRestrictedLot(id);
        return auctionService.findAuctionsByPeriod(lot, timePeriod);
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping()
    public AuctionDTO.AuctionResponse requestAuction(@PathVariable Long id, @Valid @RequestBody AuctionDTO.AuctionRequest auctionRequest) {
        Lot lot = lotService.getRestrictedLot(id);
        return auctionService.createAuction(lot, auctionRequest);
    }
}
