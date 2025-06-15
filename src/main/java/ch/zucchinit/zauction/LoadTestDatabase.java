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
        User jonSnow = userRepository.save(new User(
                "Jon",
                "Snow",
                "jon.snow@zauction.ch",
                "$2a$10$XoMOGkh2.i.CP.pEsf5Pgu36cNPD7havdQmBaioeFj15i4n/P4N8y"
        ));
        jonSnow.setBalance(BigDecimal.valueOf(10_000_000));

        User nedStark = userRepository.save(new User(
                "Ned",
                "Stark",
                "ned.stark@zauction.ch",
                "$2a$10$XoMOGkh2.i.CP.pEsf5Pgu36cNPD7havdQmBaioeFj15i4n/P4N8y"
        ));
        nedStark.setBalance(BigDecimal.valueOf(10_000_000));

        User daenerysTargaryen = userRepository.save(new User(
                "Daenerys",
                "Targaryen",
                "daenerys.targaryen@zauction.ch",
                "$2a$10$XoMOGkh2.i.CP.pEsf5Pgu36cNPD7havdQmBaioeFj15i4n/P4N8y"
        ));
        daenerysTargaryen.setBalance(BigDecimal.valueOf(10_000_000));

        Category artEtAntiquites = categoryRepository.save(new Category("Art & Antiquités", null));
        Category peintures = categoryRepository.save(new Category("Peintures", artEtAntiquites));
        Category maitresAnciens = categoryRepository.save(new Category("Maîtres anciens", peintures));
        categoryRepository.save(new Category("Art moderne", peintures));
        categoryRepository.save(new Category("Art contemporain", peintures));

        Category sculptures = categoryRepository.save(new Category("Sculptures", artEtAntiquites));
        Category bronze = categoryRepository.save(new Category("Bronze", sculptures));
        Category bois = categoryRepository.save(new Category("Bois", sculptures));
        categoryRepository.save(new Category("Marbre", sculptures));

        Category objetsArt = categoryRepository.save(new Category("Objets d’art", artEtAntiquites));
        Category ceramiques = categoryRepository.save(new Category("Céramiques", objetsArt));
        Category ivoires = categoryRepository.save(new Category("Ivoires", objetsArt));
        Category objetsReligieux = categoryRepository.save(new Category("Objets religieux", objetsArt));

        Category antiquites = categoryRepository.save(new Category("Antiquités", artEtAntiquites));
        Category egyptiennes = categoryRepository.save(new Category("Égyptiennes", antiquites));
        Category asiatiques = categoryRepository.save(new Category("Asiatiques", antiquites));
        Category grecoRomaines = categoryRepository.save(new Category("Gréco-romaines", antiquites));

        Category bijouxEtMontres = categoryRepository.save(new Category("Bijoux & Montres", null));
        Category bijouxAnciens = categoryRepository.save(new Category("Bijoux anciens", bijouxEtMontres));
        Category artDeco = categoryRepository.save(new Category("Art déco", bijouxAnciens));
        Category victorien = categoryRepository.save(new Category("Victorien", bijouxAnciens));
        categoryRepository.save(new Category("Édouardien", bijouxAnciens));

        Category bijouxContemporains = categoryRepository.save(new Category("Bijoux contemporains", bijouxEtMontres));
        Category diamants = categoryRepository.save(new Category("Diamants", bijouxContemporains));
        categoryRepository.save(new Category("Or", bijouxContemporains));
        categoryRepository.save(new Category("Créateurs indépendants", bijouxContemporains));

        Category montres = categoryRepository.save(new Category("Montres", bijouxEtMontres));
        Category montresVintage = categoryRepository.save(new Category("Montres-bracelets vintage", montres));
        categoryRepository.save(new Category("Montres de luxe", montres));
        categoryRepository.save(new Category("Montres de collection", montres));

        Category mobilierDeco = categoryRepository.save(new Category("Mobilier & Décoration", null));

        Category mobilierAncien = categoryRepository.save(new Category("Mobilier ancien", mobilierDeco));
        Category louisXV = categoryRepository.save(new Category("Louis XV", mobilierAncien));
        categoryRepository.save(new Category("Empire", mobilierAncien));
        categoryRepository.save(new Category("Régence", mobilierAncien));

        Category mobilierDesign = categoryRepository.save(new Category("Mobilier design", mobilierDeco));
        categoryRepository.save(new Category("Années 50-70", mobilierDesign));
        categoryRepository.save(new Category("Contemporain", mobilierDesign));

        Category decoration = categoryRepository.save(new Category("Décoration", mobilierDeco));
        Category luminaires = categoryRepository.save(new Category("Luminaires", decoration));
        Category tapis = categoryRepository.save(new Category("Tapis", decoration));
        Category miroirs = categoryRepository.save(new Category("Miroirs", decoration));

        Category vehicules = categoryRepository.save(new Category("Véhicules de collection", null));
        Category voitures = categoryRepository.save(new Category("Voitures", vehicules));
        Category avantGuerre = categoryRepository.save(new Category("Avant-guerre", voitures));
        categoryRepository.save(new Category("Classiques (50s–80s)", voitures));
        categoryRepository.save(new Category("Supercars modernes", voitures));

        Category motos = categoryRepository.save(new Category("Motos", vehicules));
        Category vintage = categoryRepository.save(new Category("Vintage", motos));
        categoryRepository.save(new Category("Custom", motos));

        Category bateaux = categoryRepository.save(new Category("Bateaux", vehicules));
        Category voiliersClassiques = categoryRepository.save(new Category("Voiliers classiques", bateaux));
        categoryRepository.save(new Category("Yachts anciens", bateaux));

        Category sportLoisirs = categoryRepository.save(new Category("Sport & Loisirs", null));
        Category memorabilia = categoryRepository.save(new Category("Mémorabilia", sportLoisirs));
        Category maillotsSignes = categoryRepository.save(new Category("Maillots signés", memorabilia));
        Category billetsHistoriques = categoryRepository.save(new Category("Billets historiques", memorabilia));

        Category objetsSport = categoryRepository.save(new Category("Objets de sport", sportLoisirs));
        Category raquettesBallons = categoryRepository.save(new Category("Raquettes, ballons", objetsSport));
        categoryRepository.save(new Category("Équipement d’époque", objetsSport));

        Category sportsMecaniques = categoryRepository.save(new Category("Sports mécaniques", sportLoisirs));
        Category casques = categoryRepository.save(new Category("Casques", sportsMecaniques));
        Category combinaisons = categoryRepository.save(new Category("Combinaisons", sportsMecaniques));

        lotRepository.save(new Lot(
                "Portrait de noble du XVIIIe siècle - Huile sur toile",
                "Superbe portrait d’homme aristocratique. Huile sur toile, cadre d’époque doré à la feuille. Probablement école française vers 1780.",
                "Paris",
                BigDecimal.valueOf(4500),
                List.of(
                        "1_761b8d44-0bbc-46a3-806e-bdaa131d9aea.jpg",
                        "1_5d5dbdf7-84de-4a0e-8d99-2d1fdab57dd9.jpg",
                        "1_2956fe82-9a60-4cc6-b79b-f090ae371544.jpg",
                        "1_890a75a9-319b-4a10-8479-9420d736e9fa.jpg",
                        "1_fa2d5641-9f79-4326-bd3e-758920ba3c67.jpg",
                        "1_8841cb76-8db7-443c-9ba3-65ba3d630b9a.jpg"
                ),
                maitresAnciens,
                jonSnow
        ));

        lotRepository.save(new Lot(
                "Montre Omega Seamaster Vintage 1969",
                "Modèle mécanique à remontage manuel. Cadran patiné, très bon état de fonctionnement. Bracelet cuir neuf.",
                "Genève",
                BigDecimal.valueOf(1850),
                List.of(
                        "2_f2510e60-8191-460e-b812-8551e9644538.jpg",
                        "2_18739827-f7d5-4da3-a0bb-1213996828d1.jpg",
                        "2_72ab712d-9221-4b9d-88e2-1667e3f013ce.jpg",
                        "2_93b4847b-d0bb-418f-a221-5cafc6c4f452.jpg",
                        "2_3cc1924b-b251-4b4f-a461-65b974568288.jpg",
                        "2_6fe99ff3-fd3d-47d0-bd7c-b30e296806ea.jpg"
                ),
                montresVintage,
                jonSnow
        ));

        lotRepository.save(new Lot(
                "Tapis persan fait main - Tabriz 240x170 cm",
                "Tapis en laine noué main, motif floral classique. En excellent état, couleurs vives et naturelles.",
                "Bruxelles",
                BigDecimal.valueOf(2200),
                List.of(
                        "3_73eeb684-2e51-4b4a-88cb-acf58a1e47dd.jpg",
                        "3_4a92a71f-859a-40cf-bd05-89bb83de1e8f.jpg",
                        "3_3959b146-e1f5-4a0b-879f-4ebe880ff5f8.jpg",
                        "3_15bdfe8a-b6d8-4e36-92b2-45dc4a6e144c.jpg",
                        "3_0b7b6c21-faff-4248-b29a-556b05a413cf.jpg"
                ),
                tapis,
                jonSnow
        ));

        lotRepository.save(new Lot(
                "Casque intégral Bell porté par pilote F1",
                "Casque Bell authentique, utilisé lors d'une saison de Formule 1. Avec certificat d'authenticité.",
                "Monaco",
                BigDecimal.valueOf(7500),
                List.of(
                        "4_7f2f3cdb-3485-4257-a4b7-fd18176dd770.webp",
                        "4_1caed680-248e-4f40-b632-56ada9206528.webp",
                        "4_6e891750-7b53-4011-bd36-083afdef0449.webp",
                        "4_1a8ef33f-028a-4d66-baec-60bc12a98f50.webp",
                        "4_6f6232f7-5b0a-40d5-b84d-5e9d5f9a894c.webp"
                ),
                casques,
                jonSnow
        ));

        lotRepository.save(new Lot(
                "Bague solitaire en platine avec diamant 1,2 ct",
                "Bague en platine ornée d’un diamant taille brillant, 1,2 carat, certifié GIA. État impeccable.",
                "Lyon",
                BigDecimal.valueOf(9800),
                List.of(
                        "5_bbe3ec77-218a-4975-b1ef-2ccb2c64409d.jpg",
                        "5_1cb22591-f16a-4ed3-8744-0147c6a70798.jpg",
                        "5_1afa2000-d482-42aa-bc52-45aef515eeee.jpg"
                ),
                diamants,
                jonSnow
        ));

        lotRepository.save(new Lot(
                "Sculpture en bronze - 'Le Penseur' d’après Rodin",
                "Réduction en bronze patiné, signée, d’après l’œuvre célèbre d’Auguste Rodin. Hauteur : 45 cm.",
                "Lille",
                BigDecimal.valueOf(3200),
                List.of(
                        "6_d0b86164-9192-4df0-9c56-4ff491352e6d.webp",
                        "6_4347facd-7df6-4734-bc48-a590d8dc4215.webp",
                        "6_f2be7a43-9996-4330-851b-e6d3536bf600.webp"
                ),
                bronze,
                jonSnow
        ));

        lotRepository.save(new Lot(
                "Statue africaine en bois - Tribu Fang (Gabon)",
                "Sculpture en bois dur, représentant un ancêtre mythique. Patine ancienne, très belle conservation.",
                "Bruxelles",
                BigDecimal.valueOf(850),
                List.of(
                        "7_b267d2c5-58d3-4b5b-8817-12289b5d737b.jpg",
                        "7_830b23ad-16d8-402d-ad9f-fb9267f87c5b.jpg",
                        "7_249e6cb6-bd71-44c4-b414-cb5389c2de0c.jpg",
                        "7_ef5e69be-9eef-4a49-b5b2-63839e242e79.jpg",
                        "7_b481c360-cb54-41c9-a07f-a029ca6d4c9a.jpg",
                        "7_efa5a70e-eae1-451e-93b6-6ffbb2ffbf8c.jpg"
                ),
                bois,
                jonSnow
        ));

        lotRepository.save(new Lot(
                "Okimono japonais en ivoire – XIXe siècle",
                "Personnage en tenue traditionnelle, sculpture fine et expressive. Hauteur : 12 cm. Bon état.",
                "Toulouse",
                BigDecimal.valueOf(1700),
                List.of(
                        "8_ba1593e9-8579-49c2-a1a4-bd9f0c6bad42.jpg",
                        "8_6f3dd766-2a83-4624-9796-d73d4de0a999.jpg",
                        "8_45daef7c-8c17-480b-92a6-8672d72db30f.jpg",
                        "8_13b7f318-45be-4c1a-8568-cb954e7e555e.jpg",
                        "8_c474c727-8293-4a3f-b42b-49364296cc21.jpg",
                        "8_b5851c8c-c8ff-4008-9b92-7fc83c73da5e.jpg",
                        "8_14180986-f90a-4859-be9d-b1823a304671.jpg"
                ),
                ivoires,
                jonSnow
        ));

        lotRepository.save(new Lot(
                "Plat en faïence de Delft – XVIIIe siècle",
                "Décor bleu cobalt, scène pastorale au centre. Très bon état, petit éclat sur le bord.",
                "Anvers",
                BigDecimal.valueOf(600),
                List.of(
                        "9_dc7a22c0-ebc8-40fe-b2a8-555b854cb467.jpg",
                        "9_49c6f356-8585-43e4-b783-80bbfd0e8eee.jpg",
                        "9_bdaf5c3a-7407-4e68-a6b3-afdac5c94fcf.jpg",
                        "9_30f5b2a8-735c-4663-b707-f4a4659957cc.jpg",
                        "9_495db86c-dd49-4bf3-a0df-26ee7d579a3f.jpg",
                        "9_77e4f672-4ba5-4eff-8b90-7d9c02ca5aa1.jpg",
                        "9_139667b2-f25f-432b-a4a8-8f65d8d9ceae.jpg"
                ),
                ceramiques,
                jonSnow
        ));

        lotRepository.save(new Lot(
                "Croix processionnelle en argent doré - XVIIe siècle",
                "Croix de procession baroque finement ciselée, d’origine espagnole. Orfèvrerie religieuse ancienne.",
                "Madrid",
                BigDecimal.valueOf(5400),
                List.of(
                        "10_326e12d3-28e8-496a-bfbe-d522f7c7377e.jpg",
                        "10_4ad8ec19-6dce-480a-90fd-fd221a407431.jpg",
                        "10_07f1a0fb-ca16-4486-bdb1-0730e0b987c9.jpg",
                        "10_1421b6bb-c78a-4050-9712-cbadcf5bc1e6.jpg",
                        "10_4f77528c-cbeb-4834-92ce-2feaa1f7fa85.jpg"
                ),
                objetsReligieux,
                jonSnow
        ));

        lotRepository.save(new Lot(
                "Amulette égyptienne - Oushebti en faïence",
                "Figurine funéraire bleue, période Basse Époque (664–332 av. J.-C.). Belle couleur, intacte.",
                "Londres",
                BigDecimal.valueOf(2100),
                List.of(
                        "11_9e3922c3-cb81-4aca-9806-abd81dde2320.webp",
                        "11_699c58a5-31c1-4a4e-97d0-ab501403bdea.webp",
                        "11_b486dff9-3fa4-4a76-8f21-6f6e29b8b9f7.webp"
                ),
                egyptiennes,
                jonSnow
        ));

        lotRepository.save(new Lot(
                "Masque tibétain en bois polychrome",
                "Masque rituel bouddhiste, visage de Mahakala. Début XIXe siècle, en bon état de conservation.",
                "Genève",
                BigDecimal.valueOf(1200),
                List.of(
                        "12_1308fe86-c32d-47af-98c2-1d976d4a178e.jpg",
                        "12_36a18888-0d82-47a3-ac63-c8cbdce5f7d0.jpg",
                        "12_e5ac8ae9-f558-4c74-8a19-1df3f36e421f.jpg",
                        "12_0ec1f80c-f111-4227-86de-96017fe33651.jpg",
                        "12_6f10ef3c-8fc1-4112-82f5-61769cddd0cf.jpg"
                ),
                asiatiques,
                jonSnow
        ));

        lotRepository.save(new Lot(
                "Buste romain en marbre blanc - Ier siècle",
                "Tête d’homme barbu, style impérial, fragment ancien en très bon état.",
                "Rome",
                BigDecimal.valueOf(8900),
                List.of(
                        "13_35294ec9-d4eb-49e1-a36a-5ef6ab1c7e6c.jpg",
                        "13_c8818d64-39aa-4771-9ba5-3ac20d5aed2c.jpg",
                        "13_b0cafc7d-71b9-4af2-afdb-f21d85437b9f.jpg"
                ),
                grecoRomaines,
                jonSnow
        ));

        lotRepository.save(new Lot(
                "Broche Art Déco en platine et diamants",
                "Création des années 1930. Motif géométrique, sertie de diamants taille ancienne.",
                "Nice",
                BigDecimal.valueOf(3200),
                List.of(
                        "14_9be80ff5-0482-477f-88ed-be6f21d43e77.webp",
                        "14_69cbce7d-50b6-4b24-bd8b-f4ab22a3121e.webp",
                        "14_7796e050-1657-4712-a21c-51db7aac1bc8.webp"
                ),
                artDeco,
                jonSnow
        ));

        lotRepository.save(new Lot(
                "Collier victorien en or 18k et perles",
                "Collier ancien, fin XIXe, décor floral. Fermoir original. Élégant et raffiné.",
                "Londres",
                BigDecimal.valueOf(2450),
                List.of(
                        "15_964a90cb-94b4-4bbf-ac2e-e2f41e98ac4e.jpg",
                        "15_34620fc7-90a5-4556-86b7-c24dad5d8bf9.jpg"
                ),
                victorien,
                jonSnow
        ));

        lotRepository.save(new Lot(
                "Commode Louis XV en marqueterie",
                "Époque XVIIIe, trois tiroirs, ornementations en bronze doré, très belle patine.",
                "Paris",
                BigDecimal.valueOf(6200),
                List.of(
                        "16_fe3bd541-6205-4f78-a1b2-a1bf58685fea.jpg",
                        "16_e17adf96-9cf9-4603-aac8-60da40a9942c.jpg",
                        "16_9a882051-2813-4f36-a418-872e9ec8756d.jpg",
                        "16_4934ec30-78d8-4926-9be4-663ec1bce828.jpg",
                        "16_072111b4-5d68-4eb9-b6fb-524875127dec.jpg",
                        "16_33404f4e-6e31-4c44-85bc-c3cf3f372975.jpg"
                ),
                louisXV,
                jonSnow
        ));

        lotRepository.save(new Lot(
                "Lampe italienne Stilnovo - Années 60",
                "Lampe sur pied en métal laqué et laiton. Design moderniste, très recherchée.",
                "Milan",
                BigDecimal.valueOf(950),
                List.of(
                        "17_80fee6da-5d17-46b5-b8e5-72de5f4edc01.jpg",
                        "17_4c09a347-bf7e-4db0-b7b4-454310e3d59b.jpg",
                        "17_0462f76b-d7b8-41db-bead-70d13a071219.jpg",
                        "17_639733bf-1ae8-465b-a6c8-160b0ec48a6e.jpg"
                ),
                luminaires,
                jonSnow
        ));

        lotRepository.save(new Lot(
                "Miroir de cheminée Régence en bois doré",
                "Encadrement sculpté, dorure d’origine, miroir au mercure. France, début XVIIIe.",
                "Versailles",
                BigDecimal.valueOf(3700),
                List.of(
                        "18_0063ddaf-e89d-4643-b3f3-acffa389dd82.jpg",
                        "18_f5b7814a-e1ef-4bc2-a927-2672863ea8d6.jpg",
                        "18_ad260ec8-a61a-487f-b1b7-54ea944170d6.jpg",
                        "18_3a518bc9-dafc-4541-9b43-9d8bffd39d59.jpg",
                        "18_2cbd46a2-6f96-4d96-a008-0f56ead6c390.jpg",
                        "18_05185a5a-7c88-44c9-91fd-2eaa3bba4298.jpg"
                ),
                miroirs,
                jonSnow
        ));

        lotRepository.save(new Lot(
                "Delahaye 135 M Cabriolet – 1939",
                "Voiture française de luxe, moteur 6 cylindres, restaurée. Très rare.",
                "Tours",
                BigDecimal.valueOf(120000),
                List.of(
                        "19_5a4d97e0-49aa-474f-b906-6f7c9c45be35.webp",
                        "19_8fdff3bf-27ce-4e56-aa18-6e08b528be60.webp",
                        "19_56db6e7b-ff8f-4bca-bb0e-5d3127e86de4.webp",
                        "19_74ff0df5-00c7-4905-9095-9c4714d9c6fc.webp"
                ),
                avantGuerre,
                jonSnow
        ));

        lotRepository.save(new Lot(
                "Moto Triumph Bonneville 650 – 1967",
                "Modèle emblématique, restauration complète, carte grise collection.",
                "Manchester",
                BigDecimal.valueOf(14500),
                List.of(
                        "20_e8645312-ec6b-4229-bd62-21e4fd75f19f.jpg",
                        "20_12716082-f41a-474c-9187-318f989378a7.jpg",
                        "20_31f50bc6-7549-4dce-ac7f-d42bc8d529a3.jpg",
                        "20_c8db0b84-935e-4df7-a9d8-587addd31936.jpg",
                        "20_fed3fd8c-efdb-4f5b-8dc8-9880517ad79f.jpg",
                        "20_9093e73d-fc82-4820-9b59-5257acb00649.jpg",
                        "20_121b9739-7cf1-450f-ad0f-58f791b038c8.jpg"
                ),
                vintage,
                jonSnow
        ));

        lotRepository.save(new Lot(
                "Voilier bois – Sloop de 1962",
                "Carène en acajou verni, gréement classique, entièrement restauré. Naviguant.",
                "La Rochelle",
                BigDecimal.valueOf(32000),
                List.of(
                        "21_c4df6297-cf07-442d-90c2-f5906c2d2d70.webp",
                        "21_d4e449b6-c294-4f33-a840-ee132551f0fb.webp",
                        "21_6be8b9f8-0c9a-447a-9ebb-0ad93d78894c.webp",
                        "21_b26363a1-04a1-4cbe-89cd-18ac3d5dba25.webp",
                        "21_a6819a03-b443-4e14-b34c-d62b7c768276.webp",
                        "21_b4bf3ef4-6c1d-45bc-9f88-27700fd1fa15.webp",
                        "21_7338f1d3-24d2-4b83-ba43-b515cd276027.webp"
                ),
                voiliersClassiques,
                jonSnow
        ));

        lotRepository.save(new Lot(
                "Maillot signé par Zinédine Zidane – France 98",
                "Maillot original Adidas avec signature authentifiée. Cadre d’exposition inclus.",
                "Marseille",
                BigDecimal.valueOf(2500),
                List.of(
                        "22_2474700f-6111-49d3-84b8-a75ab0db5fba.webp",
                        "22_fe0328fb-5edf-4772-b958-5e8c619784f1.webp",
                        "22_1aeb703d-7b48-4644-835b-332bc06da108.webp"
                ),
                maillotsSignes,
                nedStark
        ));

        lotRepository.save(new Lot(
                "Billet du match final Coupe du Monde 1958",
                "Billet original du match Suède – Brésil (Pelé). État excellent, rare.",
                "Stockholm",
                BigDecimal.valueOf(850),
                List.of(
                        "23_b7292255-2b3a-4f42-9974-81f18831db52.webp",
                        "23_078435c5-c570-47a4-a050-80c3c17f8b3f.webp",
                        "23_74c013a4-dbe0-4f20-8e8c-35d2d857085b.webp",
                        "23_d1b5b9a0-487d-4fed-a010-def74dfd5908.webp",
                        "23_aff45a54-b05d-43ff-880c-d58d5e6d9ec4.webp"
                ),
                billetsHistoriques,
                nedStark
        ));

        lotRepository.save(new Lot(
                "Raquette de tennis en bois – années 60",
                "Raquette Dunlop Maxply Fort, utilisée en compétition. Bel état de conservation.",
                "Liège",
                BigDecimal.valueOf(230),
                List.of(
                        "24_b26a9833-e3d0-434a-9dce-7b0f56e1706b.jpg",
                        "24_7e9feb46-3810-4a28-b9b9-5c4e9a3c49aa.jpg",
                        "24_3a3bf1db-eb3a-414d-8307-6ebbe41d0692.jpg",
                        "24_a13c74c4-5ceb-4d30-b5da-dffd04b4cda1.jpg",
                        "24_d9ea5746-6fde-4279-9f03-97e200055e2e.jpg"
                ),
                raquettesBallons,
                nedStark
        ));

        lotRepository.save(new Lot(
                "Combinaison de rallye – signée par Sébastien Loeb",
                "Combinaison officielle Citroën Racing, utilisée en WRC, avec signature et certificat.",
                "Strasbourg",
                BigDecimal.valueOf(3800),
                List.of(
                        "25_abbc3472-121d-4c0f-b0fe-59381fb5253d.webp",
                        "25_a229e035-467b-4e22-a8ed-21e8a6bf266d.webp",
                        "25_ed4eff26-80ef-4fb5-b6c0-e03996c0157d.webp",
                        "25_d15d6a7f-0515-4612-8d75-3ddf4c34fd72.webp",
                        "25_8b59bd0d-dc65-41ff-8bbe-a66bf767ebed.webp"
                ),
                combinaisons,
                nedStark
        ));

        List<Lot> allLots = lotRepository.findAll();
        allLots.forEach(lot -> lot.setPublishDate(LocalDateTime.now().minusDays(100)));
        lotRepository.saveAllAndFlush(allLots);

        List<User> users = List.of(jonSnow, nedStark, daenerysTargaryen);
        generateChronologicalAuctions(allLots, auctionRepository, users);
        userRepository.saveAll(users);
    }

    public void generateChronologicalAuctions(List<Lot> lots, AuctionRepository auctionRepository, List<User> users) {
        Random random = new Random();

        for (Lot lot : lots) {
            int auctionCount = 10 + random.nextInt(241);
            BigDecimal basePrice = lot.getInitialPrice();
            BigDecimal currentPrice = basePrice;
            LocalDateTime now = LocalDateTime.now();

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

            auctionDates.sort(Comparator.naturalOrder());
            for (LocalDateTime auctionDate : auctionDates) {
                BigDecimal increment = BigDecimal.valueOf(Math.floor((random.nextDouble() * 0.1 + 0.01) * basePrice.doubleValue()));
                currentPrice = currentPrice.add(increment);

                List<User> buyerUsers = new ArrayList<>(users.stream().filter(u -> !u.isSame(lot.getSellerUser())).toList());
                Collections.shuffle(buyerUsers);
                User user = buyerUsers.get(0);

                Auction auction = new Auction(currentPrice, auctionDate, lot, user);
                auctionRepository.saveAndFlush(auction);
            }

            lot.setLastPrice(currentPrice);
            lotRepository.saveAndFlush(lot);
        }
    }
}