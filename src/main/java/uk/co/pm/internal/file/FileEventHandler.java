package uk.co.pm.internal.file;

import java.io.IOException;
import java.nio.file.Path;

public interface FileEventHandler {

    public void handle(Path file) throws IOException;
    public void readExistingCSV();

}
