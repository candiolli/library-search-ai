package com.silascandiolli.ai;

import dev.langchain4j.data.document.Document;
import dev.langchain4j.store.embedding.EmbeddingStoreIngestor;
import io.quarkus.runtime.StartupEvent;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;
import jakarta.inject.Inject;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import io.smallrye.common.annotation.RunOnVirtualThread;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@ApplicationScoped
@RunOnVirtualThread
public class DataIngestor {

    @Inject
    EmbeddingStoreIngestor ingestor;

    @ConfigProperty(name = "rag.data.path")
    String dataPath;

    public void ingest(@Observes StartupEvent event) {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(
                Objects.requireNonNull(Thread.currentThread().getContextClassLoader().getResourceAsStream(dataPath)),
                StandardCharsets.UTF_8))) {

            // Skip header line
            reader.readLine();

            List<Document> documents = reader.lines()
                    .map(line -> line.split(","))
                    .map(this::toDocument)
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList());

            ingestor.ingest(documents);
        } catch (Exception e) {
            throw new RuntimeException("Failed to ingest data", e);
        }
    }

    private Document toDocument(String[] row) {
        try {
            // Column mapping based on the INEP CPC 2023 dataset structure.
            // Verify these indices if you use a different version of the file.

            String bookName = row[0];
            String bookDescription = row[1];
            String bookAuthor = row[2];
            String tags = row[3];
            String pages = row[4];
            String dateOfLaunch = row[5];

            String documentText = String.format(
                    "The book '%s' is talking about %s. Wrote by %s, is related with those categories %s. " +
                            "Has total of pages '%s', " +
                            "it was lanched on %s.",
                    bookName, bookDescription, tags, bookAuthor, pages, dateOfLaunch
            );

            System.out.println("row = " + documentText);

            Document document = Document.from(documentText);
            document.metadata().put("pages", pages);
            document.metadata().put("dateOfLaunch", dateOfLaunch);
            document.metadata().put("bookName", bookName);
            document.metadata().put("bookAuthor", bookAuthor);
            document.metadata().put("tags", tags);
            return document;

        } catch (ArrayIndexOutOfBoundsException e) {
            // Handle potential malformed rows properly
            return null;
        }
    }
}
