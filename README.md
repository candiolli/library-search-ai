# library-search-ai

This project uses Quarkus, the Supersonic Subatomic Java Framework.

If you want to learn more about Quarkus, please visit its website: https://quarkus.io/ .

## Tools to execute this project:

1. Java 21
2. Claude Code
2. Ollama: https://docs.ollama.com/
3. Quarkus: https://pt.quarkus.io/
4. PgVector: A standard relational database excels at structured queries but struggles with finding data based on conceptual similarity
5. RAG: Retrieval-Augmented Generation (RAG) is an AI framework that enhances the performance of large language models (LLMs) by integrating them with external knowledge sources.
6. MCP: it’s the protocol, like HTTP (https://modelcontextprotocol.io/docs/getting-started/intro)
7. HTTP GET:
   - Test: curl -v http://localhost:8080/library/ask?q=Find%20me%20one%20book%20about%20Java%20and%20AI -w "@performance-format.txt" -o test1.txt

## Running the application in dev mode

You can run your application in dev mode that enables live coding using:
```shell script
./mvnw compile quarkus:dev
```

> **_NOTE:_**  Quarkus now ships with a Dev UI, which is available in dev mode only at http://localhost:8080/q/dev/.

## Packaging and running the application

The application can be packaged using:
```shell script
./mvnw package
```
It produces the `quarkus-run.jar` file in the `target/quarkus-app/` directory.
Be aware that it’s not an _über-jar_ as the dependencies are copied into the `target/quarkus-app/lib/` directory.

The application is now runnable using `java -jar target/quarkus-app/quarkus-run.jar`.

If you want to build an _über-jar_, execute the following command:
```shell script
./mvnw package -Dquarkus.package.type=uber-jar
```

The application, packaged as an _über-jar_, is now runnable using `java -jar target/*-runner.jar`.

## Creating a native executable

You can create a native executable using: 
```shell script
./mvnw package -Dnative
```

Or, if you don't have GraalVM installed, you can run the native executable build in a container using: 
```shell script
./mvnw package -Dnative -Dquarkus.native.container-build=true
```

You can then execute your native executable with: `./target/library-search-ai-1.0.0-SNAPSHOT-runner`

If you want to learn more about building native executables, please consult https://quarkus.io/guides/maven-tooling.

## Related Guides

- JDBC Driver - PostgreSQL ([guide](https://quarkus.io/guides/datasource)): Connect to the PostgreSQL database via JDBC
- LangChain4j Ollama ([guide](https://docs.quarkiverse.io/quarkus-langchain4j/dev/index.html)): Provides the basic integration of Ollama with LangChain4j
- LangChain4j pgvector embedding store ([guide](https://docs.quarkiverse.io/quarkus-langchain4j/dev/index.html)): Provides the pgvector Embedding store for Quarkus LangChain4j
- REST ([guide](https://quarkus.io/guides/rest)): A Jakarta REST implementation utilizing build time processing and Vert.x. This extension is not compatible with the quarkus-resteasy extension, or any of the extensions that depend on it.

## Provided Code

### REST

Easily start your REST Web Services


[Related guide section...](https://quarkus.io/guides/getting-started-reactive#reactive-jax-rs-resources)
