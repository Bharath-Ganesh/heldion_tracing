package com.oracle.books.service;

import com.oracle.books.dto.Books;
import io.helidon.config.Config;
import io.helidon.webserver.Routing;
import io.helidon.webserver.ServerRequest;
import io.helidon.webserver.ServerResponse;
import io.helidon.webserver.Service;

import javax.json.*;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class BookResourceService implements Service {

    private BookManager bookManager = new BookManager();

    private static final JsonBuilderFactory JSON = Json.createBuilderFactory(Collections.emptyMap());

    /**
     * The config value for the key {@code greeting}.
     */
    private final AtomicReference<String> greeting = new AtomicReference<>();


    public BookResourceService(Config config) {
        greeting.set(config.get("app.greeting").asString().orElse("Ciao"));
    }

    @Override
    public void update(Routing.Rules rules) {
        rules
                .get("/{id}",this::books);
    }

    private void books(ServerRequest serverRequest, ServerResponse serverResponse) {
        String name = serverRequest.path().param("id");
        sendResponse(serverResponse, name);
    }

    private void sendResponse(ServerResponse response, String name) {
        String msg = name;

        JsonObject returnObject = JSON.createObjectBuilder()
                .add("message", msg)
                .build();
        response.send(returnObject);
    }






}
