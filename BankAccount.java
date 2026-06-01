import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;

public class BankAccount {

    private double balance;
    private String accountName;
    private String saveFile;
    private String historyFile;
    private ArrayList<String> history = new ArrayList<>();
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");

    private static String pinFile = "pin.txt";
    private static String accountsFile = "accounts.txt";

    public BankAccount(String accountName) {
        this.accountName = accountName;
        this.saveFile = "balance_" + accountName + ".txt";
        this.historyFile = "history_" + accountName + ".txt";
        this.balance = loadBalance();
    }

    public String getAccountName() {
        return accountName;
    }

    public static boolean checkPin() {
        Scanner scanner = new Scanner(System.in);
        String savedPin = loadPin();

        if (savedPin == null) {
            System.out.print("Создайте пин-код (4 цифры): ");
            String newPin = scanner.next();
            System.out.print("Повторите пин-код: ");
            String confirmPin = scanner.next();

            if (newPin.equals(confirmPin)) {
                savePin(newPin);
                System.out.println("Пин-код установлен!");
                return true;
            } else {
                System.out.println("Пин-коды не совпадают! Попробуйте снова.");
                return checkPin();
            }
        }

        int attempts = 3;
        while (attempts > 0) {
            System.out.print("Введите пин-код: ");
            String input = scanner.next();
            if (input.equals(savedPin)) {
                System.out.println("Добро пожаловать!");
                return true;
            } else {
                attempts--;
                if (attempts > 0) {
                    System.out.println("Неверно! Попыток осталось: " + attempts);
                } else {
                    System.out.println("Счёт заблокирован!");
                    return false;
                }
            }
        }
        return false;
    }

    private static String loadPin() {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(pinFile));
            String pin = reader.readLine();
            reader.close();
            return pin;
        } catch (Exception e) {
            return null;
        }
    }

    private static void savePin(String pin) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(pinFile));
            writer.write(pin);
            writer.close();
        } catch (Exception e) {
            System.out.println("Ошибка сохранения пин-кода!");
        }
    }

    public static ArrayList<String> getAllAccounts() {
        ArrayList<String> accounts = new ArrayList<>();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(accountsFile));
            String line;
            while ((line = reader.readLine()) != null) {
                if (!line.trim().isEmpty()) {
                    accounts.add(line.trim());
                }
            }
            reader.close();
        } catch (Exception e) {
            // файла нет — счетов нет
        }
        return accounts;
    }

    public static void saveNewAccount(String name) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(accountsFile, true));
            writer.write(name);
            writer.newLine();
            writer.close();
        } catch (Exception e) {
            System.out.println("Ошибка сохранения счёта!");
        }
    }

    private double loadBalance() {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(saveFile));
            double saved = Double.parseDouble(reader.readLine());
            reader.close();
            return saved;
        } catch (Exception e) {
            return 0;
        }
    }

    public void saveBalance() {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(saveFile));
            writer.write(String.valueOf(balance));
            writer.close();

            BufferedWriter histWriter = new BufferedWriter(new FileWriter(historyFile, true));
            for (String record : history) {
                histWriter.write(record);
                histWriter.newLine();
            }
            histWriter.close();
            history.clear();

        } catch (Exception e) {
            System.out.println("Ошибка сохранения!");
        }
    }

    public void deposit(double amount) {
        balance += amount;
        String record = "[" + LocalDateTime.now().format(formatter) + "] Пополнение: +" + amount + " | Баланс: " + balance;
        history.add(record);
        System.out.println("Пополнено: " + amount);
    }

    public void withdraw(double amount) {
        if (amount > balance) {
            System.out.println("Недостаточно средств!");
        } else {
            balance -= amount;
            String record = "[" + LocalDateTime.now().format(formatter) + "] Снятие: -" + amount + " | Баланс: " + balance;
            history.add(record);
            System.out.println("Снято: " + amount);
        }
    }

    public void showBalance() {
        System.out.println("Счёт: " + accountName + " | Баланс: " + balance);
    }

    public void showHistory() {
        System.out.println("\n--- История транзакций: " + accountName + " ---");
        try {
            BufferedReader reader = new BufferedReader(new FileReader(historyFile));
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
            reader.close();
        } catch (Exception e) {
            System.out.println("История пуста.");
        }
        for (String record : history) {
            System.out.println(record);
        }
    }
}