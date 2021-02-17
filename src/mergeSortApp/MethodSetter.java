package mergeSortApp;

import java.io.File;
import java.util.List;

public class MethodSetter {

    private WorkMethod method;

    public void setMethod(Arguments cmdArgs) {

        if (cmdArgs.getSortMode().equals("-a")) {

            if (cmdArgs.getDataType().equals("-s")) {
                this.method = new ASMergeSort();
            } else if (cmdArgs.getDataType().equals("-i")) {
                this.method = new AIMergeSort();
            }

        } else if (cmdArgs.getSortMode().equals("-d")) {

            if (cmdArgs.getDataType().equals("-s")) {
                this.method = new DSMergeSort();
            } else if (cmdArgs.getDataType().equals("-i")) {
                this.method = new DIMergeSort();
            }
        }
    }

    public List<String> checkFiles(Arguments cmdArgs) {
        return this.method.checkFiles(cmdArgs);
    }

    public File sortFiles(File file1, File file2, Arguments cmdArgs) {
        return this.method.sortFiles(file1, file2, cmdArgs);
    }
}