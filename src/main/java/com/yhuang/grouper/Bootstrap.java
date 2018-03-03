package com.yhuang.grouper;

public class Bootstrap {
    public Bootstrap() {
    }

    private void groupProcess(String fileName) throws GrouperException{
        Grouper grouper = new Grouper(fileName);

        // approach 1:
        System.out.println("\n\n-------use approach 1, by sign, sort, squash ----------");
        grouper.process();

        // approach 2:
        System.out.println("\n\n-------use approach 2, by hash ----------");
        grouper.processGroupAtLoading();
    }

    private static void printUsage() {
        System.out.println("Usage:");
        System.out.println("./grouper.sh input_file");
        System.out.println("./grouper.sh h");
        System.exit(0);
    }

    private static void validateArguments(String[] args) throws GrouperException {
        if (args.length > 0) {
            if(args[0].equalsIgnoreCase("h")) {
                printUsage();
            }
        } else {
            throw new GrouperException("filename is not specified");
        }
    }

    public static void main(String[] args) {
        try {
            validateArguments(args);

            Bootstrap starter = new Bootstrap();
            String fileName = args[0];

            // for test
            fileName = "input_file";
            starter.groupProcess(fileName);

        } catch (GrouperException ex) {
            System.out.println("has error in process the file " + ex.getMessage());
        }
    }
}