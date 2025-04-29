package ch.zucchinit.zauction.Lot;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/lots")
public class LotController {

    private final LotService lotService;

    public LotController(LotService lotService) {
        this.lotService = lotService;
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

    @GetMapping("/{id}")
    public LotDTO.LotDetails one(@PathVariable Long id) {
        return lotService.findById(id);
    }
}
