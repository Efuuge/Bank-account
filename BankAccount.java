import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.Scanner;

public class BankAccount {

    private double balance;
    private double minBalance = 0;
    private String accountName;
    private String saveFile;
    private String historyFile;
    private String quickCardsFile;
    private ArrayList<String> history = new ArrayList<>();
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");

    private static final String pinFile = "pin.txt";
    private static final String accountsFile = "accounts.txt";
    private static final String nicknameFile = "nickname.txt";

    public BankAccount(String accountName) {
        this.accountName = accountName;
        this.saveFile = "balance_" + accountName + ".txt";
        this.historyFile = "history_" + accountName + ".txt";
        this.quickCardsFile = "quick_cards_" + accountName + ".txt";
        this.balance = loadBalance();
    }

    public String getAccountName() { return accountName; }

    public void setMinBalance(double min) {
        this.minBalance = min;
        System.out.println(Lang.get("min_set") + min);
    }

    public double getMinBalance() { return minBalance; }

    public static String getNickname() {
        try (BufferedReader r = new BufferedReader(new FileReader(nicknameFile))) {
            return r.readLine().trim();
        } catch (Exception e) {
            return null;
        }
    }

    public static void setupNickname(String nick) {
        try (BufferedWriter w = new BufferedWriter(new FileWriter(nicknameFile))) {
            w.write(nick);
            System.out.println(Lang.get("nickname_set"));
        } catch (Exception ignored) {}
    }

    private double loadBalance() {
        try (BufferedReader r = new BufferedReader(new FileReader(saveFile))) {
            String bStr = r.readLine();
            String mStr = r.readLine();
            if (mStr != null) this.minBalance = Double.parseDouble(mStr);
            return Double.parseDouble(bStr);
        } catch (Exception e) {
            return 0;
        }
    }

    public void saveBalance() {
        try (BufferedWriter w = new BufferedWriter(new FileWriter(saveFile))) {
            w.write(balance + "\n" + minBalance);
        } catch (Exception ignored) {}
    }

    public double getBalance() {
        return this.balance;
    }

    private boolean hasSufficientFunds(double amount) {
        return (this.balance - amount) >= this.minBalance;
    }

    public void deposit(double amount) {
        if (amount <= 0) {
            System.out.println(Lang.get("invalid_amount"));
            return;
        }
        this.balance += amount;
        addHistory("+" + amount + " [" + LocalDateTime.now().format(formatter) + "]");
        System.out.println(Lang.get("deposited") + amount);
    }

    public void withdraw(double amount) {
        if (amount <= 0) {
            System.out.println(Lang.get("invalid_amount"));
            return;
        }
        if (!hasSufficientFunds(amount)) {
            System.out.println(Lang.get("insufficient"));
            return;
        }
        this.balance -= amount;
        addHistory("-" + amount + " [" + LocalDateTime.now().format(formatter) + "]");
        System.out.println(Lang.get("withdrawn") + amount);
    }

    public void transfer(String targetAccountName, double amount) {
        if (amount <= 0) {
            System.out.println(Lang.get("invalid_amount"));
            return;
        }
        ArrayList<String> all = getAllAccounts();
        if (!all.contains(targetAccountName)) {
            System.out.println(Lang.get("target_not_found"));
            return;
        }
        if (!hasSufficientFunds(amount)) {
            System.out.println(Lang.get("insufficient"));
            return;
        }

        this.balance -= amount;
        saveBalance();
        addHistory("->" + targetAccountName + ": " + amount + " [" + LocalDateTime.now().format(formatter) + "]");

        BankAccount target = new BankAccount(targetAccountName);
        target.balance += amount;
        target.saveBalance();
        target.addHistory("<-" + this.accountName + ": " + amount + " [" + LocalDateTime.now().format(formatter) + "]");

        System.out.println(Lang.get("transfer_success") + amount + Lang.get("to_account") + targetAccountName);
    }

    public void transferToCard(String cardNum, double amount) {
        if (amount <= 0) {
            System.out.println(Lang.get("invalid_amount"));
            return;
        }
        if (!hasSufficientFunds(amount)) {
            System.out.println(Lang.get("insufficient"));
            return;
        }

        this.balance -= amount;
        saveBalance();
        addHistory("->Card " + cardNum + ": " + amount + " [" + LocalDateTime.now().format(formatter) + "]");
        saveQuickCard(cardNum);
        System.out.println(Lang.get("transfer_success") + amount + " " + Lang.get("to_account") + "Card " + cardNum);
    }

    private void addHistory(String record) {
        history.add(record);
        try (BufferedWriter w = new BufferedWriter(new FileWriter(historyFile, true))) {
            w.write(record + "\n");
        } catch (Exception ignored) {}
    }

    public void showHistory() {
        boolean hasHistory = false;
        try (BufferedReader r = new BufferedReader(new FileReader(historyFile))) {
            String line;
            while ((line = r.readLine()) != null) {
                System.out.println(line);
                hasHistory = true;
            }
        } catch (Exception ignored) {}
        if (!hasHistory) System.out.println(Lang.get("history_empty"));
    }

    // FIX 1: поменян порядок проверок — "->" и "<-" проверяются раньше чем "-" и "+"
    public static double calculateTurnover(String name) {
        double total = 0;
        try (BufferedReader r = new BufferedReader(new FileReader("history_" + name + ".txt"))) {
            String line;
            while ((line = r.readLine()) != null) {
                if (line.startsWith("->") || line.startsWith("<-")) {
                    String[] parts = line.split(" ");
                    String val = parts[0].replace("->", "").replace("<-", "");
                    if (val.contains(":") || val.isEmpty()) val = parts[1];
                    total += Double.parseDouble(val);
                } else if (line.startsWith("+") || line.startsWith("-")) {
                    String[] parts = line.split(" ");
                    String val = parts[0].replace("+", "").replace("-", "");
                    total += Double.parseDouble(val);
                }
            }
        } catch (Exception ignored) {}
        return total;
    }

    public ArrayList<String> loadQuickCards() {
        ArrayList<String> cards = new ArrayList<>();
        try (BufferedReader r = new BufferedReader(new FileReader(quickCardsFile))) {
            String line;
            while ((line = r.readLine()) != null) {
                if (!line.trim().isEmpty()) cards.add(line.trim());
            }
        } catch (Exception ignored) {}
        return cards;
    }

    public ArrayList<String> parseHistoryCards() {
        LinkedHashSet<String> cardsSet = new LinkedHashSet<>();
        try (BufferedReader r = new BufferedReader(new FileReader(historyFile))) {
            String line;
            while ((line = r.readLine()) != null) {
                if (line.startsWith("->Card ")) {
                    String card = line.substring(7, 23);
                    cardsSet.add(card);
                }
            }
        } catch (Exception ignored) {}

        ArrayList<String> result = new ArrayList<>(cardsSet);
        if (result.size() > 10) {
            return new ArrayList<>(result.subList(result.size() - 10, result.size()));
        }
        return result;
    }

    private void saveQuickCard(String cardNum) {
        ArrayList<String> current = loadQuickCards();
        current.remove(cardNum);
        current.add(0, cardNum);
        if (current.size() > 5) current.remove(current.size() - 1);

        try (BufferedWriter w = new BufferedWriter(new FileWriter(quickCardsFile))) {
            for (String c : current) w.write(c + "\n");
        } catch (Exception ignored) {}
    }

    public void rename(String newName) {
        ArrayList<String> accounts = getAllAccounts();
        if (accounts.contains(newName)) {
            System.out.println(Lang.get("error_rename_exists"));
            return;
        }

        ensureFileExists(saveFile);
        ensureFileExists(historyFile);
        ensureFileExists(quickCardsFile);

        boolean s1 = new File(saveFile).renameTo(new File("balance_" + newName + ".txt"));
        boolean s2 = new File(historyFile).renameTo(new File("history_" + newName + ".txt"));
        boolean s3 = new File(quickCardsFile).renameTo(new File("quick_cards_" + newName + ".txt"));

        if (s1 && s2 && s3) {
            int idx = accounts.indexOf(accountName);
            if (idx != -1) accounts.set(idx, newName);
            writeAllAccounts(accounts);
            this.accountName = newName;
            this.saveFile = "balance_" + newName + ".txt";
            this.historyFile = "history_" + newName + ".txt";
            this.quickCardsFile = "quick_cards_" + newName + ".txt";
            System.out.println(Lang.get("renamed") + newName);
        } else {
            System.out.println(Lang.get("error_io_failed"));
        }
    }

    private void ensureFileExists(String path) {
        File file = new File(path);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException ignored) {}
        }
    }

    public static void deleteAccount(String name) {
        new File("balance_" + name + ".txt").delete();
        new File("history_" + name + ".txt").delete();
        new File("quick_cards_" + name + ".txt").delete();
        ArrayList<String> accounts = getAllAccounts();
        accounts.remove(name);
        writeAllAccounts(accounts);
        System.out.println(Lang.get("account_deleted"));
    }

    public static ArrayList<String> getAllAccounts() {
        ArrayList<String> list = new ArrayList<>();
        try (BufferedReader r = new BufferedReader(new FileReader(accountsFile))) {
            String line;
            while ((line = r.readLine()) != null) {
                if (!line.trim().isEmpty()) list.add(line.trim());
            }
        } catch (Exception ignored) {}
        return list;
    }

    public static void saveNewAccount(String name) {
        ArrayList<String> list = getAllAccounts();
        if (!list.contains(name)) {
            list.add(name);
            writeAllAccounts(list);
        }
    }

    private static void writeAllAccounts(ArrayList<String> list) {
        try (BufferedWriter w = new BufferedWriter(new FileWriter(accountsFile))) {
            for (String s : list) w.write(s + "\n");
        } catch (Exception ignored) {}
    }

    public static boolean checkPin(Scanner sc) {
        try (BufferedReader r = new BufferedReader(new FileReader(pinFile))) {
            String line = r.readLine();
            String[] p = line.split(":");
            String savedPin = p[0];
            int attempts = Integer.parseInt(p[1]);

            if (attempts <= 0) {
                System.out.println(Lang.get("blocked"));
                return false;
            }

            while (attempts > 0) {
                System.out.print(Lang.get("enter_pin"));
                String input = sc.nextLine().trim();
                if (input.equals(savedPin)) {
                    if (attempts < 3) updatePinFile(savedPin, 3);
                    return true;
                }
                attempts--;
                updatePinFile(savedPin, attempts);
                if (attempts > 0) {
                    System.out.println(Lang.get("wrong_pin") + attempts);
                }
            }
            System.out.println(Lang.get("blocked"));
            return false;
        } catch (Exception e) {
            setupPin(sc);
            return true;
        }
    }

    private static void setupPin(Scanner sc) {
        while (true) {
            System.out.print(Lang.get("create_pin"));
            String p1 = sc.nextLine().trim();
            if (p1.length() != 4 || !p1.matches("\\d+")) {
                System.out.println(Lang.get("invalid_pin_format"));
                continue;
            }
            System.out.print(Lang.get("repeat_pin"));
            String p2 = sc.nextLine().trim();
            if (p1.equals(p2)) {
                updatePinFile(p1, 3);
                System.out.println(Lang.get("pin_set"));
                break;
            }
            System.out.println(Lang.get("pins_no_match"));
        }
    }

    private static void updatePinFile(String pin, int attempts) {
        try (BufferedWriter w = new BufferedWriter(new FileWriter(pinFile))) {
            w.write(pin + ":" + attempts);
        } catch (Exception ignored) {}
    }
}
