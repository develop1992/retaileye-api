package edu.ttu.retaileye.ai;

import edu.ttu.retaileye.exceptions.InternalException;
import edu.ttu.retaileye.records.AnalyzeVideoRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
@RequiredArgsConstructor
@Slf4j
public class VideoAnalysisService {

    private final WebClient.Builder webClientBuilder;

    @Value("${ai.api.base-url}")
    private String aiBaseUrl;

    public void analyzeVideo(String fileName) {
        try {
            log.info("Sending request to AI service for file: {}", fileName);

            webClientBuilder.build()
                    .post()
                    .uri(aiBaseUrl + "/analyze-video")
                    .contentType(MediaType.APPLICATION_JSON)
                    .bodyValue(new AnalyzeVideoRequest(fileName))
                    .retrieve()
                    .bodyToMono(Void.class)
                    .doOnSuccess(response -> log.info("Video analysis request sent successfully"))
                    .doOnError(err -> log.error("AI service call failed: {}", err.getMessage()))
                    .block(); // Blocking for simplicity, will consider using reactive patterns in production

        } catch (Exception e) {
            throw new InternalException("Failed to call AI service", e);
        }
    }
}