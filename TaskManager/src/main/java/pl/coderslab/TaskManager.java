package pl.coderslab;

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
        printOptions(OPTIONS);
    }

    public static void printOptions(String[] tab) {
        System.out.println(ConsoleColors.BLUE);
        System.out.println("Please select an option: " + ConsoleColors.RESET);
        for (String option : tab) {
            System.out.println(option);
        }
    }

    public static String[][] loadDataToTab(String filename) throws IOException {
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
    public static void printTab(String[][] tab ){
        for(int i =0; i< tab.length;i++){
            System.out.print(i + " : ");
            for(int j =0; j<tab[i].length;j++){
                System.out.println(tab[i][j] + " " );
            }
            System.out.println();
        }
        Scanner scan = new Scanner(System.in);
        while (scan.hasNextLine()){
            String input = scan.nextLine();
            switch (input){
                case "exit":
                    break;
                case "add":
                    addTask();
                    break;
                case "remove":
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
    public static void addTask(){
        Scanner scan = new Scanner(System.in);
        System.out.println("Describe task");
        String describe = scan.nextLine();
        System.out.println("Task date");
        String data = scan.nextLine();
        System.out.println("How important is new task? true/false");
        String important = scan.nextLine();

        tasks = Arrays.copyOf(tasks, tasks.length + 1);
        tasks[tasks.length -1] = new String[3];
        tasks[tasks.length - 1][0] = describe;
        tasks[tasks.length - 1][1] = data;
        tasks[tasks.length - 1][2] = important;
    }
}
