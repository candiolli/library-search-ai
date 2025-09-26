package com.silascandiolli.ai;

import dev.langchain4j.data.document.splitter.DocumentSplitters;
import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.model.embedding.EmbeddingModel;
import dev.langchain4j.store.embedding.EmbeddingStore;
import dev.langchain4j.store.embedding.EmbeddingStoreIngestor;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Produces;

@ApplicationScoped
public class IngestorProducer {

    @Produces
    public EmbeddingStoreIngestor embeddingStoreIngestor(
            EmbeddingStore<TextSegment> store,
            EmbeddingModel embeddingModel) {

        return EmbeddingStoreIngestor.builder()
                .documentSplitter(DocumentSplitters.recursive(800, 100))
                .embeddingModel(embeddingModel)
                .embeddingStore(store)
                .build();
    }
}