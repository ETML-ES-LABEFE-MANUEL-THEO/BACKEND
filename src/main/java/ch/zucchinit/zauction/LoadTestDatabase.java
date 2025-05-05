package ch.zucchinit.zauction;

import ch.zucchinit.zauction.Auction.Auction;
import ch.zucchinit.zauction.Auction.AuctionRepository;
import ch.zucchinit.zauction.Category.Category;
import ch.zucchinit.zauction.Category.CategoryRepository;
import ch.zucchinit.zauction.Lot.Lot;
import ch.zucchinit.zauction.Lot.LotRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Configuration
public class LoadTestDatabase implements CommandLineRunner {

    private final CategoryRepository categoryRepository;
    private final LotRepository lotRepository;
    private final AuctionRepository auctionRepository;

    public LoadTestDatabase(CategoryRepository categoryRepository, LotRepository lotRepository, AuctionRepository auctionRepository) {
        this.categoryRepository = categoryRepository;
        this.lotRepository = lotRepository;
        this.auctionRepository = auctionRepository;
    }

    public void run(String... args) {
        Category it = categoryRepository.save(new Category("Informatique", null));
        Category computer = categoryRepository.save(new Category("Ordinateurs", it));
        Category screen = categoryRepository.save(new Category("Écrans", computer));
        categoryRepository.save(new Category("Périphériques", it));
        categoryRepository.save(new Category("Portables", it));
        Category jewelry = categoryRepository.save(new Category("Bijoux", null));
        categoryRepository.save(new Category("Bagues", jewelry));
        categoryRepository.save(new Category("Colliers", jewelry));
        Category cars = categoryRepository.save(new Category("Voitures", null));
        categoryRepository.save(new Category("Luxe", cars));
        categoryRepository.save(new Category("Sport", cars));

        lotRepository.save(new Lot("Ordinateur Saphira Diamond Edition", "Un super ordinateur quantique d'une édition limitée !", "Lausanne", BigDecimal.valueOf(10_000), getRandomMedias(), it));
        lotRepository.save(new Lot("Écran Asus 27 pouces", "Un super écran !", "Morges", BigDecimal.valueOf(1_000), getRandomMedias(), screen));
        Lot zonda = lotRepository.save(new Lot("Pagani Zonda", "La beauté incarnée !", "Gstaad", BigDecimal.valueOf(1_000_000), getRandomMedias(), cars));

        auctionRepository.save(new Auction(BigDecimal.valueOf(1_100_000), zonda));
        auctionRepository.save(new Auction(BigDecimal.valueOf(1_200_000), zonda));
        auctionRepository.save(new Auction(BigDecimal.valueOf(1_300_000), zonda));
    }

    List<String> getRandomMedias() {
        List<String> medias = new ArrayList<>(
            List.of(
                "https://images.unsplash.com/photo-1559703248-dcaaec9fab78?q=80&w=1964&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D",
                "https://images.unsplash.com/photo-1557180295-76eee20ae8aa?q=80&w=2080&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D",
                "https://images.unsplash.com/photo-1429087969512-1e85aab2683d?q=80&w=1974&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D",
                "https://images.unsplash.com/photo-1503602642458-232111445657?q=80&w=1974&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D",
                "https://images.unsplash.com/reserve/LJIZlzHgQ7WPSh5KVTCB_Typewriter.jpg?q=80&w=1992&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D",
                "https://images.unsplash.com/photo-1528825871115-3581a5387919?q=80&w=1915&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D",
                "https://images.unsplash.com/photo-1523049673857-eb18f1d7b578?q=80&w=1975&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D",
                "https://images.unsplash.com/photo-1516962126636-27ad087061cc?q=80&w=1974&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D",
                "https://images.unsplash.com/photo-1554591203-3c8b71297add?q=80&w=2070&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D",
                "https://images.unsplash.com/photo-1565656898731-61d5df85f9a7?q=80&w=2070&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D"
        ));

        Collections.shuffle(medias);
        return medias.subList(0, 5);
    }
}
