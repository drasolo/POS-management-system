package Clase;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class AuditService {
    private static AuditService instance;
    private static final String AUDIT_FILE_PATH = "audit.csv";

    private AuditService() {
        // Private constructor for singleton
    }

    public static AuditService getInstance() {
        if (instance == null) {
            instance = new AuditService();
        }
        return instance;
    }

    public void logAction(String action) {
        try (FileWriter writer = new FileWriter(AUDIT_FILE_PATH, true)) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            String timestamp = LocalDateTime.now().format(formatter);
            writer.append(action).append(", ").append(timestamp).append("\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
