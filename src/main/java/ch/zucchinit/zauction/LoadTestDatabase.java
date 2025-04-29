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

        lotRepository.save(new Lot("Ordinateur Saphira Diamond Edition", "Un super ordinateur quantique d'une édition limitée !", "Lausanne", BigDecimal.valueOf(10_000), it));
        lotRepository.save(new Lot("Écran Asus 27 pouces", "Un super écran !", "Morges", BigDecimal.valueOf(1_000), screen));
        Lot zonda = lotRepository.save(new Lot("Pagani Zonda", "La beauté incarnée !", "Gstaad", BigDecimal.valueOf(1_000_000), cars));

        auctionRepository.save(new Auction(BigDecimal.valueOf(1_100_000), zonda));
        auctionRepository.save(new Auction(BigDecimal.valueOf(1_200_000), zonda));
        auctionRepository.save(new Auction(BigDecimal.valueOf(1_300_000), zonda));
    }
}
