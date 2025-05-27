package ch.zucchinit.zauction.Lot;

import ch.zucchinit.zauction.Auction.AuctionDTO;
import ch.zucchinit.zauction.Auction.AuctionService;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/lots")
public class LotController {
    private final LotService lotService;
    private final AuctionService auctionService;

    public LotController(LotService lotService, AuctionService auctionService) {
        this.lotService = lotService;
        this.auctionService = auctionService;
    }

    @GetMapping()
    public LotDTO.LotPaginatedThumbnail paginate(
            @RequestParam(required = false, defaultValue = "0") Integer page,
            @RequestParam(required = false, defaultValue = "9") Integer take,
            @RequestParam(required = false, name = "category") Long categoryId,
            @RequestParam(required = false) String search)
    {
        return lotService.findByPageWithCategoryAndSearch(page, take, categoryId, search);
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/{id}")
    public LotDTO.LotDetails one(@PathVariable Long id) {
        Lot lot = lotService.findById(id);
        return lotService.getLotDetails(lot);
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/{id}/auctions")
    public List<AuctionDTO.AuctionPrice> auctionsPerPeriod(@PathVariable Long id, @RequestParam(required = false) String period) {
        AuctionDTO.TimePeriod timePeriod;

        try {
            timePeriod = AuctionDTO.TimePeriod.valueOf(period.toUpperCase());
        } catch (Exception e) {
            timePeriod = AuctionDTO.TimePeriod.WEEK;
        }

        Lot lot = lotService.findById(id);
        return auctionService.findAuctionsByPeriod(lot, timePeriod);
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/{id}/auctions")
    public AuctionDTO.AuctionResponse requestAuction(@PathVariable Long id, @Valid @RequestBody AuctionDTO.AuctionRequest auctionRequest) {
        Lot lot = lotService.findById(id);
        return auctionService.createAuction(lot, auctionRequest);
    }
}
