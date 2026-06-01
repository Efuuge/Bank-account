import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;

public class BankAccount {

    private double balance;
    private double minBalance = 0;
    private String accountName;
    private String saveFile;
    private String historyFile;
    private ArrayList<String> history = new ArrayList<>();
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");

    private static String pinFile = "pin.txt";
    private static String accountsFile = "accounts.txt";
    private static String nicknameFile = "nickname.txt";

    public BankAccount(String accountName) {
        this.accountName = accountName;
        this.saveFile = "balance_" + accountName + ".txt";
        this.historyFile = "history_" + accountName + ".txt";
        this.balance = loadBalance();
    }

    public String getAccountName() { return accountName; }
    public void setMinBalance(double min) {
        this.minBalance = min;
        System.out.println(Lang.get("min_set") + min);
    }
    public double getMinBalance() { return minBalance; }

    // ─── NICKNAME ────────────────────────────────────────────────
    public static String getNickname() {
        try {
            BufferedReader r = new BufferedReader(new FileReader(nicknameFile));
            String n = r.readLine(); r.close(); return n;
        } catch (Exception e) { return null; }
    }

    public static void setupNickname() {
        Scanner sc = new Scanner(System.in);
        System.out.print(Lang.get("enter_nickname"));
        String n = sc.next();
        try {
            BufferedWriter w = new BufferedWriter(new FileWriter(nicknameFile));
            w.write(n); w.close();
            System.out.println(Lang.get("nick_saved") + n);
        } catch (Exception ignored) {}
    }

    public static void changeNickname() {
        Scanner sc = new Scanner(System.in);
        System.out.print(Lang.get("new_nick"));
        String n = sc.next();
        try {
            BufferedWriter w = new BufferedWriter(new FileWriter(nicknameFile));
            w.write(n); w.close();
            System.out.println(Lang.get("nick_changed") + n);
        } catch (Exception ignored) {}
    }

    // ─── PIN ─────────────────────────────────────────────────────
    public static boolean checkPin() {
        Scanner sc = new Scanner(System.in);
        String saved = loadPin();
        if (saved == null) {
            System.out.print(Lang.get("create_pin"));
            String p1 = sc.next();
            System.out.print(Lang.get("repeat_pin"));
            String p2 = sc.next();
            if (p1.equals(p2)) { savePin(p1); System.out.println(Lang.get("pin_set")); return true; }
            else { System.out.println(Lang.get("pins_no_match")); return checkPin(); }
        }
        int attempts = 3;
        while (attempts > 0) {
            System.out.print(Lang.get("enter_pin"));
            String input = sc.next();
            if (input.equals(saved)) {
                String nick = getNickname();
                System.out.println(Lang.get("welcome") + (nick != null ? nick : "") + "!");
                return true;
            }
            attempts--;
            if (attempts > 0) System.out.println(Lang.get("wrong_pin") + attempts);
            else System.out.println(Lang.get("blocked"));
        }
        return false;
    }

    public static void changePin() {
        Scanner sc = new Scanner(System.in);
        String saved = loadPin();
        System.out.print(Lang.get("old_pin"));
        if (!sc.next().equals(saved)) { System.out.println(Lang.get("wrong_old_pin")); return; }
        System.out.print(Lang.get("new_pin"));
        String p1 = sc.next();
        System.out.print(Lang.get("repeat_pin"));
        String p2 = sc.next();
        if (p1.equals(p2)) { savePin(p1); System.out.println(Lang.get("pin_changed")); }
        else System.out.println(Lang.get("pins_no_match"));
    }

    private static String loadPin() {
        try {
            BufferedReader r = new BufferedReader(new FileReader(pinFile));
            String p = r.readLine(); r.close(); return p;
        } catch (Exception e) { return null; }
    }

    private static void savePin(String pin) {
        try {
            BufferedWriter w = new BufferedWriter(new FileWriter(pinFile));
            w.write(pin); w.close();
        } catch (Exception ignored) {}
    }

    // ─── ACCOUNTS LIST ────────────────────────────────────────────
    public static ArrayList<String> getAllAccounts() {
        ArrayList<String> list = new ArrayList<>();
        try {
            BufferedReader r = new BufferedReader(new FileReader(accountsFile));
            String line;
            while ((line = r.readLine()) != null) if (!line.trim().isEmpty()) list.add(line.trim());
            r.close();
        } catch (Exception ignored) {}
        return list;
    }

    public static void saveNewAccount(String name) {
        try {
            BufferedWriter w = new BufferedWriter(new FileWriter(accountsFile, true));
            w.write(name); w.newLine(); w.close();
        } catch (Exception ignored) {}
    }

    private static void writeAllAccounts(ArrayList<String> accounts) {
        try {
            BufferedWriter w = new BufferedWriter(new FileWriter(accountsFile));
            for (String a : accounts) { w.write(a); w.newLine(); }
            w.close();
        } catch (Exception ignored) {}
    }

    // ─── BALANCE ─────────────────────────────────────────────────
    private double loadBalance() {
        try {
            BufferedReader r = new BufferedReader(new FileReader(saveFile));
            double b = Double.parseDouble(r.readLine()); r.close(); return b;
        } catch (Exception e) { return 0; }
    }

    public void saveBalance() {
        try {
            BufferedWriter w = new BufferedWriter(new FileWriter(saveFile));
            w.write(String.valueOf(balance)); w.close();
            BufferedWriter hw = new BufferedWriter(new FileWriter(historyFile, true));
            for (String rec : history) { hw.write(rec); hw.newLine(); }
            hw.close();
            history.clear();
        } catch (Exception ignored) {}
    }

    public void showBalance() {
        System.out.println(Lang.get("balance_info") + accountName +
                " | " + Lang.get("balance_label") + balance +
                " | " + Lang.get("min_label") + minBalance);
    }

    // ─── OPERATIONS ──────────────────────────────────────────────
    private String timestamp() {
        return "[" + LocalDateTime.now().format(formatter) + "] ";
    }

    public void deposit(double amount) {
        balance += amount;
        history.add(timestamp() + "+ " + amount + " | " + Lang.get("balance_label") + balance);
        System.out.println(Lang.get("deposited") + amount);
    }

    public void withdraw(double amount) {
        if (amount > balance) {
            history.add(timestamp() + "[" + Lang.get("cancelled_label") + "] " + Lang.get("no_funds") + " (" + amount + ")");
            System.out.println(Lang.get("no_funds"));
        } else if (balance - amount < minBalance) {
            history.add(timestamp() + "[" + Lang.get("cancelled_label") + "] " + Lang.get("below_min") + minBalance + " (" + amount + ")");
            System.out.println(Lang.get("below_min") + minBalance);
        } else {
            balance -= amount;
            history.add(timestamp() + "- " + amount + " | " + Lang.get("balance_label") + balance);
            System.out.println(Lang.get("withdrawn") + amount);
        }
    }

    public void transfer(String targetName, double amount) {
        ArrayList<String> accounts = getAllAccounts();
        if (!accounts.contains(targetName)) {
            history.add(timestamp() + "[" + Lang.get("cancelled_label") + "] " + Lang.get("acc_not_found") + " -> " + targetName);
            System.out.println(Lang.get("acc_not_found"));
            return;
        }
        if (targetName.equals(accountName)) {
            history.add(timestamp() + "[" + Lang.get("cancelled_label") + "] " + Lang.get("no_self"));
            System.out.println(Lang.get("no_self"));
            return;
        }
        if (amount > balance || balance - amount < minBalance) {
            history.add(timestamp() + "[" + Lang.get("cancelled_label") + "] " + Lang.get("no_funds") + " -> " + targetName + " (" + amount + ")");
            System.out.println(Lang.get("no_funds"));
            return;
        }
        balance -= amount;
        addToAccount(targetName, amount);
        history.add(timestamp() + "-> " + targetName + " : " + amount + " | " + Lang.get("balance_label") + balance);
        logToAccount(targetName, timestamp() + "<- " + accountName + " : " + amount);
        System.out.println(Lang.get("transfer_ok") + amount + " -> " + targetName);
    }

    private static void addToAccount(String name, double amount) {
        String file = "balance_" + name + ".txt";
        try {
            double cur = 0;
            try {
                BufferedReader r = new BufferedReader(new FileReader(file));
                cur = Double.parseDouble(r.readLine()); r.close();
            } catch (Exception ignored) {}
            BufferedWriter w = new BufferedWriter(new FileWriter(file));
            w.write(String.valueOf(cur + amount)); w.close();
        } catch (Exception ignored) {}
    }

    private static void logToAccount(String name, String record) {
        try {
            BufferedWriter w = new BufferedWriter(new FileWriter("history_" + name + ".txt", true));
            w.write(record); w.newLine(); w.close();
        } catch (Exception ignored) {}
    }

    public void logCancelled(String operation) {
        history.add(timestamp() + "[" + Lang.get("cancelled_label") + "] " + operation);
        saveBalance();
    }

    // ─── HISTORY ─────────────────────────────────────────────────
    public void showHistory() {
        System.out.println("\n--- " + Lang.get("history_title") + accountName + " ---");
        boolean hasHistory = false;
        try {
            BufferedReader r = new BufferedReader(new FileReader(historyFile));
            String line;
            while ((line = r.readLine()) != null) { System.out.println(line); hasHistory = true; }
            r.close();
        } catch (Exception ignored) {}
        for (String rec : history) { System.out.println(rec); hasHistory = true; }
        if (!hasHistory) System.out.println(Lang.get("history_empty"));
    }

    // ─── RENAME & DELETE ─────────────────────────────────────────
    public void rename(String newName) {
        new File(saveFile).renameTo(new File("balance_" + newName + ".txt"));
        new File(historyFile).renameTo(new File("history_" + newName + ".txt"));
        ArrayList<String> accounts = getAllAccounts();
        int idx = accounts.indexOf(accountName);
        if (idx != -1) accounts.set(idx, newName);
        writeAllAccounts(accounts);
        this.accountName = newName;
        this.saveFile = "balance_" + newName + ".txt";
        this.historyFile = "history_" + newName + ".txt";
        System.out.println(Lang.get("renamed") + newName);
    }

    public static void deleteAccount(String name) {
        new File("balance_" + name + ".txt").delete();
        new File("history_" + name + ".txt").delete();
        ArrayList<String> accounts = getAllAccounts();
        accounts.remove(name);
        writeAllAccounts(accounts);
        System.out.println(Lang.get("deleted"));
    }
}
