package com.api.smartfarm.image;

import java.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TomatoSummaryService {

    @Autowired
    private TomatoSummaryRepository tomatoSummaryRepository;

    public void updateTomatoSummary(String fileName, LocalDate date) {
        TomatoSummary summary = tomatoSummaryRepository.findByDate(date)
                .orElse(new TomatoSummary());
        summary.setDate(date);

        // 파일 이름에서 카테고리 추출
        String category = extractCategoryFromFileName(fileName);

        if ("ripe".equals(category)) {
            summary.setRipe(summary.getRipe() + 1);
        } else if ("unripe".equals(category)) {
            summary.setUnripe(summary.getUnripe() + 1);
        } else if ("halfripe".equals(category)) {
            summary.setHalfripe(summary.getHalfripe() + 1);
        }

        tomatoSummaryRepository.save(summary);
    }

    private String extractCategoryFromFileName(String fileName) {
        // 파일 이름을 '-'로 스플릿
        String[] parts = fileName.split("-");
        
        // 파일 이름 형식이 기대하는 형식인지 확인 (3번째 부분이 카테고리인 경우)
        if (parts.length >= 3) {
            // 중간 부분이 카테고리라고 가정
            String category = parts[1].toLowerCase(); // 또는 parts[2]를 사용할 수 있습니다

            if ("ripe".equals(category) || "unripe".equals(category) || "halfripe".equals(category)) {
                return category;
            }
        }
        
        return "unknown"; // 카테고리를 식별할 수 없는 경우
    }

    public TomatoSummary getSummaryByDate(LocalDate date) {
        return tomatoSummaryRepository.findByDate(date)
                .orElse(new TomatoSummary());
    }
}

