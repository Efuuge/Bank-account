import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {

        if (!BankAccount.checkPin()) {
            return;
        }

        BankAccount account = selectAccount();
        if (account == null) return;

        while (true) {
            System.out.println("\n--- Банк | Счёт: " + account.getAccountName() + " ---");
            System.out.println("1. Показать баланс");
            System.out.println("2. Пополнить счёт");
            System.out.println("3. Снять деньги");
            System.out.println("4. История транзакций");
            System.out.println("5. Сменить счёт");
            System.out.println("6. Удалить текущий счёт");
            System.out.println("7. Выйти");
            System.out.print("Выберите: ");

            int choice = scanner.nextInt();

            if (choice == 1) {
                account.showBalance();
            } else if (choice == 2) {
                System.out.print("Сумма: ");
                double amount = scanner.nextDouble();
                account.deposit(amount);
                account.saveBalance();
            } else if (choice == 3) {
                System.out.print("Сумма: ");
                double amount = scanner.nextDouble();
                account.withdraw(amount);
                account.saveBalance();
            } else if (choice == 4) {
                account.showHistory();
            } else if (choice == 5) {
                account.saveBalance();
                account = selectAccount();
                if (account == null) break;
            } else if (choice == 6) {
                deleteAccount(account);
                account = selectAccount();
                if (account == null) break;
            } else if (choice == 7) {
                account.saveBalance();
                System.out.println("До свидания!");
                break;
            }
        }
    }

    public static BankAccount selectAccount() {
        ArrayList<String> accounts = BankAccount.getAllAccounts();

        if (accounts.isEmpty()) {
            System.out.println("Счетов нет. Создайте первый счёт.");
            return createAccount();
        }

        System.out.println("\n--- Ваши счета ---");
        for (int i = 0; i < accounts.size(); i++) {
            System.out.println((i + 1) + ". " + accounts.get(i));
        }
        System.out.println((accounts.size() + 1) + ". Создать новый счёт");
        System.out.print("Выберите: ");

        int choice = scanner.nextInt();

        if (choice == accounts.size() + 1) {
            return createAccount();
        } else if (choice >= 1 && choice <= accounts.size()) {
            String name = accounts.get(choice - 1);
            System.out.println("Выбран счёт: " + name);
            return new BankAccount(name);
        } else {
            System.out.println("Неверный выбор!");
            return selectAccount();
        }
    }

    public static BankAccount createAccount() {
        System.out.print("Введите название счёта: ");
        String name = scanner.next();
        BankAccount.saveNewAccount(name);
        System.out.println("Счёт \"" + name + "\" создан!");
        return new BankAccount(name);
    }

    public static void deleteAccount(BankAccount account) {
        System.out.print("Вы уверены что хотите удалить счёт \"" + account.getAccountName() + "\"? (да/нет): ");
        String confirm = scanner.next();

        if (confirm.equals("да")) {
            new File("balance_" + account.getAccountName() + ".txt").delete();
            new File("history_" + account.getAccountName() + ".txt").delete();

            ArrayList<String> accounts = BankAccount.getAllAccounts();
            accounts.remove(account.getAccountName());

            try {
                BufferedWriter writer = new BufferedWriter(new FileWriter("accounts.txt"));
                for (String acc : accounts) {
                    writer.write(acc);
                    writer.newLine();
                }
                writer.close();
            } catch (Exception e) {
                System.out.println("Ошибка удаления!");
            }

            System.out.println("Счёт \"" + account.getAccountName() + "\" удалён!");
        } else {
            System.out.println("Отменено.");
        }
    }
}