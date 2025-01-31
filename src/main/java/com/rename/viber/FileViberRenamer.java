package com.rename.viber;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FileViberRenamer {

    public static void main(String[] args) {
        // Задайте шлях до папки з файлами
        String path = "e:\\test\\img";

        // Викликаємо метод для перейменування
        renameFiles(path);
    }

    public static void renameFiles(String folderPath) {
        // Патерн для перевірки імен файлів
        Pattern pattern = Pattern.compile(".*_viber_(\\d{4})-(\\d{2})-(\\d{2})_.*\\.jpg");

        // Отримуємо список файлів у папці
        File folder = new File(folderPath);
        File[] files = folder.listFiles((dir, name) -> name.endsWith(".jpg"));

        if (files == null || files.length == 0) {
            System.out.println("У папці немає файлів або вона не існує.");
            return;
        }

        // Сортуємо файли для забезпечення правильного порядку
        Arrays.sort(files, Comparator.comparing(File::getName));

        // Карта для відстеження лічильників за датою
        Map<String, Integer> dateCounters = new HashMap<>();

        for (File file : files) {
            Matcher matcher = pattern.matcher(file.getName());
            if (matcher.matches()) {
                // Отримуємо дату з імені
                String year = matcher.group(1);
                String month = matcher.group(2);
                String day = matcher.group(3);

                // Формуємо ключ дати
                String dateKey = String.format("%s.%s.%s", year, month, day);

                // Отримуємо лічильник для цієї дати, або встановлюємо 1
                int counter = dateCounters.getOrDefault(dateKey, 0) + 1;
                dateCounters.put(dateKey, counter);

                // Формуємо нове ім'я файлу
                String newName = String.format("%s_%04d.jpg", dateKey, counter);
                Path sourcePath = file.toPath();
                Path targetPath = Paths.get(folderPath, newName);

                try {
                    Files.move(sourcePath, targetPath);
                    System.out.println("Перейменовано: " + sourcePath.getFileName() + " -> " + newName);
                } catch (IOException e) {
                    System.err.println("Помилка перейменування файлу: " + sourcePath.getFileName());
                    e.printStackTrace();
                }
            } else {
                System.out.println("Файл не відповідає шаблону: " + file.getName());
            }
        }
    }
}
