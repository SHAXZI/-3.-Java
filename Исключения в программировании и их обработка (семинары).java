import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class UserDataApp {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String input;

        System.out.println("Введите данные (Фамилия Имя Отчество датарождения номертелефона пол): ");
        input = scanner.nextLine();

        String[] data = input.split("\\s+");

        if (data.length < 6) {
            System.out.println("Ошибка: Вы ввели меньше данных, чем требуется.");
            return;
        } else if (data.length > 6) {
            System.out.println("Ошибка: Вы ввели больше данных, чем требуется.");
            return;
        }

        try {
            String lastName = data[0];
            String firstName = data[1];
            String middleName = data[2];
            String birthDate = validateDate(data[3]);
            long phoneNumber = validatePhoneNumber(data[4]);
            char gender = validateGender(data[5]);

            writeToFile(lastName, firstName, middleName, birthDate, phoneNumber, gender);
            System.out.println("Данные успешно записаны!");

        } catch (IllegalArgumentException e) {
            System.out.println("Ошибка: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("Ошибка записи в файл: " + e.getMessage());
        } finally {
            scanner.close();
        }
    }

    private static String validateDate(String date) {
        if (!date.matches("\\d{2}\\.\\d{2}\\.\\d{4}")) {
            throw new IllegalArgumentException("Неверный формат даты. Ожидается dd.mm.yyyy.");
        }
        return date;
    }

    private static long validatePhoneNumber(String phone) {
        try {
            return Long.parseUnsignedLong(phone);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Неверный номер телефона. Ожидается целое число.");
        }
    }

    private static char validateGender(String gender) {
        if (gender.length() != 1 || (gender.charAt(0) != 'f' && gender.charAt(0) != 'm')) {
            throw new IllegalArgumentException("Неверный пол. Ожидается 'f' или 'm'.");
        }
        return gender.charAt(0);
    }

    private static void writeToFile(String lastName, String firstName, String middleName,
                                     String birthDate, long phoneNumber, char gender) throws IOException {
        String fileName = lastName + ".txt";
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, true))) {
            writer.write(String.format("%s %s %s %s %d %c", lastName, firstName, middleName, birthDate, phoneNumber, gender));
            writer.newLine();
        }
    }
}