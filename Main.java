import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    static Scanner scanner = new Scanner(System.in);

    private static double readDouble() {
        try {
            String token = scanner.nextLine().trim();
            return Double.parseDouble(token);
        } catch (NumberFormatException e) {
            System.out.println(Lang.get("invalid_number_format"));
            return -1;
        }
    }

    private static int readInt() {
        try {
            String token = scanner.nextLine().trim();
            return Integer.parseInt(token);
        } catch (NumberFormatException e) {
            System.out.println(Lang.get("invalid_number_format"));
            return -1;
        }
    }

    private static boolean confirmAction() {
        System.out.print(Lang.get("confirm_prompt"));
        String confirm = scanner.nextLine().trim();
        if (confirm.equalsIgnoreCase("Y")) {
            return true;
        } else {
            System.out.println(Lang.get("action_cancelled"));
            return false;
        }
    }

    public static void main(String[] args) {
        Lang.load();

        if (BankAccount.getNickname() == null) {
            System.out.print(Lang.get("enter_nickname"));
            String nick = scanner.nextLine().trim();
            BankAccount.setupNickname(nick);
        }

        if (!BankAccount.checkPin(scanner)) return;

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
            System.out.println(Lang.get("menu_15"));
            System.out.print(Lang.get("choose"));

            int choice = readInt();

            switch (choice) {
                case -1:
                    break;
                case 1:
                    System.out.print(Lang.get("balance_is") + account.getBalance());
                    if (account.getMinBalance() > 0) {
                        System.out.print(Lang.get("min_balance_is") + account.getMinBalance() + ")");
                    }
                    System.out.println();
                    break;
                case 2:
                    System.out.print(Lang.get("enter_deposit"));
                    double dep = readDouble();
                    if (dep == -1) break;
                    account.deposit(dep);
                    account.saveBalance();
                    break;
                case 3:
                    System.out.print(Lang.get("enter_withdraw"));
                    double with = readDouble();
                    if (with == -1) break;
                    account.withdraw(with);
                    account.saveBalance();
                    break;
                case 4:
                    System.out.print(Lang.get("enter_target"));
                    String target = scanner.nextLine().trim();
                    System.out.print(Lang.get("enter_transfer"));
                    double trans = readDouble();
                    if (trans == -1) break;
                    account.transfer(target, trans);
                    break;
                case 5:
                    account.saveBalance();
                    account = selectAccount();
                    break;
                case 6:
                    account.showHistory();
                    break;
                case 7:
                    System.out.print(Lang.get("enter_new_name"));
                    String newName = scanner.nextLine().trim();
                    if (!newName.isEmpty() && confirmAction()) {
                        account.rename(newName);
                    }
                    break;
                case 8:
                    System.out.print(Lang.get("enter_min_balance"));
                    double minB = readDouble();
                    if (minB == -1) break;
                    account.setMinBalance(minB);
                    account.saveBalance();
                    break;
                case 9:
                    handleCardTransfer(account);
                    break;
                case 10:
                    System.out.print(Lang.get("enter_nickname"));
                    String newNick = scanner.nextLine().trim();
                    if (!newNick.isEmpty()) {
                        BankAccount.setupNickname(newNick);
                    }
                    break;
                case 11:
                    new AIAssistant().startConversation(scanner);
                    break;
                case 12:
                    changeLanguage();
                    break;
                case 13:
                    if (confirmAction()) {
                        String nameToDelete = account.getAccountName();
                        BankAccount.deleteAccount(nameToDelete);
                        account = selectAccount();
                        if (account == null) return;
                    }
                    break;
                case 14:
                    account.saveBalance();
                    handleTurnoverMenu(account);
                    break;
                case 15:
                    account.saveBalance();
                    System.out.println(Lang.get("goodbye"));
                    return;
                default:
                    System.out.println(Lang.get("invalid"));
            }
        }
    }

    static void handleTurnoverMenu(BankAccount currentAccount) {
        System.out.println("\n" + Lang.get("turnover_menu_title"));
        System.out.println(Lang.get("turnover_opt_1"));
        System.out.println(Lang.get("turnover_opt_2"));
        System.out.println(Lang.get("turnover_opt_3"));
        System.out.print(Lang.get("choose"));
        int mode = readInt();
        if (mode == -1) return;

        ArrayList<String> allAccounts = BankAccount.getAllAccounts();

        if (mode == 1) {
            double turn = BankAccount.calculateTurnover(currentAccount.getAccountName());
            System.out.println(Lang.get("turnover_result") + currentAccount.getAccountName() + ": " + turn);
        } else if (mode == 2) {
            for (String acc : allAccounts) {
                double turn = BankAccount.calculateTurnover(acc);
                System.out.println(Lang.get("turnover_result") + acc + ": " + turn);
            }
        } else if (mode == 3) {
            System.out.println("\n" + Lang.get("your_accounts"));
            for (int i = 0; i < allAccounts.size(); i++) {
                System.out.println((i + 1) + ". " + allAccounts.get(i));
            }
            System.out.print(Lang.get("turnover_select_hint"));
            String line = scanner.nextLine().trim();
            if (line.isEmpty()) return;

            String[] indices = line.split("\\s+");
            double totalTurnover = 0;

            for (String indexStr : indices) {
                try {
                    int idx = Integer.parseInt(indexStr) - 1;
                    if (idx >= 0 && idx < allAccounts.size()) {
                        String accName = allAccounts.get(idx);
                        double turn = BankAccount.calculateTurnover(accName);
                        System.out.println(Lang.get("turnover_result") + accName + ": " + turn);
                        totalTurnover += turn;
                    }
                } catch (NumberFormatException ignored) {}
            }
            System.out.println(Lang.get("turnover_total") + totalTurnover);
        } else {
            System.out.println(Lang.get("invalid"));
        }
    }

    static void handleCardTransfer(BankAccount account) {
        ArrayList<String> quickCards = account.loadQuickCards();
        ArrayList<String> historyCards = account.parseHistoryCards();

        System.out.println("\n" + Lang.get("quick_cards_title"));
        int idx = 1;
        for (String c : quickCards) {
            System.out.println(idx + ". " + c);
            idx++;
        }

        System.out.println("\n" + Lang.get("quick_cards_history_title"));
        int historyStartIdx = idx;
        for (String c : historyCards) {
            System.out.println(idx + ". " + c);
            idx++;
        }

        System.out.println(idx + ". [" + Lang.get("enter_new_card_opt") + "]");

        System.out.print(Lang.get("choose"));
        int select = readInt();
        if (select == -1) return;

        String cardNum = "";
        if (select >= 1 && select < historyStartIdx) {
            cardNum = quickCards.get(select - 1);
        } else if (select >= historyStartIdx && select < idx) {
            cardNum = historyCards.get(select - historyStartIdx);
        } else if (select == idx) {
            System.out.print(Lang.get("enter_card"));
            cardNum = scanner.nextLine().trim();
            if (cardNum.length() != 16 || !cardNum.matches("\\d+")) {
                System.out.println(Lang.get("invalid_card"));
                return;
            }
        } else {
            System.out.println(Lang.get("invalid"));
            return;
        }

        System.out.print(Lang.get("enter_transfer"));
        double amount = readDouble();
        if (amount == -1) return;
        account.transferToCard(cardNum, amount);
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
        int ch = readInt();
        if (ch == -1) return selectAccount();
        if (ch == accounts.size() + 1) return createAccount();
        if (ch >= 1 && ch <= accounts.size()) return new BankAccount(accounts.get(ch - 1));
        System.out.println(Lang.get("invalid"));
        return selectAccount();
    }

    static BankAccount createAccount() {
        System.out.print(Lang.get("account_name"));
        String name = scanner.nextLine().trim();
        if (name.isEmpty()) return selectAccount();
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
        int l = readInt();
        if (l == -1) return;
        if (l == 1) Lang.current = "ru";
        else if (l == 2) Lang.current = "en";
        else if (l == 3) Lang.current = "uz";
        else System.out.println("Invalid/Неверно");
        Lang.save();
    }
}
