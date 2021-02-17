package mergeSortApp;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayDeque;
import java.util.List;
import java.util.Queue;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        List<String> checkedArgs = Arguments.parseCMDArgs(args);

        if (checkedArgs == null) {
            System.out.println("Wrong command line arguments!");
            return;
        }

        Arguments cmdArgs = new Arguments(checkedArgs);

        long time = System.currentTimeMillis();
        if (doLogic(cmdArgs)) {
            System.out.println("Success! Sorted in " +
                    String.format("%.2f", (double)(System.currentTimeMillis() - time) / 1000) + " seconds");
        }
    }

    private static boolean doLogic(Arguments cmdArgs) {

        Queue<File> checkedFiles = new ArrayDeque<>();

        MethodSetter method = new MethodSetter();
        method.setMethod(cmdArgs);
        cmdArgs.setProvedFileList(method.checkFiles(cmdArgs));

        for (String checkedPath : cmdArgs.getProvedFileList()) {
            File file = new File(checkedPath);
            checkedFiles.offer(file);
        }

        if (checkedFiles.size() == 0) {
            System.out.println("No correct files found...");
            return false;
        }

        if (checkedFiles.size() == 1) {
            try (Scanner scanner = new Scanner(checkedFiles.peek());
                 FileWriter fw = new FileWriter(cmdArgs.getOutFileName())) {

                while (scanner.hasNext()) {
                    fw.write(scanner.nextLine() + "\r\n");
                }
                return true;

            } catch (IOException e) {
                System.out.println("Something is wrong with new file creation!\n" +
                        "There is only 1 correct file anyway: " + checkedFiles.poll().getName());
                return false;
            }
        }

        while (checkedFiles.size() != 1) {
            checkedFiles.offer(method.sortFiles(checkedFiles.poll(), checkedFiles.poll(), cmdArgs));
        }

        File correctName = new File(cmdArgs.getOutFileName());
        if (!checkedFiles.poll().renameTo(correctName)) {
            System.out.println("Something is wrong with new file creation!\n" +
                    "Sorted file can not be saved as: " + cmdArgs.getOutFileName() + "\n" +
                    "Saved as: " + (cmdArgs.getIterator() - 1) + cmdArgs.getOutFileName());
        }
        return true;
    }
}