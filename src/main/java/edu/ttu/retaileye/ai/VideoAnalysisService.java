package edu.ttu.retaileye.ai;

import edu.ttu.retaileye.exceptions.InternalException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@Slf4j
public class VideoAnalysisService {

    public void analyzeVideo(String filePath) {
        try {
            log.info("Triggering motion detection for file: {}", filePath);

            ProcessBuilder pb = new ProcessBuilder(
                    "C:\\Users\\bahra\\OneDrive\\Desktop\\School\\CS 4366\\retaileye-ai\\.venv\\Scripts\\python.exe",
                "C:/Users/bahra/OneDrive/Desktop/School/CS 4366/retaileye-ai/main.py",
                    filePath
            );

            pb.redirectErrorStream(true);
            pb.inheritIO(); // prints Python output to console
            pb.start();

        } catch (IOException e) {
            throw new InternalException("Error running motion detection", e);
        }
    }
}