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
import java.time.LocalDateTime;
import java.util.*;

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
// --- Création des catégories principales et secondaires ---

        Category informatique = categoryRepository.save(new Category("Informatique", null));
        Category ordinateurs = categoryRepository.save(new Category("Ordinateurs", informatique));
        Category ecrans = categoryRepository.save(new Category("Écrans", informatique));
        Category peripheriques = categoryRepository.save(new Category("Périphériques", informatique));
        Category smartphones = categoryRepository.save(new Category("Smartphones", informatique));

        Category bijoux = categoryRepository.save(new Category("Bijoux", null));
        Category bagues = categoryRepository.save(new Category("Bagues", bijoux));
        Category colliers = categoryRepository.save(new Category("Colliers", bijoux));
        Category montres = categoryRepository.save(new Category("Montres", bijoux));

        Category voitures = categoryRepository.save(new Category("Voitures", null));
        Category luxe = categoryRepository.save(new Category("Luxe", voitures));
        Category sport = categoryRepository.save(new Category("Sport", voitures));
        Category utilitaires = categoryRepository.save(new Category("Utilitaires", voitures));

        Category art = categoryRepository.save(new Category("Art", null));
        Category peinture = categoryRepository.save(new Category("Peinture", art));
        Category sculpture = categoryRepository.save(new Category("Sculpture", art));
        Category photographie = categoryRepository.save(new Category("Photographie", art));

        Category maison = categoryRepository.save(new Category("Maison", null));
        Category meubles = categoryRepository.save(new Category("Meubles", maison));
        Category electro = categoryRepository.save(new Category("Électroménager", maison));
        Category deco = categoryRepository.save(new Category("Décoration", maison));


        Lot macbook = lotRepository.save(new Lot(
                "MacBook Pro 16'' M3 Max",
                "Dernière génération, état neuf, garantie 2 ans.",
                "Genève",
                BigDecimal.valueOf(3500),
                List.of(
                        "https://store.storeimages.cdn-apple.com/4668/as-images.apple.com/is/mbp16-spaceblack-select-202310?wid=2000&hei=2000&fmt=jpeg&qlt=95&.v=1697311090226", // Image officielle Apple
                        "https://www.apple.com/ch-de/newsroom/images/product/mac/standard/Apple-MacBook-Pro-16-inch-hero-231030_big.jpg.large.jpg"
                ),
                ordinateurs
        ));

        Lot dell = lotRepository.save(new Lot(
                "Dell XPS 13",
                "Ultrabook performant, parfait pour les déplacements.",
                "Lausanne",
                BigDecimal.valueOf(1200),
                List.of(
                        "https://i.dell.com/sites/csimages/App-Merchandizing_Images/all/xps-13-9310-laptop-campaign-hero-504x350-ng.psd",
                        "https://www.notebookcheck.net/uploads/tx_nbc2/DellXPS139310__1_.JPG"
                ),
                ordinateurs
        ));

        Lot iphone = lotRepository.save(new Lot(
                "iPhone 15 Pro Max",
                "128 Go, bleu, sous blister.",
                "Neuchâtel",
                BigDecimal.valueOf(1100),
                List.of(
                        "https://store.storeimages.cdn-apple.com/4668/as-images.apple.com/is/iphone-15-pro-model-unselect-gallery-1-202309?wid=5120&hei=2880&fmt=jpeg&qlt=80&.v=1692914157015"
                ),
                smartphones
        ));

        Lot appleWatch = lotRepository.save(new Lot(
                "Apple Watch Series 9",
                "Boîtier 45mm, bracelet sport, état impeccable.",
                "Fribourg",
                BigDecimal.valueOf(400),
                List.of(
                        "https://store.storeimages.cdn-apple.com/4668/as-images.apple.com/is/watch-card-40-s9-202309?wid=600&hei=600&fmt=jpeg&qlt=95&.v=1693501324197"
                ),
                peripheriques
        ));

        Lot ecranSamsung = lotRepository.save(new Lot(
                "Écran Samsung 32'' 4K",
                "Idéal pour le gaming et le travail.",
                "Yverdon",
                BigDecimal.valueOf(350),
                List.of(
                        "https://images.samsung.com/is/image/samsung/p6pim/ch_fr/ls32bm700upxen/gallery/ch-fr-smart-monitor-m7-432623-ls32bm700upxen-533670418?$650_519_PNG$"
                ),
                ecrans
        ));

        Lot bagueOr = lotRepository.save(new Lot(
                "Bague en or blanc 18k",
                "Serti diamant, taille 54.",
                "Lausanne",
                BigDecimal.valueOf(1500),
                List.of(
                        "https://images.unsplash.com/photo-1517841905240-472988babdf9?auto=format&fit=crop&w=800&q=80"
                ),
                bagues
        ));

        Lot rolex = lotRepository.save(new Lot(
                "Rolex Submariner",
                "Montre de luxe, édition 2020, certificat d’authenticité.",
                "Genève",
                BigDecimal.valueOf(9000),
                List.of(
                        "https://www.rolex.com/content/dam/rolexcom/products/watch_assets_front/2020/upright/m126610ln-0001.png"
                ),
                montres
        ));

        Lot collierPerles = lotRepository.save(new Lot(
                "Collier de perles Akoya",
                "Longueur 45cm, fermoir or jaune.",
                "Montreux",
                BigDecimal.valueOf(2500),
                List.of(
                        "https://images.unsplash.com/photo-1506744038136-46273834b3fb?auto=format&fit=crop&w=800&q=80"
                ),
                colliers
        ));

        Lot ferrari = lotRepository.save(new Lot(
                "Ferrari 488 GTB",
                "V8, 670ch, rouge, 15'000km.",
                "Zürich",
                BigDecimal.valueOf(210_000),
                List.of(
                        "https://cdn.ferrari.com/cms/network/media/img/resize/5f6d1b0d4b5a7b001f0b5e6e-ferrari-488-gtb-2015-1280x960.jpg"
                ),
                sport
        ));

        Lot renaultKangoo = lotRepository.save(new Lot(
                "Renault Kangoo Express",
                "Utilitaire, diesel, 2018, 80'000km.",
                "Lausanne",
                BigDecimal.valueOf(9000),
                List.of(
                        "https://cdn.group.renault.com/ren/master/renault-new-kangoo-express-vu-ph1-001.jpg"
                ),
                utilitaires
        ));

        Lot tesla = lotRepository.save(new Lot(
                "Tesla Model S Plaid",
                "Électrique, 2023, 5'000km, full options.",
                "Genève",
                BigDecimal.valueOf(120_000),
                List.of(
                        "https://www.tesla.com/sites/default/files/modelsx-new/social/model-s-hero-social.jpg"
                ),
                luxe
        ));

        Lot tableau = lotRepository.save(new Lot(
                "Tableau 'Coucher de soleil sur le Léman'",
                "Huile sur toile, artiste local.",
                "Vevey",
                BigDecimal.valueOf(1800),
                List.of(
                        "https://images.unsplash.com/photo-1464983953574-0892a716854b?auto=format&fit=crop&w=800&q=80"
                ),
                peinture
        ));

        Lot statue = lotRepository.save(new Lot(
                "Sculpture contemporaine",
                "Acier poli, 1m20 de haut.",
                "Lausanne",
                BigDecimal.valueOf(3200),
                List.of(
                        "https://images.unsplash.com/photo-1502082553048-f009c37129b9?auto=format&fit=crop&w=800&q=80"
                ),
                sculpture
        ));

        Lot photo = lotRepository.save(new Lot(
                "Photographie signée",
                "Édition limitée, 1/10, encadrée.",
                "Genève",
                BigDecimal.valueOf(600),
                List.of(
                        "https://images.unsplash.com/photo-1465101046530-73398c7f28ca?auto=format&fit=crop&w=800&q=80"
                ),
                photographie
        ));

        Lot canape = lotRepository.save(new Lot(
                "Canapé d’angle en cuir",
                "3 places, état neuf, couleur taupe.",
                "Lausanne",
                BigDecimal.valueOf(950),
                List.of(
                        "https://images.unsplash.com/photo-1519710164239-da123dc03ef4?auto=format&fit=crop&w=800&q=80"
                ),
                meubles
        ));

        Lot frigo = lotRepository.save(new Lot(
                "Réfrigérateur Samsung Family Hub",
                "Connecté, grande capacité.",
                "Genève",
                BigDecimal.valueOf(1800),
                List.of(
                        "https://images.samsung.com/is/image/samsung/p6pim/ch_fr/rs6ha8891b1ef/gallery/ch-fr-side-by-side-rs8000a-rs6ha8891b1ef-530345637?$650_519_PNG$"
                ),
                electro
        ));

        Lot vase = lotRepository.save(new Lot(
                "Vase en céramique artisanale",
                "Pièce unique, signé.",
                "Vevey",
                BigDecimal.valueOf(200),
                List.of(
                        "https://images.unsplash.com/photo-1506744038136-46273834b3fb?auto=format&fit=crop&w=800&q=80"
                ),
                deco
        ));

        List<Lot> allLots = lotRepository.findAll();
        generateChronologicalAuctions(allLots, auctionRepository);
    }

    public void generateChronologicalAuctions(List<Lot> lots, AuctionRepository auctionRepository) {
        Random random = new Random();

        for (Lot lot : lots) {
            int auctionCount = 10 + random.nextInt(991);

            BigDecimal basePrice = lot.getInitialPrice();
            BigDecimal currentPrice = basePrice;

            LocalDateTime now = LocalDateTime.now();

            // 1. Générer une liste de dates aléatoires dans la période souhaitée
            List<LocalDateTime> auctionDates = new ArrayList<>();
            for (int i = 0; i < auctionCount; i++) {
                int daysAgo = random.nextInt(90);
                int hour = 8 + random.nextInt(12);
                int minute = random.nextInt(60);
                LocalDateTime date = now.minusDays(daysAgo)
                        .withHour(hour)
                        .withMinute(minute)
                        .withSecond(0)
                        .withNano(0);
                auctionDates.add(date);
            }

            // 2. Trier les dates dans l'ordre croissant (de la plus ancienne à la plus récente)
            auctionDates.sort(Comparator.naturalOrder());

            // 3. Créer et sauvegarder les enchères dans l'ordre des dates
            for (LocalDateTime auctionDate : auctionDates) {
                BigDecimal increment = BigDecimal.valueOf((random.nextDouble() * 0.1 + 0.01) * basePrice.doubleValue());
                currentPrice = currentPrice.add(increment);

                Auction auction = new Auction(currentPrice, lot, auctionDate);
                auctionRepository.save(auction);
            }
        }
    }
}
