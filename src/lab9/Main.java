package lab9;

import java.io.*;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        // Задаем имена файлов
        File inputFile = new File("input.txt");
        File outputFile = new File("result.txt");

        // Карта для хранения слов и их количества
        Map<String, Integer> wordCounts = new HashMap<>();

        System.out.println("обработкa файла");

        // Используем try-with-resources для авто-закрытия потоков (Критерий ЛР)
        // 1-й тип исключения: FileNotFoundException (если входного файла нет)
        // 2-й тип исключения: IOException (общие проблемы ввода-вывода)
        try (BufferedReader reader = new BufferedReader(new FileReader(inputFile));
             BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile))) {

            String line;
            // Читаем файл построчно
            while ((line = reader.readLine()) != null) {
                // Очищаем строку от лишних знаков и бьем на слова по пробелам
                String[] words = line.toLowerCase().replaceAll("[^a-zA-Zа-яА-ЯёЁ\\s]", "").split("\\s+");

                for (String word : words) {
                    if (!word.isEmpty()) {
                        // Увеличиваем счетчик для каждого слова
                        wordCounts.put(word, wordCounts.getOrDefault(word, 0) + 1);
                    }
                }
            }

            // Переносим данные в список для сортировки
            List<Map.Entry<String, Integer>> list = new ArrayList<>(wordCounts.entrySet());

            // Сортируем слова по убыванию частоты (от частых к редким)
            list.sort((a, b) -> b.getValue().compareTo(a.getValue()));

            // Записываем результат в файл result.txt
            for (Map.Entry<String, Integer> entry : list) {
                writer.write(entry.getKey() + ": " + entry.getValue());
                writer.newLine();
            }

            System.out.println("Успешно! Результаты сохранены в файл: " + outputFile.getName());

        } catch (FileNotFoundException e) {
            System.err.println("Ошибка 1: файл 'input.txt' не найден! Создайте его рядом с кодом.");
        } catch (SecurityException e) {
            // 3-й тип исключения: Ошибка доступа (если файл защищен от чтения/записи)
            System.err.println("Ошибка 2: Нет прав доступа к файлам.");
        } catch (IOException e) {
            System.err.println("Ошибка 3: Произошла непредвиденная ошибка при чтении или записи файла.");
        }
    }
}
