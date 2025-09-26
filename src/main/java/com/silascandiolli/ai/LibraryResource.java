package com.silascandiolli.ai;

import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.QueryParam;

@Path("/library")
public class LibraryResource {

    @Inject
    LibraryAssistant assistant;

    @GET
    @Path("/ask")
    public String ask(@QueryParam("q") String question) {
        return assistant.answer(question);
    }

}
