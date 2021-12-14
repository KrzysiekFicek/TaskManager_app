package pl.coderslab;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.math.NumberUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class TaskManager {
    static final String FILE_NAME = "task.csv";
    static final String[] OPTIONS = {"add", "remove", "list", "exit"};
    static String[][] tasks;

    public static void main(String[] args) throws IOException {

        loadDataToTab(FILE_NAME);
        Scanner scan = new Scanner(System.in);
        while (scan.hasNextLine()) {
            String input = scan.nextLine();
            switch (input) {
                case "exit":
                    saveTabToFile(FILE_NAME, tasks);
                    System.out.println(ConsoleColors.RED + "Good Bye");
                    System.exit(0);
                    break;
                case "add":
                    addTask();
                    break;
                case "remove":
                    removeTask(tasks, getTheNumber());
                    break;
                case "list":
                    printTab(tasks);
                    break;
                default:
                    System.out.println("Select correct option");
            }
            printOptions(OPTIONS);
        }
    }

        public static void printOptions (String[]tab){
            System.out.println(ConsoleColors.BLUE);
            System.out.println("Please select an option: " + ConsoleColors.RESET);
            for (String option : tab) {
                System.out.println(option);
            }
        }


        public static String[][] loadDataToTab (String filename) throws IOException {
            Path dir = Paths.get(filename);
            if (!Files.exists(dir)) {
                System.out.println("Files not exist");
                System.exit(0);
            }
            String[][] tab = null;
            try {
                List<String> strings = Files.readAllLines(dir);    // metoda loadData... do ponownej analizy zaczynając od List!!
                tab = new String[strings.size()][strings.get(0).split(",").length];  // czy można użyć klasy Scanner + while i zapisać do tab??

                for (int i = 0; i < strings.size(); i++) {
                    String[] split = strings.get(i).split(",");
                    for (int j = 0; j < split.length; j++) {
                        tab[i][j] = split[j];
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return tab;
        }

        public static void printTab (String[][]tab){
            for (int i = 0; i < tab.length; i++) {
                System.out.print(i + " : ");
                for (int j = 0; j < tab[i].length; j++) {
                    System.out.println(tab[i][j] + " ");
                }
                System.out.println();
            }

        }


    public static void addTask() {
        Scanner scan = new Scanner(System.in);
        System.out.println("Describe task");
        String describe = scan.nextLine();
        System.out.println("Task date");
        String data = scan.nextLine();
        System.out.println("How important is new task? true/false");
        String important = scan.nextLine();

        tasks = Arrays.copyOf(tasks, tasks.length + 1);
        tasks[tasks.length - 1] = new String[3];
        tasks[tasks.length - 1][0] = describe;
        tasks[tasks.length - 1][1] = data;
        tasks[tasks.length - 1][2] = important;
    }

    public static boolean isNumberGreaterEqualZero(String input) {
        if (NumberUtils.isParsable(input)) {
            return Integer.parseInt(input) >= 0;
        }
        return false;
    }

    public static int getTheNumber() {
        Scanner scan = new Scanner(System.in);
        System.out.println("Select number to remove");

        String number = scan.nextLine(); // czy można zastosować try-catch
        while (!isNumberGreaterEqualZero(number)) {
            System.out.println("Incorrect argument");
            scan.nextLine();
        }
        return Integer.parseInt(number);
    }

    public static void removeTask(String[][] tab, int index) {
        try {
            if (index < tab.length) {
                tasks = ArrayUtils.remove(tab, index);
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Element not exist");
        }
    }

    public static void saveTabToFile(String fileName, String[][] tab) {
        Path dir = Paths.get(fileName);

        String lines[] = new String[tab.length];
        for (int i = 0; i < tab.length; i++) {
            lines[i] = String.join(",", tab[i]);
        }
        try {
            Files.write(dir, Arrays.asList(lines));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

