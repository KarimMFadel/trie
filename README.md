# Java Trie Implementation
This project provides a simple implementation of a Trie data structure in Java. It supports insert, lookup, update, and delete operations. The project is built using Apache Maven and includes unit tests written with JUnit 5.

## Project Structure
The project follows the standard Maven directory layout:
```
trie/
├── pom.xml
└── src/
├── main/
│   └── java/
│       └── com/
│           └── example/
│               └── trie/
│                   └── Trie.java
└── test/
└── java/
└── com/
└── example/
└── trie/
└── TrieTest.java
```

## Prerequisites
Java Development Kit (JDK) 21 or later.

Apache Maven.

## How to Build and Run
Follow these steps to build the project and run the unit tests from the command line.

1. Clone the Repository
   First, you would clone the repository to your local machine.
   (Note: For this example, you will just need to create the files as laid out in the project structure).

2. Navigate to the Project Directory
   Open your terminal or command prompt and change to the root directory of the project (where the pom.xml file is located).
```bash
  cd path/to/your/trie
```

3. Compile the Project
   Compile the source code using Maven. This command downloads dependencies and compiles your Java classes.
```bash
  mvn compile
```

4. Run the Unit Tests
   Execute the JUnit tests to verify that the Trie implementation is working correctly.
```bash
  mvn test
 ```

You should see output indicating that the build was successful and all tests passed.

5. Package the Project
   You can package the compiled code into a JAR file.

```bash
  mvn package
```

This will create a trie-1.0-SNAPSHOT.jar file in the target directory.

# trie
implemented https://en.wikipedia.org/wiki/Trie
