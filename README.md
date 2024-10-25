# TOGAF Expairt

> An experimental project leveraging the power of AI to help architects to _architect_ better.
> The project provides a REST server, a REST CLI, and an Indexer CLI.

## Requirements

- Java 21

## Run

TOGAF Expairt is a plain Java application that can be run from the command line.
The following command will start the application:

**Start the REST server**

```shell
java -cp togaf-expairt.jar io.morin.togafexpairt.restserver.RestServer
```

**Chat with the server using a CLI prompt**

```shell
java -cp togaf-expairt.jar io.morin.togafexpairt.cli.RestClientCli
```

**Perform an indexation or re-indexation**

```shell
java -cp togaf-expairt.jar io.morin.togafexpairt.cli.IndexerCli
```

## Configuration

Each key in the table below corresponds to an environment variable or a Java system property.

For instance, to set the `togafexpairt.langchain4j.chat_model` property, you can use the following command line:

```shell
java -Dtogafexpairt.langchain4j.chat_model=MISTRAL -jar togafexpairt-0.0.1-SNAPSHOT.jar
```

Or you can set the `togafexpairt.langchain4j.chat_model` environment variable:

```shell
togafexpairt_langchain4j_integration=MISTRAL java -jar togafexpairt-0.0.1-SNAPSHOT.jar
```

| Key                                                    | Default                        | Enumeration                      |
|--------------------------------------------------------|--------------------------------|----------------------------------|
| `togafexpairt.langchain4j.chat_model`                  | `OLLAMA`                       | `MISTRAL`, `OLLAMA`              |
| `togafexpairt.langchain4j.embedding_model`             | `OLLAMA`                       | `MINI_LM`, `MINI_LM_Q`, `OLLAMA` |
| `togafexpairt.langchain4j.dimension`                   | `384`                          |                                  |
| `togafexpairt.langchain4j.max_memory_messages`         | `20`                           |                                  |
| `togafexpairt.langchain4j.max_segment_size_in_chars`   | `500`                          |                                  |
| `togafexpairt.langchain4j.max_overlap_size_in_chars`   | `60`                           |                                  |
| `togafexpairt.langchain4j.max_embedded_content_result` | `30`                           |                                  |
| `togafexpairt.mistral.model_name`                      | `mistral-medium`               |                                  |
| `togafexpairt.mistral.api_key`                         |                                |                                  |
| `togafexpairt.qdrant.host`                             | `localhost`                    |                                  |
| `togafexpairt.qdrant.port`                             | `6334`                         |                                  |
| `togafexpairt.ollama.chat_model_name`                  | `llama3.2:1b`                  |                                  |
| `togafexpairt.ollama.embedding_model_name`             | `nomic-embed-text`             |                                  |
| `togafexpairt.ollama.base_url`                         | `http://localhost:11434`       |                                  |
| `togafexpairt.restserver.host`                         | `localhost`                    |                                  |
| `togafexpairt.restserver.port`                         | `9090`                         |                                  |
| `togafexpairt.rest_cli.prompt_url`                     | `http://localhost:9090/prompt` |                                  |

## Dependencies

Start Ollama and Qdrant with Docker Compose:

```shell
docker compose up
```

Stop Ollama and Qdrant with Docker Compose:
```shell
docker compose down --remove-orphans
```

Stop Ollama and Qdrant with Docker Compose:
```shell
docker compose down --remove-orphans --volumes
```

Open the Qdrant dashboard:
```shell
open http://http://localhost:6333/dashboard
```

## Build

**Build the uber jar**

```shell
./mvnw verify
```

## Maintenance

**Dependencies upgrade**

```shell
./mvnw versions:display-dependency-updates
```

**Release**

```shell
./mvnw --batch-mode release:clean \
&& ./mvnw --batch-mode release:prepare \
  -DreleaseVersion=X.Y.Z \
  -DdevelopmentVersion=Y.X.Z-SNAPSHOT
```
