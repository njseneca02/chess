# ♕ BYU CS 240 Chess

## Sequence diagram:
https://sequencediagram.org/index.html#initialData=IYYwLg9gTgBAwgGwJYFMB2YBQAHYUxIhK4YwDKKUAbpTngUSWDABLBoAmCtu+hx7ZhWqEUdPo0EwAIsDDAAgiBAoAzqswc5wAEbBVKGBx2ZM6MFACeq3ETQBzGAAYAdAE5xDAaTgALdvYoALIoAIyY9lAQAK7YMADEaMBUljAASij2SKoWckgQaAkA7r5IYGKIqKQAtAB85JQ0UABcMADaAAoA8mQAKgC6MAD00QZQADpoAN4ARKOUSQC2KDPNMzAzADQbuOpF0Byr61sbKIvASAhHGwC+mMJNMHWs7FyUrbPzUEsraxvbM12qn2UEOfxOMzOFyu4LubE43FgzweolaUEy2XKUAAFBksjlKBkAI7RNRgACU90aoie9Vk8iUKnUrUCYAAqmNsV8fpT6YplGpVLSjDpWmQAKIAGXFcF6MG5wGWMAAZlFFvKxpg+YzBU9kdSVK00NEEAgqSIVMLtQLmTAQOi5CgOZQuWMftsgSCONsoZdedodephcZWgBJAByErScoVyw9+mBBx950uMAjvS6GtoKMtz2tTNUrXtKEdCmiYF8roWipQ-oZNqFzxDacj4ujWe+Ne2wHLvl6EAA1ugWxmYD2K1qAw29fUcyhWuO+4P0OaqDTqs94W8WukMQSoBlVCasFvETOGtR3jBPm6a+tWscAYv+0O0PeNrdV5Rz5VzK0AExOE4kw3tWyzvo+GzPsub4wA+n7oBwpiRDEcTxNAASGJKEBZIU8QlGUFTIOYwoPFenQ9AMwwGOo+RoCBsYoPGewHHcZFIvUp5XqBnbgeCAKegc1wQr6MLHHCrxnvqFrzjACA4UgaDYthuHEqSOSUnOVpTgWC69s6OKMXW-IFsGorkFKMoxreSqqhA6pfDAJSUIYgmgjAAC8MBuUh+a6hus4GrJjGruudI6YKRYOuUZYVlWvG1pO9amU25kRlG1lgUxY69i+w7ppmi5JSZ-nSWuho5RWeVoKFuabpJV54pihJqMemBcUiZXcXMNkrHBH4QtBr4QZ+7E-sRGAAUBIE9VlI2DblMHzXciHIVEsQJJEKDDipMTMPhpTlJgv41IFl47m00iWb04qDEMNGqHRkxDSuY31QiV4vTVHWkUFrTyfYe3KThe1qWSmlBdpyWRUYKAILFlZfcZgaNvUzZXdKN2VUur4qmq2PFSj55znpVUwbVhgBS8H07rt5aHm1P1U+xHxsY035UydYBTcB0wrZwa2oQk6IcDAkqYjAADiNZCgdhHHRNzBdRdkvilRQz2DWz2La+X4cdT26kzjK5M2d5WycgOTS8sqjKZi1tqGDGkU1DJW2qyDu28j06pWKlmyjAms23j9mBzWhM+2VqJhzbLtUx1rTi1bMsMwgJ4NZ1ZvcRsQdqKs7SzLnobSKsAIEeUBk-EcEI6AgoADpXd5rBCufhjWzc3DA-Rs+d41VNzMCAbzUw5zL+dtIXNbF6XGzl06vXVwCtf143fEzC3NZt8sHdd-zSEROtaHYNEUDYNw8DRYYDvFIdRH97952tBRfR3bn2tk6+IGtzWPePO9hvY2ql-TeP92oZwfubVo5whwO2xHAS+DsnYUjjuFaGtpiyOlgd-ZY3sUpozSmgMYzBsHZSLtIGAilIAxzUBHFKUcKpkLjv-RErR4ElnKIg1qacwE02JuzHck9ljT36uJPWfc-yD2mtMGYZDlpmAFgfIW8RLBw3kkUGAAApCAikpY1gSMvEAA4Fb32Vk-DobJ1ZvySDrdAIEz7ABUVAOAEB5JQG2GQ3+HNOIZyNkA6Y9jHHONce4qe0gJK8PobJAAVtotAsCtGKU4SScGKCZARVtLELQHCazYgCZQIJ0AQlCOkLg-y+DWhsg6NIBQWNc5OV8C5ahxdPJNPIRKOUeSnEuOgC0r6TDvE01aAkuJNZU7pwiaY68njM7wEVjzECe9BYbXiJEBxsySywGANgM+hA8gFBvvLLmEDyIYzVrdYYxgxHMO4uE7cEDo4gG4HgWBiANlIIhjJV2KNWjSDhigCuYwva0LKSKH511xQhwcpqLSeZ0mFhkH87JNtsSlKDL7GQ4LIXUNSX5W0vzuAxV7Ci4FaLykYsxhCuy6oiowuuTuV5eAxk8LuczfhrMrnPC5vMvm8ikJAA

This project demonstrates mastery of proper software design, client/server architecture, networking using HTTP and WebSocket, database persistence, unit testing, serialization, and security.

## 10k Architecture Overview

The application implements a multiplayer chess server and a command line chess client.

[![Sequence Diagram](10k-architecture.png)](https://sequencediagram.org/index.html#initialData=C4S2BsFMAIGEAtIGckCh0AcCGAnUBjEbAO2DnBElIEZVs8RCSzYKrgAmO3AorU6AGVIOAG4jUAEyzAsAIyxIYAERnzFkdKgrFIuaKlaUa0ALQA+ISPE4AXNABWAexDFoAcywBbTcLEizS1VZBSVbbVc9HGgnADNYiN19QzZSDkCrfztHFzdPH1Q-Gwzg9TDEqJj4iuSjdmoMopF7LywAaxgvJ3FC6wCLaFLQyHCdSriEseSm6NMBurT7AFcMaWAYOSdcSRTjTka+7NaO6C6emZK1YdHI-Qma6N6ss3nU4Gpl1ZkNrZwdhfeByy9hwyBA7mIT2KAyGGhuSWi9wuc0sAI49nyMG6ElQQA)

## Modules

The application has three modules.

- **Client**: The command line program used to play a game of chess over the network.
- **Server**: The command line program that listens for network requests from the client and manages users and games.
- **Shared**: Code that is used by both the client and the server. This includes the rules of chess and tracking the state of a game.

## Starter Code

As you create your chess application you will move through specific phases of development. This starts with implementing the moves of chess and finishes with sending game moves over the network between your client and server. You will start each phase by copying course provided [starter-code](starter-code/) for that phase into the source code of the project. Do not copy a phases' starter code before you are ready to begin work on that phase.

## IntelliJ Support

Open the project directory in IntelliJ in order to develop, run, and debug your code using an IDE.

## Maven Support

You can use the following commands to build, test, package, and run your code.

| Command                    | Description                                     |
| -------------------------- | ----------------------------------------------- |
| `mvn compile`              | Builds the code                                 |
| `mvn package`              | Run the tests and build an Uber jar file        |
| `mvn package -DskipTests`  | Build an Uber jar file                          |
| `mvn install`              | Installs the packages into the local repository |
| `mvn test`                 | Run all the tests                               |
| `mvn -pl shared test`      | Run all the shared tests                        |
| `mvn -pl client exec:java` | Build and run the client `Main`                 |
| `mvn -pl server exec:java` | Build and run the server `Main`                 |

These commands are configured by the `pom.xml` (Project Object Model) files. There is a POM file in the root of the project, and one in each of the modules. The root POM defines any global dependencies and references the module POM files.

## Running the program using Java

Once you have compiled your project into an uber jar, you can execute it with the following command.

```sh
java -jar client/target/client-jar-with-dependencies.jar

♕ 240 Chess Client: chess.ChessPiece@7852e922
```
