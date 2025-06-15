package ch.zucchinit.zauction.Lot;

import ch.zucchinit.zauction.Utils.S3Connector;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

@Service
public class LotMediaService {
    private final LotRepository lotRepository;
    private final S3Connector s3Connector;


    public LotMediaService(LotRepository lotRepository, S3Connector s3Connector) {
        this.lotRepository = lotRepository;
        this.s3Connector = s3Connector;
    }


    public Lot insertLotMedias(Lot lot, Map<Integer, MultipartFile> files) throws IOException {
        Map<String, byte[]> newMediasMap = new HashMap<>();

        for (Map.Entry<Integer, MultipartFile> entry : files.entrySet()) {
            MultipartFile file = entry.getValue();
            newMediasMap.put(getMediaName(lot, file), entry.getValue().getBytes());
        }

        lot.setMedias(s3Connector.uploadFiles(newMediasMap));
        return lotRepository.save(lot);
    }

    public Lot updateLotMedias(Lot lot, List<LotDTO.LotMediaAction> metas, List<MultipartFile> medias) throws IOException {
        List<String> currentMedias = new ArrayList<>(lot.getMedias());
        Map<String, byte[]> toUpload = new HashMap<>();
        List<String> toDelete = new ArrayList<>();
        int mediaAddIndex = 0;

        List<LotDTO.LotMediaAction> deletes = new ArrayList<>();
        List<LotDTO.LotMediaAction> adds = new ArrayList<>();
        List<LotDTO.LotMediaAction> moves = new ArrayList<>();

        for (LotDTO.LotMediaAction meta : metas) {
            switch (meta.action()) {
                case "delete": deletes.add(meta); break;
                case "add": adds.add(meta); break;
                case "move": moves.add(meta); break;
            }
        }

        deletes.sort(Comparator.comparingInt(LotDTO.LotMediaAction::to).reversed());
        for (LotDTO.LotMediaAction meta : deletes) {
            int to = meta.to();
            String deleted = currentMedias.remove(to);
            toDelete.add(deleted);
        }

        adds.sort(Comparator.comparingInt(LotDTO.LotMediaAction::to));
        int addOffset = 0;
        for (LotDTO.LotMediaAction meta : adds) {
            int to = meta.to() + addOffset;
            MultipartFile file = medias.get(mediaAddIndex++);
            String mediaName = getMediaName(lot, file);
            currentMedias.add(to, mediaName);
            toUpload.put(mediaName, file.getBytes());
            addOffset++;
        }

        for (LotDTO.LotMediaAction meta : moves) {
            int from = meta.from();
            int to = meta.to();
            if (from == to) continue;
            String moved = currentMedias.remove(from);
            if (to > from) to--;
            currentMedias.add(to, moved);
        }

        if (!toUpload.isEmpty()) s3Connector.uploadFiles(toUpload);
        if (!toDelete.isEmpty()) s3Connector.deleteFiles(toDelete);

        lot.setMedias(currentMedias);
        return lotRepository.save(lot);
    }


    private String getMediaName(Lot lot, MultipartFile file) {
        String extension = s3Connector.getFileExtension(file.getOriginalFilename());
        return lot.getId() + "_" + UUID.randomUUID() + extension;
    }
}
