package com.silascandiolli.ai;

import dev.langchain4j.service.SystemMessage;
import io.quarkiverse.langchain4j.RegisterAiService;

@RegisterAiService(retrievalAugmentor = LibraryDataRetriever.class)
public interface LibraryAssistant {

    @SystemMessage("You are an assistant specializing in libraries organization. " +
            "Answer questions based on the provided information.")
    String answer(String question);

}
