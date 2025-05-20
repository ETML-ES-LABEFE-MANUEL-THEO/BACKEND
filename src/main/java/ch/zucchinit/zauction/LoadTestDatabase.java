package ch.zucchinit.zauction;

import ch.zucchinit.zauction.Auction.Auction;
import ch.zucchinit.zauction.Auction.AuctionRepository;
import ch.zucchinit.zauction.Auth.User;
import ch.zucchinit.zauction.Auth.UserRepository;
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

    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final LotRepository lotRepository;
    private final AuctionRepository auctionRepository;

    public LoadTestDatabase(UserRepository userRepository, CategoryRepository categoryRepository, LotRepository lotRepository, AuctionRepository auctionRepository) {
        this.userRepository = userRepository;
        this.categoryRepository = categoryRepository;
        this.lotRepository = lotRepository;
        this.auctionRepository = auctionRepository;
    }

    public void run(String... args) {
        userRepository.save(new User("John", "Snow", "john.snow@zauction.ch", ""));
// Art & Antiquités
        Category artEtAntiquites = categoryRepository.save(new Category("Art & Antiquités", null));

        Category peintures = categoryRepository.save(new Category("Peintures", artEtAntiquites));
        Category maitresAnciens = categoryRepository.save(new Category("Maîtres anciens", peintures));
        Category artModerne = categoryRepository.save(new Category("Art moderne", peintures));
        Category artContemporain = categoryRepository.save(new Category("Art contemporain", peintures));

        Category sculptures = categoryRepository.save(new Category("Sculptures", artEtAntiquites));
        Category bronze = categoryRepository.save(new Category("Bronze", sculptures));
        Category marbre = categoryRepository.save(new Category("Marbre", sculptures));
        Category bois = categoryRepository.save(new Category("Bois", sculptures));

        Category objetsArt = categoryRepository.save(new Category("Objets d’art", artEtAntiquites));
        Category ceramiques = categoryRepository.save(new Category("Céramiques", objetsArt));
        Category ivoires = categoryRepository.save(new Category("Ivoires", objetsArt));
        Category objetsReligieux = categoryRepository.save(new Category("Objets religieux", objetsArt));

        Category antiquites = categoryRepository.save(new Category("Antiquités", artEtAntiquites));
        Category egyptiennes = categoryRepository.save(new Category("Égyptiennes", antiquites));
        Category asiatiques = categoryRepository.save(new Category("Asiatiques", antiquites));
        Category grecoRomaines = categoryRepository.save(new Category("Gréco-romaines", antiquites));

// Bijoux & Montres
        Category bijouxEtMontres = categoryRepository.save(new Category("Bijoux & Montres", null));

        Category bijouxAnciens = categoryRepository.save(new Category("Bijoux anciens", bijouxEtMontres));
        Category artDeco = categoryRepository.save(new Category("Art déco", bijouxAnciens));
        Category victorien = categoryRepository.save(new Category("Victorien", bijouxAnciens));
        Category edouardien = categoryRepository.save(new Category("Édouardien", bijouxAnciens));

        Category bijouxContemporains = categoryRepository.save(new Category("Bijoux contemporains", bijouxEtMontres));
        Category or = categoryRepository.save(new Category("Or", bijouxContemporains));
        Category diamants = categoryRepository.save(new Category("Diamants", bijouxContemporains));
        Category createursIndependants = categoryRepository.save(new Category("Créateurs indépendants", bijouxContemporains));

        Category montres = categoryRepository.save(new Category("Montres", bijouxEtMontres));
        Category montresLuxe = categoryRepository.save(new Category("Montres de luxe", montres));
        Category montresCollection = categoryRepository.save(new Category("Montres de collection", montres));
        Category montresVintage = categoryRepository.save(new Category("Montres-bracelets vintage", montres));

// Mobilier & Décoration
        Category mobilierDeco = categoryRepository.save(new Category("Mobilier & Décoration", null));

        Category mobilierAncien = categoryRepository.save(new Category("Mobilier ancien", mobilierDeco));
        Category louisXV = categoryRepository.save(new Category("Louis XV", mobilierAncien));
        Category empire = categoryRepository.save(new Category("Empire", mobilierAncien));
        Category regence = categoryRepository.save(new Category("Régence", mobilierAncien));

        Category mobilierDesign = categoryRepository.save(new Category("Mobilier design", mobilierDeco));
        Category annees5070 = categoryRepository.save(new Category("Années 50-70", mobilierDesign));
        Category contemporain = categoryRepository.save(new Category("Contemporain", mobilierDesign));

        Category decoration = categoryRepository.save(new Category("Décoration", mobilierDeco));
        Category luminaires = categoryRepository.save(new Category("Luminaires", decoration));
        Category tapis = categoryRepository.save(new Category("Tapis", decoration));
        Category miroirs = categoryRepository.save(new Category("Miroirs", decoration));

// Véhicules de collection
        Category vehicules = categoryRepository.save(new Category("Véhicules de collection", null));

        Category voitures = categoryRepository.save(new Category("Voitures", vehicules));
        Category avantGuerre = categoryRepository.save(new Category("Avant-guerre", voitures));
        Category classiques = categoryRepository.save(new Category("Classiques (50s–80s)", voitures));
        Category supercars = categoryRepository.save(new Category("Supercars modernes", voitures));

        Category motos = categoryRepository.save(new Category("Motos", vehicules));
        Category vintage = categoryRepository.save(new Category("Vintage", motos));
        Category custom = categoryRepository.save(new Category("Custom", motos));

        Category bateaux = categoryRepository.save(new Category("Bateaux", vehicules));
        Category yachtsAnciens = categoryRepository.save(new Category("Yachts anciens", bateaux));
        Category voiliersClassiques = categoryRepository.save(new Category("Voiliers classiques", bateaux));

// Sport & Loisirs
        Category sportLoisirs = categoryRepository.save(new Category("Sport & Loisirs", null));

        Category memorabilia = categoryRepository.save(new Category("Mémorabilia", sportLoisirs));
        Category maillotsSignes = categoryRepository.save(new Category("Maillots signés", memorabilia));
        Category billetsHistoriques = categoryRepository.save(new Category("Billets historiques", memorabilia));

        Category objetsSport = categoryRepository.save(new Category("Objets de sport", sportLoisirs));
        Category raquettesBallons = categoryRepository.save(new Category("Raquettes, ballons", objetsSport));
        Category equipementEpoque = categoryRepository.save(new Category("Équipement d’époque", objetsSport));

        Category sportsMecaniques = categoryRepository.save(new Category("Sports mécaniques", sportLoisirs));
        Category casques = categoryRepository.save(new Category("Casques", sportsMecaniques));
        Category combinaisons = categoryRepository.save(new Category("Combinaisons", sportsMecaniques));



        Lot peintureAncienne = lotRepository.save(new Lot(
                "Portrait de noble du XVIIIe siècle - Huile sur toile",
                "Superbe portrait d’homme aristocratique. Huile sur toile, cadre d’époque doré à la feuille. Probablement école française vers 1780.",
                "Paris",
                BigDecimal.valueOf(4500),
                List.of(
                        "https://www.proantic.com/galerie/daniel-et-vincent/img/1424421-main-671a81e24e448.jpg",
                        "https://www.proantic.com/galerie/daniel-et-vincent/img/1424421-671a81f88ad8f.jpg",
                        "https://www.proantic.com/galerie/daniel-et-vincent/img/1424421-671a81f8ade1b.jpg",
                        "https://www.proantic.com/galerie/daniel-et-vincent/img/1424421-alb-671a820c35c74.jpg",
                        "https://www.proantic.com/galerie/daniel-et-vincent/img/1424421-alb-671a820ccf555.jpg",
                        "https://www.proantic.com/galerie/daniel-et-vincent/img/1424421-alb-671a820d0876a.jpg"
                ),
                maitresAnciens
        ));

        Lot montreVintage = lotRepository.save(new Lot(
                "Montre Omega Seamaster Vintage 1969",
                "Modèle mécanique à remontage manuel. Cadran patiné, très bon état de fonctionnement. Bracelet cuir neuf.",
                "Genève",
                BigDecimal.valueOf(1850),
                List.of(
                        "https://img.chrono24.com/images/uhren/38322805-g3n2lp57t5qvq44z7bxv6jwq-Zoom.jpg",
                        "https://img.chrono24.com/images/uhren/38322805-mh3273neav0utpt3m24jqiko-ExtraLarge.jpg",
                        "https://img.chrono24.com/images/uhren/38322805-b6i3qzlqz9wiwtwvdpbdimx5-Zoom.jpg",
                        "https://img.chrono24.com/images/uhren/38322805-eb9e2jbrelbjxocznmg8ole9-Zoom.jpg",
                        "https://img.chrono24.com/images/uhren/38322805-ng7ren1k9nmkpatalfca86u4-Zoom.jpg",
                        "https://img.chrono24.com/images/uhren/38322805-omxzoqutszm4v43l1yl7txeb-Zoom.jpg"
                ),
                montresVintage
        ));

        Lot tapisPersan = lotRepository.save(new Lot(
                "Tapis persan fait main - Tabriz 240x170 cm",
                "Tapis en laine noué main, motif floral classique. En excellent état, couleurs vives et naturelles.",
                "Bruxelles",
                BigDecimal.valueOf(2200),
                List.of(
                        "https://cdn.rugvista.net/products/main/543724.jpg?width=2400&quality=75&format=auto",
                        "https://cdn.rugvista.net/products/detail/2029629.jpg?width=2400&quality=75&format=auto",
                        "https://cdn.rugvista.net/products/detail/2029626.jpg?width=2400&quality=75&format=auto ",
                        "https://cdn.rugvista.net/products/detail/2029624.jpg?width=2400&quality=75&format=auto",
                        "https://cdn.rugvista.net/products/detail/2029622.jpg?width=384&quality=75&format=auto"
                ),
                tapis
        ));

        Lot casqueF1 = lotRepository.save(new Lot(
                "Casque intégral Bell porté par pilote F1",
                "Casque Bell authentique, utilisé lors d'une saison de Formule 1. Avec certificat d'authenticité.",
                "Monaco",
                BigDecimal.valueOf(7500),
                List.of(
                        "https://www.artcurial.com/item-images/860922/PICTURE_CATALOG/002.jpg?w=700&force_webp=true",
                        "https://www.artcurial.com/item-images/860923/PICTURE_CATALOG/003.jpg?w=700&force_webp=true",
                        "https://www.artcurial.com/item-images/860924/PICTURE_CATALOG/001.jpg?w=700&force_webp=true",
                        "https://www.artcurial.com/item-images/860925/PICTURE_CATALOG/004.jpg?w=700&force_webp=true",
                        "https://www.artcurial.com/item-images/860926/PICTURE_CATALOG/005.jpg?w=700&force_webp=true"
                ),
                casques
        ));

        Lot diamant = lotRepository.save(new Lot(
                "Bague solitaire en platine avec diamant 1,2 ct",
                "Bague en platine ornée d’un diamant taille brillant, 1,2 carat, certifié GIA. État impeccable.",
                "Lyon",
                BigDecimal.valueOf(9800),
                List.of(
                        "https://www.77diamonds.com/image/57159/photo/white-gold/round/big/-/-/-/-/delicacy-vintage?v=20210629083403640",
                        "https://www.77diamonds.com/image/57162/photo/white-gold/round/big/-/-/-/-/delicacy-vintage?v=20210629083403670",
                        "https://www.77diamonds.com/image/57165/photo/white-gold/round/big/-/-/-/-/delicacy-vintage?v=20210629083403700"
                ),
                diamants
        ));

        Lot sculptureBronze = lotRepository.save(new Lot(
                "Sculpture en bronze - 'Le Penseur' d’après Rodin",
                "Réduction en bronze patiné, signée, d’après l’œuvre célèbre d’Auguste Rodin. Hauteur : 45 cm.",
                "Lille",
                BigDecimal.valueOf(3200),
                List.of(
                        "https://i.ebayimg.com/images/g/MMoAAOSwjX5nkf28/s-l1600.webp",
                        "https://i.ebayimg.com/images/g/Pb4AAOSwe9Jnkf2~/s-l1600.webp",
                        "https://i.ebayimg.com/images/g/gIgAAOSwD59nkf2~/s-l1600.webp"
                ),
                bronze
        ));

        Lot sculptureBois = lotRepository.save(new Lot(
                "Statue africaine en bois - Tribu Fang (Gabon)",
                "Sculpture en bois dur, représentant un ancêtre mythique. Patine ancienne, très belle conservation.",
                "Bruxelles",
                BigDecimal.valueOf(850),
                List.of(
                        "https://www.art-africain.co/contents/media/l_15938-statuette-fetiche-africain-fang-270724.jpg?lmd=29117360",
                        "https://www.art-africain.co/contents/media/l_15939-statuette-fetiche-africain-fang-270724.jpg?lmd=29117361",
                        "https://www.art-africain.co/contents/media/l_15940-statuette-fetiche-africain-fang-270724.jpg?lmd=29117361",
                        "https://www.art-africain.co/contents/media/l_15942-statuette-fetiche-africain-fang-270724.jpg?lmd=29117361",
                        "https://www.art-africain.co/contents/media/l_15943-statuette-fetiche-africain-fang-270724.jpg?lmd=29117361",
                        "https://www.art-africain.co/contents/media/l_15945--statuette-fetiche-africain-fang-270724.jpg?lmd=29117361"
                ),
                bois
        ));

        Lot ivoireChinois = lotRepository.save(new Lot(
                "Okimono japonais en ivoire – XIXe siècle",
                "Personnage en tenue traditionnelle, sculpture fine et expressive. Hauteur : 12 cm. Bon état.",
                "Toulouse",
                BigDecimal.valueOf(1700),
                List.of(
                        "https://www.proantic.com/galerie/mastromauro/img/1543963-main-681dbc2ea1fef.jpg",
                        "https://www.proantic.com/galerie/mastromauro/img/1543963-681dbc5734c3b.jpg",
                        "https://www.proantic.com/galerie/mastromauro/img/1543963-681dbc573e08a.jpg",
                        "https://www.proantic.com/galerie/mastromauro/img/1543963-681dbc5742fae.jpg",
                        "https://www.proantic.com/galerie/mastromauro/img/1543963-alb-681dbc775ca9b.jpg",
                        "https://www.proantic.com/galerie/mastromauro/img/1543963-alb-681dbc7769140.jpg",
                        "https://www.proantic.com/galerie/mastromauro/img/1543963-alb-681dbc7770d1d.jpg"
                ),
                ivoires
        ));

        Lot ceramique = lotRepository.save(new Lot(
                "Plat en faïence de Delft – XVIIIe siècle",
                "Décor bleu cobalt, scène pastorale au centre. Très bon état, petit éclat sur le bord.",
                "Anvers",
                BigDecimal.valueOf(600),
                List.of(
                        "https://www.proantic.com/galerie/annebesnard/img/1484506-main-67a25836e4d29.jpg",
                        "https://www.proantic.com/galerie/annebesnard/img/1484506-67a2588cafd5b.jpg",
                        "https://www.proantic.com/galerie/annebesnard/img/1484506-67a258b3bfbf9.jpg",
                        "https://www.proantic.com/galerie/annebesnard/img/1484506-67a258d5ae053.jpg",
                        "https://www.proantic.com/galerie/annebesnard/img/1484506-alb-67a2590111b22.jpg",
                        "https://www.proantic.com/galerie/annebesnard/img/1484506-alb-67a2590137402.jpg",
                        "https://www.proantic.com/galerie/annebesnard/img/1484506-alb-67a25926cf015.jpg"
                ),
                ceramiques
        ));

        Lot objetReligieux = lotRepository.save(new Lot(
                "Croix processionnelle en argent doré - XVIIe siècle",
                "Croix de procession baroque finement ciselée, d’origine espagnole. Orfèvrerie religieuse ancienne.",
                "Madrid",
                BigDecimal.valueOf(5400),
                List.of(
                        "https://collections.louvre.fr/media/cache/original/0000000021/0000098683/0000176803_OG.JPG",
                        "https://collections.louvre.fr/media/cache/original/0000000021/0000098683/0000176798_OG.JPG",
                        "https://collections.louvre.fr/media/cache/original/0000000021/0000098683/0000176796_OG.JPG",
                        "https://collections.louvre.fr/media/cache/original/0000000021/0000098683/0000176794_OG.JPG",
                        "https://collections.louvre.fr/media/cache/original/0000000021/0000098683/0000176800_OG.JPG"
                ),
                objetsReligieux
        ));

        Lot antiquiteEgyptienne = lotRepository.save(new Lot(
                "Amulette égyptienne - Oushebti en faïence",
                "Figurine funéraire bleue, période Basse Époque (664–332 av. J.-C.). Belle couleur, intacte.",
                "Londres",
                BigDecimal.valueOf(2100),
                List.of(
                        "https://assets.catawiki.com/image/cw_ldp_l/plain/assets/catawiki/assets/2025/2/17/a/e/7/ae7efff3-2efa-4cf2-ade8-84100a2dbdef.jpg@webp",
                        "https://assets.catawiki.com/image/cw_ldp_l/plain/assets/catawiki/assets/2025/2/17/1/d/c/1dcf5816-c222-4837-bff3-ecd17170f545.jpg@webp",
                        "https://assets.catawiki.com/image/cw_ldp_l/plain/assets/catawiki/assets/2025/2/17/d/9/3/d938c285-3e45-4ab1-8992-1035d655d1c3.jpg@webp"
                ),
                egyptiennes
        ));

        Lot antiquiteAsiatique = lotRepository.save(new Lot(
                "Masque tibétain en bois polychrome",
                "Masque rituel bouddhiste, visage de Mahakala. Début XIXe siècle, en bon état de conservation.",
                "Genève",
                BigDecimal.valueOf(1200),
                List.of(
                        "https://a.1stdibscdn.com/19th-century-antique-tibetan-wooden-buddhist-mahakala-mask-with-stand-for-sale/f_57302/f_310952521667297882918/f_31095252_1667297883205_bg_processed.jpg?disable=upscale&auto=webp&quality=60&width=1400",
                        "https://a.1stdibscdn.com/19th-century-antique-tibetan-wooden-buddhist-mahakala-mask-with-stand-for-sale-picture-2/f_57302/f_310952521667297958655/DSC_3285_master.JPG?disable=upscale&auto=webp&quality=60&width=1400",
                        "https://a.1stdibscdn.com/19th-century-antique-tibetan-wooden-buddhist-mahakala-mask-with-stand-for-sale-picture-4/f_57302/f_310952521667297977070/DSC_3289_master.JPG?disable=upscale&auto=webp&quality=60&width=1400",
                        "https://a.1stdibscdn.com/19th-century-antique-tibetan-wooden-buddhist-mahakala-mask-with-stand-for-sale-picture-9/f_57302/f_310952521667298018490/DSC_3292_master.JPG?disable=upscale&auto=webp&quality=60&width=1400",
                        "https://a.1stdibscdn.com/19th-century-antique-tibetan-wooden-buddhist-mahakala-mask-with-stand-for-sale-picture-3/f_57302/f_310952521667297968859/DSC_3288_master.JPG?disable=upscale&auto=webp&quality=60&width=1400"
                ),
                asiatiques
        ));

        Lot antiquiteGrecoRomaine = lotRepository.save(new Lot(
                "Buste romain en marbre blanc - Ier siècle",
                "Tête d’homme barbu, style impérial, fragment ancien en très bon état.",
                "Rome",
                BigDecimal.valueOf(8900),
                List.of(
                        "https://www.anticstore.com/DocBD/commerce/antiquaire/il-quadrifoglio-891/objet/94979/AnticStore-Large-Ref-94979_01.jpg",
                        "https://www.anticstore.com/DocBD/commerce/antiquaire/il-quadrifoglio-891/objet/94979/AnticStore-Large-Ref-94979_03.jpg",
                        "https://www.anticstore.com/DocBD/commerce/antiquaire/il-quadrifoglio-891/objet/94979/AnticStore-Large-Ref-94979_02.jpg"
                ),
                grecoRomaines
        ));

        Lot bijouxArtDeco = lotRepository.save(new Lot(
                "Broche Art Déco en platine et diamants",
                "Création des années 1930. Motif géométrique, sertie de diamants taille ancienne.",
                "Nice",
                BigDecimal.valueOf(3200),
                List.of(
                        "https://i.ebayimg.com/images/g/KPYAAOSwU45gwJwc/s-l1600.webp",
                        "https://i.ebayimg.com/images/g/cEQAAOSweYdgwJwd/s-l1600.webp",
                        "https://i.ebayimg.com/images/g/dIoAAOSwraJgwJwg/s-l1600.webp"
                ),
                artDeco
        ));

        Lot bijouxVictorien = lotRepository.save(new Lot(
                "Collier victorien en or 18k et perles",
                "Collier ancien, fin XIXe, décor floral. Fermoir original. Élégant et raffiné.",
                "Londres",
                BigDecimal.valueOf(2450),
                List.of(
                        "https://www.uhrenschmuck24.ch/media/catalog/product/img/1/image/1080x1080/c/o/collier-mit-anhaenger-750-18-k-gelbgold-mit-diamanten-0-13-ct-h-si-by-christian-1000961714-166429034982.jpg",
                        "https://www.uhrenschmuck24.ch/media/catalog/product/img/1/image/1080x1080/c/o/collier-mit-anhaenger-750-18-k-gelbgold-mit-diamanten-0-13-ct-h-si-by-christian-1000961714-166429034994.jpg"
                ),
                victorien
        ));

        Lot mobilierLouisXV = lotRepository.save(new Lot(
                "Commode Louis XV en marqueterie",
                "Époque XVIIIe, trois tiroirs, ornementations en bronze doré, très belle patine.",
                "Paris",
                BigDecimal.valueOf(6200),
                List.of(
                        "https://cdn20.pamono.com/p/z/1/9/1937963_6w9munjucc/louis-xv-kommode-aus-holz-1.jpg",
                        "https://cdn20.pamono.com/p/g/1/9/1937963_828i2c2g62/louis-xv-kommode-aus-holz-2.jpg",
                        "https://cdn20.pamono.com/p/g/1/9/1937963_ko2yl5qbsp/louis-xv-kommode-aus-holz-3.jpg",
                        "https://cdn20.pamono.com/p/g/1/9/1937963_y3vvcoxsya/louis-xv-kommode-aus-holz-4.jpg",
                        "https://cdn20.pamono.com/p/g/1/9/1937963_hwx2wu7wjn/louis-xv-kommode-aus-holz-5.jpg",
                        "https://cdn20.pamono.com/p/g/1/9/1937963_ge0aagw0qr/louis-xv-kommode-aus-holz-8.jpg"
                ),
                louisXV
        ));

        Lot lampeDesign = lotRepository.save(new Lot(
                "Lampe italienne Stilnovo - Années 60",
                "Lampe sur pied en métal laqué et laiton. Design moderniste, très recherchée.",
                "Milan",
                BigDecimal.valueOf(950),
                List.of(
                        "https://i.etsystatic.com/42655398/r/il/66df6f/5711400826/il_1588xN.5711400826_rez8.jpg",
                        "https://i.etsystatic.com/42655398/r/il/490fa6/5711396946/il_1588xN.5711396946_7i80.jpg",
                        "https://i.etsystatic.com/42655398/r/il/924607/5759460829/il_1588xN.5759460829_bfmq.jpg",
                        "https://i.etsystatic.com/42655398/r/il/fb3f5f/5759460679/il_1588xN.5759460679_qtrp.jpg"
                ),
                luminaires
        ));

        Lot miroirAncien = lotRepository.save(new Lot(
                "Miroir de cheminée Régence en bois doré",
                "Encadrement sculpté, dorure d’origine, miroir au mercure. France, début XVIIIe.",
                "Versailles",
                BigDecimal.valueOf(3700),
                List.of(
                        "https://www.proantic.com/galerie/rocaille/img/1545740-main-6821b6be52ef7.jpg",
                        "https://www.proantic.com/galerie/rocaille/img/1545740-6821b6e120fcd.jpg",
                        "https://www.proantic.com/galerie/rocaille/img/1545740-6821b6e129726.jpg",
                        "https://www.proantic.com/galerie/rocaille/img/1545740-6821b6e12ec9a.jpg",
                        "https://www.proantic.com/galerie/rocaille/img/1545740-alb-6821b70b5407a.jpg",
                        "https://www.proantic.com/galerie/rocaille/img/1545740-alb-6821b70b6cdca.jpg"
                ),
                miroirs
        ));

        Lot voitureAvantGuerre = lotRepository.save(new Lot(
                "Delahaye 135 M Cabriolet – 1939",
                "Voiture française de luxe, moteur 6 cylindres, restaurée. Très rare.",
                "Tours",
                BigDecimal.valueOf(120000),
                List.of(
                        "https://www.artcurial.com/item-images/FR/413/10271413_Vue.jpg?w=700&force_webp=true",
                        "https://www.artcurial.com/item-images/FR/413/10271413_Vue2.jpg?w=700&force_webp=true",
                        "https://www.artcurial.com/item-images/FR/413/10271413_Vue3.jpg?w=700&force_webp=true",
                        "https://www.artcurial.com/item-images/FR/413/10271413_Vue4.jpg?w=700&force_webp=true"
                ),
                avantGuerre
        ));

        Lot motoVintage = lotRepository.save(new Lot(
                "Moto Triumph Bonneville 650 – 1967",
                "Modèle emblématique, restauration complète, carte grise collection.",
                "Manchester",
                BigDecimal.valueOf(14500),
                List.of(
                        "https://assets.carandclassic.com/uploads/cars/triumph/C1872420/1967-triumph-bonneville-650-680cecc5de912.jpg?fit=fillmax&h=1200&ixlib=php-4.1.0&q=85&w=1200&s=bf4a80c35a18a6a8ac36a8f2455823fd",
                        "https://assets.carandclassic.com/uploads/cars/triumph/C1872420/1967-triumph-bonneville-650-680cecc5df4fe.jpg?fit=fillmax&h=800&ixlib=php-4.1.0&q=85&w=800&s=610768556ab7e2d79b382b699f47999c",
                        "https://assets.carandclassic.com/uploads/cars/triumph/C1872420/1967-triumph-bonneville-650-680cecc5def11.jpg?fit=fillmax&h=800&ixlib=php-4.1.0&q=85&w=800&s=3bace5a9f1df71e57b4cef36f061c0e3",
                        "https://assets.carandclassic.com/uploads/cars/triumph/C1872420/1967-triumph-bonneville-650-680cecc5e099d.jpg?fit=fillmax&h=1200&ixlib=php-4.1.0&q=85&w=1200&s=d9c94d9ef13494c3dd25b2a3ef60f4bf",
                        "https://assets.carandclassic.com/uploads/cars/triumph/C1872420/1967-triumph-bonneville-650-680cecc5e04f1.jpg?fit=fillmax&h=800&ixlib=php-4.1.0&q=85&w=800&s=74cc3b56a1b292fe7b4d33505b1d7eeb",
                        "https://assets.carandclassic.com/uploads/cars/triumph/C1872420/1967-triumph-bonneville-650-680cecc5e15f0.jpg?fit=fillmax&h=1200&ixlib=php-4.1.0&q=85&w=1200&s=3008ea0678119906601d0106e5ada435",
                        "https://assets.carandclassic.com/uploads/cars/triumph/C1872420/1967-triumph-bonneville-650-680cecc5e1b59.jpg?fit=fillmax&h=800&ixlib=php-4.1.0&q=85&w=800&s=f2ed49bcd7d6706c8d0035b5e1364510"
                ),
                vintage
        ));

        Lot voilierClassique = lotRepository.save(new Lot(
                "Voilier bois – Sloop de 1962",
                "Carène en acajou verni, gréement classique, entièrement restauré. Naviguant.",
                "La Rochelle",
                BigDecimal.valueOf(32000),
                List.of(
                        "https://static.b24.co/fotos/xlarge/602427-c0d0a1a72f64127f722c439f551552d7-x-6274919-84b9c8b6b3f0ab8014fdd7561e7ea193.webp",
                        "https://static.b24.co/fotos/xlarge/602427-be55c6aeae2979c4321e5e8b44466406-x-6274911-66f849d1d07b7398082a5b1f726a5d51.webp",
                        "https://static.b24.co/fotos/xlarge/602427-82ea6c7198104e20986c3c5a667e376f-x-6274913-dff901ac07c15cd57cbcf94de03e549b.webp",
                        "https://static.b24.co/fotos/xlarge/602427-e7222e4a08b173bae66a4ee755a9c96c-x-6274907-f3de116f4ab4f8c7327d3c3b89b00bbd.webp",
                        "https://static.b24.co/fotos/xlarge/602427-441be33c89436026cc08c10c863cc4fd-x-6274908-2711937507e7bf9d20ade08103bc16c3.webp",
                        "https://static.b24.co/fotos/xlarge/602427-8541e7d885a02959617bf10c921ca443-x-6274926-e238d0b88115c1f73f4ac0a3cfc79a49.webp",
                        "https://static.b24.co/fotos/xlarge/602427-ed541380b61abd4c7e7ae00ecbc77bf0-x-6278211-12304f3f9410a5cb894937076c777df7.webp"
                ),
                voiliersClassiques
        ));

        Lot maillotSigne = lotRepository.save(new Lot(
                "Maillot signé par Zinédine Zidane – France 98",
                "Maillot original Adidas avec signature authentifiée. Cadre d’exposition inclus.",
                "Marseille",
                BigDecimal.valueOf(2500),
                List.of(
                    "https://static.wixstatic.com/media/577107_4f75f11a53ad45b28762baa02378caaf~mv2.webp/v1/fill/w_611,h_625,al_c,q_85,usm_0.66_1.00_0.01,enc_avif,quality_auto/577107_4f75f11a53ad45b28762baa02378caaf~mv2.webp",
                        "https://static.wixstatic.com/media/577107_0c519ddaaccb4460a0fbe3bf06eeb77f~mv2.webp/v1/fill/w_611,h_625,al_c,q_85,usm_0.66_1.00_0.01,enc_avif,quality_auto/577107_0c519ddaaccb4460a0fbe3bf06eeb77f~mv2.webp",
                        "https://static.wixstatic.com/media/577107_6dbfbc0791f542099e3fa4ba4e63ea36~mv2.webp/v1/fill/w_611,h_625,al_c,q_85,usm_0.66_1.00_0.01,enc_avif,quality_auto/577107_6dbfbc0791f542099e3fa4ba4e63ea36~mv2.webp"
                ),
                maillotsSignes
        ));

        Lot billetHistorique = lotRepository.save(new Lot(
                "Billet du match final Coupe du Monde 1958",
                "Billet original du match Suède – Brésil (Pelé). État excellent, rare.",
                "Stockholm",
                BigDecimal.valueOf(850),
                List.of(
                        "https://i.ebayimg.com/images/g/JOEAAeSwtXBnoitJ/s-l960.webp",
                        "https://i.ebayimg.com/images/g/KTEAAeSwifVnoitJ/s-l960.webp",
                        "https://i.ebayimg.com/images/g/rp8AAeSwLgpnrKv9/s-l960.webp",
                        "https://i.ebayimg.com/images/g/kKAAAeSwKTpnoitK/s-l960.webp",
                        "https://i.ebayimg.com/images/g/dDgAAeSwTNdnoitK/s-l960.webp"
                ),
                billetsHistoriques
        ));

        Lot raquetteTennis = lotRepository.save(new Lot(
                "Raquette de tennis en bois – années 60",
                "Raquette Dunlop Maxply Fort, utilisée en compétition. Bel état de conservation.",
                "Liège",
                BigDecimal.valueOf(230),
                List.of(
                        "https://alarecherchedumeilleur.com/wp-content/uploads/2020/12/ancienne-raquette-tennis-vintage-04.jpg",
                        "https://alarecherchedumeilleur.com/wp-content/uploads/2020/12/ancienne-raquette-tennis-vintage-02.jpg",
                        "https://alarecherchedumeilleur.com/wp-content/uploads/2020/12/ancienne-raquette-tennis-vintage-03-700x700.jpg",
                        "https://alarecherchedumeilleur.com/wp-content/uploads/2020/12/ancienne-raquette-tennis-vintage-06-700x667.jpg",
                        "https://alarecherchedumeilleur.com/wp-content/uploads/2020/12/IMG_5142-scaled.jpeg"
                ),
                raquettesBallons
        ));

        Lot combinaisonRallye = lotRepository.save(new Lot(
                "Combinaison de rallye – signée par Sébastien Loeb",
                "Combinaison officielle Citroën Racing, utilisée en WRC, avec signature et certificat.",
                "Strasbourg",
                BigDecimal.valueOf(3800),
                List.of(
                        "https://i.ebayimg.com/images/g/n7kAAOSw591m6aEn/s-l1600.webp",
                        "https://i.ebayimg.com/images/g/gTAAAOSw69pm6aEn/s-l1600.webp",
                        "https://i.ebayimg.com/images/g/lkwAAOSwZQBm6aEk/s-l1600.webp",
                        "https://i.ebayimg.com/images/g/BxQAAOSwgbhm6aEo/s-l1600.webp",
                        "https://i.ebayimg.com/images/g/ejMAAOSwWYNm6aEf/s-l1600.webp"
                ),
                combinaisons
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
