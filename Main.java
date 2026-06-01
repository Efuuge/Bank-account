import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        Lang.load();

        if (BankAccount.getNickname() == null) BankAccount.setupNickname();
        if (!BankAccount.checkPin()) return;

        BankAccount account = selectAccount();
        if (account == null) return;

        while (true) {
            String nick = BankAccount.getNickname();
            System.out.println("\n=== " + (nick != null ? nick + " | " : "") + account.getAccountName() + " ===");
            System.out.println(Lang.get("menu_1"));
            System.out.println(Lang.get("menu_2"));
            System.out.println(Lang.get("menu_3"));
            System.out.println(Lang.get("menu_4"));
            System.out.println(Lang.get("menu_5"));
            System.out.println(Lang.get("menu_6"));
            System.out.println(Lang.get("menu_7"));
            System.out.println(Lang.get("menu_8"));
            System.out.println(Lang.get("menu_9"));
            System.out.println(Lang.get("menu_10"));
            System.out.println(Lang.get("menu_11"));
            System.out.println(Lang.get("menu_12"));
            System.out.println(Lang.get("menu_13"));
            System.out.println(Lang.get("menu_14"));
            System.out.print(Lang.get("choose"));

            int choice = scanner.nextInt();

            if (choice == 1) {
                account.showBalance();

            } else if (choice == 2) {
                System.out.print(Lang.get("amount"));
                account.deposit(scanner.nextDouble());
                account.saveBalance();

            } else if (choice == 3) {
                System.out.print(Lang.get("amount"));
                account.withdraw(scanner.nextDouble());
                account.saveBalance();

            } else if (choice == 4) {
                System.out.print(Lang.get("transfer_to"));
                String target = scanner.next();
                System.out.print(Lang.get("transfer_amount"));
                double amount = scanner.nextDouble();
                account.transfer(target, amount);
                account.saveBalance();

            } else if (choice == 5) {
                account.showHistory();

            } else if (choice == 6) {
                account.saveBalance();
                account = selectAccount();
                if (account == null) break;

            } else if (choice == 7) {
                System.out.print(Lang.get("new_name"));
                account.rename(scanner.next());

            } else if (choice == 8) {
                System.out.print(Lang.get("enter_min"));
                account.setMinBalance(scanner.nextDouble());

            } else if (choice == 9) {
                BankAccount.changePin();

            } else if (choice == 10) {
                BankAccount.changeNickname();

            } else if (choice == 11) {
                new AIAssistant().startConversation();

            } else if (choice == 12) {
                changeLanguage();

            } else if (choice == 13) {
                System.out.print(Lang.get("confirm_delete"));
                String ans = scanner.next();
                if (Lang.isYes(ans)) {
                    String name = account.getAccountName();
                    account.saveBalance();
                    BankAccount.deleteAccount(name);
                    account = selectAccount();
                    if (account == null) break;
                } else {
                    account.logCancelled("Delete account");
                    System.out.println(Lang.get("cancelled_op"));
                }

            } else if (choice == 14) {
                account.saveBalance();
                System.out.println(Lang.get("goodbye"));
                break;
            }
        }
    }

    static BankAccount selectAccount() {
        ArrayList<String> accounts = BankAccount.getAllAccounts();
        if (accounts.isEmpty()) {
            System.out.println(Lang.get("no_accounts"));
            return createAccount();
        }
        System.out.println("\n" + Lang.get("your_accounts"));
        for (int i = 0; i < accounts.size(); i++)
            System.out.println((i + 1) + ". " + accounts.get(i));
        System.out.println((accounts.size() + 1) + Lang.get("create_new"));
        System.out.print(Lang.get("choose"));
        int ch = scanner.nextInt();
        if (ch == accounts.size() + 1) return createAccount();
        if (ch >= 1 && ch <= accounts.size()) return new BankAccount(accounts.get(ch - 1));
        System.out.println(Lang.get("invalid"));
        return selectAccount();
    }

    static BankAccount createAccount() {
        System.out.print(Lang.get("account_name"));
        String name = scanner.next();
        BankAccount.saveNewAccount(name);
        System.out.println(Lang.get("account_created") + name);
        return new BankAccount(name);
    }

    static void changeLanguage() {
        System.out.println(Lang.get("lang_select"));
        System.out.println("1. Русский");
        System.out.println("2. English");
        System.out.println("3. O'zbek");
        System.out.print(Lang.get("choose"));
        int ch = scanner.nextInt();
        if (ch == 1) Lang.current = "ru";
        else if (ch == 2) Lang.current = "en";
        else if (ch == 3) Lang.current = "uz";
        Lang.save();
        System.out.println(Lang.get("lang_changed"));
    }
}
