Quarkus #26780 reproducer
=========================

This is a reproducer project for the https://github.com/quarkusio/quarkus/issues/26780

The class FileResource won't be properly exposed in native mode unless annotated with `@RegisterForReflection`.

### Running in JVM mode

Package and run the application:

```shell
mvn clean package
java -jar target/quarkus-app/quarkus-run.jar
```

1. Open your browser and go to http://localhost:8080/
2. You should be able to click on the links and load the included files.

Alternatively run:

```shell
 curl localhost:8080/file-001.txt
```

Which should load the requested file.

### Running in native mode

Package and run the application:
```shell
mvn clean package -Pnative
 ./target/reactive-nosuchmethodexception-1.0.0-SNAPSHOT-runner
```

Run:

```shell
 curl localhost:8080/file-001.txt
```

The application log should print an error:

```
Caused by: java.lang.NoSuchMethodException: FileResource.getStaticFile(java.lang.String)
```
