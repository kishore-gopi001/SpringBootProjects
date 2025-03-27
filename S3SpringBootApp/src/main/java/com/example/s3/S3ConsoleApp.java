package com.example.s3;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import java.util.Scanner;

@SpringBootApplication
public class S3ConsoleApp implements CommandLineRunner {
    private final S3Service s3Service;

    public S3ConsoleApp(S3Service s3Service) {
        this.s3Service = s3Service;
    }

    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(S3ConsoleApp.class, args);
        S3ConsoleApp app = context.getBean(S3ConsoleApp.class);
        app.runConsole();
    }

    private void runConsole() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("\nAWS S3 Console Application");
            System.out.println("1. Upload File");
            System.out.println("2. Download File");
            System.out.println("3. Delete File");
            System.out.println("4. List Files");
            System.out.println("5. Exit");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1 -> {
                    System.out.print("Enter file path to upload: ");
                    String filePath = scanner.nextLine();
                    System.out.print("Enter S3 key (filename in S3): ");
                    String key = scanner.nextLine();
                    s3Service.uploadFile(key, filePath);
                }
                case 2 -> {
                    System.out.print("Enter S3 key of the file to download: ");
                    String key = scanner.nextLine();
                    System.out.print("Enter local path to save the file: ");
                    String downloadPath = scanner.nextLine();
                    s3Service.downloadFile(key, downloadPath);
                }
                case 3 -> {
                    System.out.print("Enter S3 key of the file to delete: ");
                    String key = scanner.nextLine();
                    s3Service.deleteFile(key);
                }
                case 4 -> s3Service.listFiles();
                case 5 -> {
                    System.out.println("Exiting...");
                    return;
                }
                default -> System.out.println("Invalid choice! Please enter a valid option.");
            }
        }
    }

    @Override
    public void run(String... args) {}
}