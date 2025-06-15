package ch.zucchinit.zauction.Lot;

import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/lots")
public class LotController {
    private final LotService lotService;

    public LotController(LotService lotService) { this.lotService = lotService; }

    @GetMapping()
    public LotDTO.PaginatedLot<LotDTO.LotThumbnail> paginate(
            @RequestParam(required = false, defaultValue = "0") Integer page,
            @RequestParam(required = false, defaultValue = "9") Integer take,
            @ModelAttribute LotDTO.LotFilter filters)
    {
        return lotService.findByPageWithCategoryAndSearch(page, take, filters);
    }

    @GetMapping("/buy")
    public LotDTO.PaginatedLot<LotDTO.LotHistory> historyBuy(
            @RequestParam(required = false, defaultValue = "0") Integer page,
            @RequestParam(required = false, defaultValue = "9") Integer take)
    {
        return lotService.findByPageForHistory(page, take, false);
    }

    @GetMapping("/sell")
    public LotDTO.PaginatedLot<LotDTO.LotHistory> historySell(
            @RequestParam(required = false, defaultValue = "0") Integer page,
            @RequestParam(required = false, defaultValue = "9") Integer take)
    {
        return lotService.findByPageForHistory(page, take, true);
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/{id}")
    public LotDTO.LotDetails one(@PathVariable Long id) {
        Lot lot = lotService.getRestrictedLot(id);
        return lotService.getLotDetails(lot);
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping
    public LotDTO.LotDetails create(
            @Valid @RequestPart(name = "lot") LotDTO.LotCreation lotCreation,
            @RequestPart(name = "mediasMeta") List<Integer> metas,
            @RequestPart(name = "medias") List<MultipartFile> files) throws IOException {
        Map<Integer, MultipartFile> medias = new HashMap<>();
        for (int i = 0; i < metas.size(); i++) medias.put(metas.get(i), files.get(i));

        Lot lot = lotService.createLot(lotCreation, medias);
        return lotService.getLotDetails(lot);
    }

    @PreAuthorize("isAuthenticated()")
    @PatchMapping("/{id}")
    public LotDTO.LotDetails update(
            @PathVariable Long id,
            @Valid @RequestPart(name = "lot") LotDTO.LotModification lotModification,
            @RequestPart(name = "mediasMeta", required = false) List<LotDTO.LotMediaAction> metas,
            @RequestPart(name = "medias", required = false) List<MultipartFile> files) throws IOException {

        Lot lot = lotService.findById(id);
        return lotService.getLotDetails(lotService.updateLot(lot, lotModification, metas, files));
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/{id}/publish")
    public void publishLot(@PathVariable Long id) {
        Lot lot = lotService.findById(id);
        lotService.publishLot(lot);
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/{id}/close")
    public void closeLot(@PathVariable Long id) {
        Lot lot = lotService.findById(id);
        lotService.closeLot(lot);
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/{id}/transfer")
    public LotDTO.LotTransferResult acceptLot(@PathVariable Long id) {
        Lot lot = lotService.findById(id);
        return lotService.transferLot(lot);
    }
}
