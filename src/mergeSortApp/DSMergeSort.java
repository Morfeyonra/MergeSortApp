package mergeSortApp;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class DSMergeSort implements WorkMethod {

    @Override
    public List<String> checkFiles(Arguments cmdArgs) {

        List<String> inputFileArray = cmdArgs.getInputFileArray();
        List<String> provedFileList = new ArrayList<>();

        for (String relativeFilePath : inputFileArray) {

            File file = new File(relativeFilePath);
            try (Scanner scanner = new Scanner(file)) {

                boolean isOk = scanner.hasNext();
                String stringLine = scanner.nextLine();

                while (scanner.hasNext() && isOk) {

                    String newStringLine = scanner.nextLine();
                    if (newStringLine.compareTo(stringLine) > 0) {
                        System.out.println("There is an error into the input data!\n" +
                                "In file: " + relativeFilePath);
                        isOk = false;
                        continue;
                    }
                    stringLine = newStringLine;
                }

                if (isOk) {
                    provedFileList.add(relativeFilePath);
                }

            } catch (FileNotFoundException e) {
                System.out.println("No file found or it is empty: " + relativeFilePath);
            }
        }
        return provedFileList;
    }

    @Override
    public File sortFiles(File file1, File file2, Arguments cmdArgs) {

        try (Scanner scanner1 = new Scanner(file1);
             Scanner scanner2 = new Scanner(file2)) {

            try (FileWriter fw = new FileWriter( cmdArgs.getIterator() + cmdArgs.getOutFileName())) {

                String sc1num = scanner1.nextLine();
                boolean isWrittenSc1 = false;
                String sc2num = scanner2.nextLine();

                if (sc1num.compareTo(sc2num) >= 0) {
                    fw.write(sc1num + "\r\n");
                    isWrittenSc1 = true;
                } else {
                    fw.write(sc2num + "\r\n");
                }

                while (scanner1.hasNext() && scanner2.hasNext()) {

                    if (isWrittenSc1) {
                        sc1num = scanner1.nextLine();
                        isWrittenSc1 = false;
                    } else {
                        sc2num = scanner2.nextLine();
                    }

                    if (sc1num.compareTo(sc2num) >= 0) {
                        fw.write(sc1num + "\r\n");
                        isWrittenSc1 = true;
                    } else {
                        fw.write(sc2num + "\r\n");
                    }
                }

                while (scanner1.hasNext()) {
                    fw.write(scanner1.nextLine() + "\r\n");
                }

                while (scanner2.hasNext()) {
                    fw.write(scanner2.nextLine() + "\r\n");
                }

            } catch (IOException e) {
                System.out.println("Something is wrong with new file creation!");
            }

        } catch (FileNotFoundException e) {
            System.out.println("One or both files are not found: " + file1.getPath() + file1.getPath());
        }

        collectGarbage(file1, file2, cmdArgs);

        cmdArgs.preIncrementIterator();
        return new File((cmdArgs.getIterator() - 1) + cmdArgs.getOutFileName());
    }
}