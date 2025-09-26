# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project Overview

This is a Java-based AI-powered library search application built with Quarkus, LangChain4j, and PostgreSQL with pgvector. It provides a Retrieval-Augmented Generation (RAG) system for querying information about Brazilian higher education institutions and their course evaluations (CPC 2023 data).

## Common Commands

**Development mode (with live reload):**
```bash
./mvnw compile quarkus:dev
```

**Run tests:**
```bash
./mvnw test
```

**Package application:**
```bash
./mvnw package
```

**Run packaged application:**
```bash
java -jar target/quarkus-app/quarkus-run.jar
```

**Build native executable:**
```bash
./mvnw package -Dnative
```

## Architecture Overview

### Core Components

- **LibraryResource** (`/library/ask` endpoint): REST controller that accepts queries and returns AI-generated answers
- **LibraryAssistant**: LangChain4j AI service interface with RAG capabilities, configured to specialize in library organization
- **LibraryDataRetriever**: Supplier that configures the RAG retrieval augmentor with embedding store and model
- **DataIngestor**: Startup service that processes the CPC 2023 CSV data and ingests it into the pgvector embedding store
- **IngestorProducer**: CDI producer for configuring document splitter and embedding store ingestor

### Data Flow

1. On startup, `DataIngestor` reads `cpc_2023-custom.csv` and converts rows into documents
2. Documents are split into segments (800 chars with 100 char overlap) and embedded using `nomic-embed-text` model
3. Embeddings are stored in PostgreSQL with pgvector extension
4. User queries to `/library/ask?q=<question>` trigger semantic search through the embedding store
5. Retrieved context is passed to `gpt-oss:20b` model via `LibraryAssistant` for answer generation

### Configuration

The application uses Ollama for both chat and embedding models:
- Chat model: `gpt-oss:20b`
- Embedding model: `nomic-embed-text` (768 dimensions)
- Ollama endpoint: `http://localhost:11434`

PostgreSQL with pgvector is required for embedding storage. The extension automatically creates the vector extension and drops/recreates tables on restart during development.

### Data Format

The CSV data contains Brazilian higher education institution information with columns:
- Institution name and code
- Administrative category
- Course area
- City and state
- CPC (Preliminary Course Concept) score

Each row is converted to a natural language document describing the institution, course, location, and evaluation score.