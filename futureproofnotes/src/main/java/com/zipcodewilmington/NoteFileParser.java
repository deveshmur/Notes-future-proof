package com.zipcodewilmington;

import java.nio.file.Path;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.yaml.snakeyaml.Yaml;


public class NoteFileParser {

    private static final String YAML_DELIMITER = "---";
    
    public Note parse(Path filePath) {
        try {
            String fileContent = Files.readString(filePath, StandardCharsets.UTF_8);
            String yamlText = extractYAML(fileContent);
            String body = extractBody(fileContent);

            NoteMetadata metadata = parseMetadata(yamlText);

            String fileName = filePath.getFileName().toString(); 
            String id = fileName.replaceFirst("\\.notes", "");
        
            return new Note(id, metadata, body);
        } catch (IOException e) {
            throw new RuntimeException("Failed to read note.file");
        }
    }

    public String extractYAML (String fileContent) {
        
        if (!fileContent.startsWith(YAML_DELIMITER)) {
            throw new RuntimeException("Note file is missing starting YAML delimiter '---'");
        }

        int firstDelimiterIndex = fileContent.indexOf(YAML_DELIMITER);
        int secondDelimiterIndex = fileContent.indexOf(YAML_DELIMITER, firstDelimiterIndex + YAML_DELIMITER.length());

        if (secondDelimiterIndex == -1) {
            throw new RuntimeException("Note file is missing closing YAML delimiter '---'");
        }

        int yamlStart = firstDelimiterIndex + YAML_DELIMITER.length();
        int yamlEnd = secondDelimiterIndex;

        String yamlSection = fileContent.substring(yamlStart, yamlEnd).trim();
        if (yamlSection.isEmpty()) {
            throw new RuntimeException("YAML metadata section is empty");
        }
        return yamlSection;
    }

    public String extractBody(String fileContent) {
        int firstDelimiterIndex = fileContent.indexOf(YAML_DELIMITER);
        int secondDelimiterIndex = fileContent.indexOf(YAML_DELIMITER, firstDelimiterIndex + YAML_DELIMITER.length());

        if (firstDelimiterIndex == -1 || secondDelimiterIndex == -1) {
            throw new RuntimeException("Cannot extract body: YAML delimiters missing");
        }

        int bodyStart = secondDelimiterIndex + YAML_DELIMITER.length();
        String body = fileContent.substring(bodyStart).trim();

        return body;
    }

    @SuppressWarnings("unchecked")
    public NoteMetadata parseMetadata(String yamlText) {
        Yaml yaml = new Yaml();
        Object loaded = yaml.load(yamlText);

        if (!(loaded instanceof Map)) {
            throw new RuntimeException("YAML metadata is not a valid mapping");
        }

        
        Map<String, Object> map = (Map<String, Object>) loaded;

        NoteMetadata metadata = new NoteMetadata();


        String title = getString(map, "title");
        String createdStr = getString(map, "created");
        String modifiedStr = getString(map, "modified");


        if (title == null || title.isBlank()) {
            throw new RuntimeException("Missing required metadata field: title");
        }
        if (createdStr == null || createdStr.isBlank()) {
            throw new RuntimeException("Missing required metadata field: created");
        }
        if (modifiedStr == null || modifiedStr.isBlank()) {
            throw new RuntimeException("Missing required metadata field: modified");
        }
        

        metadata.setTitle(title);
        metadata.setCreated(parseUtcDateTime(createdStr));
        metadata.setModified(parseUtcDateTime(modifiedStr));


        Object tagsObj = map.get("tags");
        List<String> tags = new ArrayList<>();
        if (tagsObj instanceof List) {
            for (Object tag : (List<?>) tagsObj) {
                if (tag != null) {
                    tags.add(tag.toString());
                }
            }
        }


        metadata.setTags(tags.isEmpty() ? null : tags);
        metadata.setAuthor(getString(map, "author"));
        metadata.setStatus(getString(map, "status"));


        String priorityStr = null;
        Object priorityObj = map.get("priority");
        if (priorityObj != null) {
            priorityStr = priorityObj.toString();
        }
        if (priorityStr != null && !priorityStr.isBlank()) {
            try {
                metadata.setPriority(Integer.parseInt(priorityStr));
            } catch (NumberFormatException e) {
                throw new RuntimeException("Invalid priority value in metadata: " + priorityStr, e);
            }
        }

        return metadata;
    }

    public String serialize(Note note) {
        NoteMetadata metadata = note.getMetadata();

        if (metadata == null) {
            throw new RuntimeException("Cannot serialize note without metadata");
        }

        StringBuilder sb = new StringBuilder();
        sb.append(YAML_DELIMITER).append("\n");

        sb.append("title: ").append(escape(metadata.getTitle())).append("\n");
        sb.append("created: ").append(formatUtcDateTime(metadata.getCreated())).append("\n");
        sb.append("modified: ").append(formatUtcDateTime(metadata.getModified())).append("\n");

        if (metadata.getTags() != null && !metadata.getTags().isEmpty()) {
            sb.append("tags:\n");
            for (String tag : metadata.getTags()) {
                sb.append("  - ").append(escape(tag)).append("\n");
            }
        }

        if (metadata.getAuthor() != null && !metadata.getAuthor().isBlank()) {
            sb.append("author: ").append(escape(metadata.getAuthor())).append("\n");
        }

        if (metadata.getStatus() != null && !metadata.getStatus().isBlank()) {
            sb.append("status: ").append(escape(metadata.getStatus())).append("\n");
        }

        if (metadata.getPriority() != null) {
            sb.append("priority: ").append(metadata.getPriority()).append("\n");
        }

        sb.append(YAML_DELIMITER).append("\n");

        String body = note.getBody() == null ? "" : note.getBody();
        sb.append(body).append("\n");

        return sb.toString();
    }
    

    private String getString(Map<String, Object> map, String key) {
        Object value = map.get(key);
        return value == null ? null : value.toString();
    }

    private LocalDateTime parseUtcDateTime(String iso8601WithZ) {
        try {
            Instant instant = Instant.parse(iso8601WithZ);
            return LocalDateTime.ofInstant(instant, ZoneOffset.UTC);
        } catch (Exception e) {
            throw new RuntimeException("Invalid timestamp (expected ISO-8601 with Z): " + iso8601WithZ, e);
        }
    }

    private String formatUtcDateTime(LocalDateTime dateTime) {
        if (dateTime == null) {
            return null;
        }
        return dateTime.atOffset(ZoneOffset.UTC).toInstant().toString(); // yields 2025-05-20T10:30:00Z
    }


    private String escape(String value) {
        if (value == null) {
            return "";
        }
        return value.replace("\n", " ").trim();
    }

}
