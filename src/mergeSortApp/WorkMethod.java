package mergeSortApp;

import java.io.File;
import java.util.List;

interface WorkMethod {

    List<String> checkFiles(Arguments cmdArgs);

    File sortFiles(File file1, File file2, Arguments cmdArgs);

    default void collectGarbage(File file1, File file2, Arguments cmdArgs) {

        if (file1.getName().matches("\\d+" + cmdArgs.getOutFileName())) {
            file1.delete();
        }

        if (file2.getName().matches("\\d+" + cmdArgs.getOutFileName())) {
            file2.delete();
        }
    }
}