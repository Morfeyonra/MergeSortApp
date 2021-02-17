package mergeSortApp;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class Arguments {

    private long iterator;

    private final String sortMode;
    private final String dataType;

    private final String outFileName;

    private final List<String> inputFileArray;
    private List<String> provedFileList;

    public long getIterator() {
        return iterator;
    }

    public void preIncrementIterator() {
        ++this.iterator;
    }

    public String getSortMode() {
        return sortMode;
    }

    public String getDataType() {
        return dataType;
    }

    public String getOutFileName() {
        return outFileName;
    }

    public List<String> getInputFileArray() {
        return inputFileArray;
    }

    public void setProvedFileList(List<String> provedFileList) {
        this.provedFileList = provedFileList;
    }

    public List<String> getProvedFileList() {
        return provedFileList;
    }


    public Arguments(List<String> checkedArgs) {
        this.iterator = 0;
        this.sortMode = checkedArgs.get(0);
        this.dataType = checkedArgs.get(1);
        this.outFileName = checkedArgs.get(2);
        this.inputFileArray = new ArrayList<>(checkedArgs.subList(3, checkedArgs.size()));
    }


    static List<String> parseCMDArgs(String[] args) {

        if (args.length < 3) {
            System.out.println("There should be at least 3 command line arguments!");
            return null;
        }

        String sortMode = "-a";
        boolean sortModeExists = false;

        String dataType = "-s";
        boolean dataTypeExists = false;

        String newFileName;
        List<String> checkedArgs = new ArrayList<>();

        for (int i = 0; i < 2; i++) {

            if (args[i].equals("-a") || args[i].equals("-d")) {
                sortMode = args[i];
                sortModeExists = !sortModeExists;
            }

            if (args[i].equals("-s") || args[i].equals("-i")) {
                dataType = args[i];
                dataTypeExists = !dataTypeExists;
            }
        }

        if (sortModeExists && dataTypeExists) {

            if (args.length < 4) {
                System.out.println("There should be outputFileName and at least 1 inputFile command line arguments!");
                return null;
            }

            if (!isFilenameValid(args[2])) {
                System.out.println("Incorrect outputFile name.");
                return null;
            }

            checkedArgs.add(sortMode);
            checkedArgs.add(dataType);
            newFileName = args[2];
            checkedArgs.add(newFileName);
            checkedArgs.addAll(Arrays.asList(args).subList(3, args.length));

        } else if (dataTypeExists) {

            if (!isFilenameValid(args[1])) {
                System.out.println("Incorrect outputFile name.");
                return null;
            }

            checkedArgs.add(sortMode);
            checkedArgs.add(dataType);
            newFileName = args[1];
            checkedArgs.add(newFileName);
            checkedArgs.addAll(Arrays.asList(args).subList(2, args.length));

        } else {
            return null;
        }

        return checkedArgs;
    }

    static boolean isFilenameValid(String file) {
        File f = new File(file);
        try {
            f.getCanonicalPath();
            return true;
        } catch (IOException e) {
            return false;
        }
    }
}
