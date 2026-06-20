package com.euflausino.requisition.adapter.out.repository;

import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Component
public final class PathForSaveInSystem {

    private PathForSaveInSystem() {}

    private static final String APP_FOLDER = "requisitor";

    private static final Path ROOT = Paths.get(System.getProperty("user.home"), APP_FOLDER);

    private static Path root() {
        create(ROOT);
        return ROOT;
    }

    private static Path requests() {

        Path path = root().resolve("requests");

        create(path);

        return path;
    }

    private static Path collections() {

        Path path =
                root().resolve("collections");

        create(path);

        return path;
    }

    private static Path exports() {

        Path path =
                root().resolve("exports");

        create(path);

        return path;
    }

    private static Path environments() {

        Path path =
                root().resolve("environments");

        create(path);

        return path;
    }

    static Path requestFile(
            String fileName
    ) {

        return requests()
                .resolve(
                        sanitize(fileName)
                                + ".json"
                );
    }

    static Path collectionFile(
            String fileName
    ) {

        return collections()
                .resolve(
                        sanitize(fileName)
                                + ".json"
                );
    }

    private static void create(
            Path path
    ) {

        try {

            Files.createDirectories(
                    path
            );

        } catch (IOException e) {

            throw new RuntimeException(
                    "Erro ao criar diretório: "
                            + path,
                    e
            );
        }
    }

    private static String sanitize(
            String value
    ) {

        return value
                .replaceAll(
                        "[\\\\/:*?\"<>|]",
                        "_"
                );
    }
}