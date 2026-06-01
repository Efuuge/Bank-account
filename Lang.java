import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class Lang {
    public static String current = "ru";
    private static Map<String, String[]> t = new HashMap<>();

    static {
        // 0=ru, 1=en, 2=uz
        t.put("welcome",           new String[]{"Добро пожаловать, ",        "Welcome, ",                    "Xush kelibsiz, "});
        t.put("enter_pin",         new String[]{"Введите пин-код: ",          "Enter PIN: ",                  "PIN kiriting: "});
        t.put("wrong_pin",         new String[]{"Неверно! Попыток осталось: ","Wrong! Attempts left: ",       "Noto'g'ri! Urinish qoldi: "});
        t.put("blocked",           new String[]{"Счёт заблокирован!",         "Account blocked!",             "Hisob bloklandi!"});
        t.put("create_pin",        new String[]{"Создайте пин-код (4 цифры): ","Create PIN (4 digits): ",    "PIN yarating (4 raqam): "});
        t.put("repeat_pin",        new String[]{"Повторите пин-код: ",        "Repeat PIN: ",                 "PIN takrorlang: "});
        t.put("pins_no_match",     new String[]{"Пин-коды не совпадают!",     "PINs don't match!",            "PIN-kodlar mos kelmadi!"});
        t.put("pin_set",           new String[]{"Пин-код установлен!",        "PIN set!",                     "PIN o'rnatildi!"});
        t.put("pin_changed",       new String[]{"Пин-код изменён!",           "PIN changed!",                 "PIN o'zgartirildi!"});
        t.put("old_pin",           new String[]{"Старый пин-код: ",           "Old PIN: ",                    "Eski PIN: "});
        t.put("new_pin",           new String[]{"Новый пин-код: ",            "New PIN: ",                    "Yangi PIN: "});
        t.put("wrong_old_pin",     new String[]{"Неверный пин-код!",          "Wrong PIN!",                   "Noto'g'ri PIN!"});
        t.put("enter_nickname",    new String[]{"Введите никнейм: ",          "Enter nickname: ",             "Taxallus kiriting: "});
        t.put("nick_saved",        new String[]{"Никнейм сохранён: ",         "Nickname saved: ",             "Taxallus saqlandi: "});
        t.put("nick_changed",      new String[]{"Никнейм изменён: ",          "Nickname changed: ",           "Taxallus o'zgartirildi: "});
        t.put("new_nick",          new String[]{"Новый никнейм: ",            "New nickname: ",               "Yangi taxallus: "});
        t.put("choose",            new String[]{"Выберите: ",                 "Choose: ",                     "Tanlang: "});
        t.put("amount",            new String[]{"Сумма: ",                    "Amount: ",                     "Miqdor: "});
        t.put("deposited",         new String[]{"Пополнено: ",                "Deposited: ",                  "To'ldirildi: "});
        t.put("withdrawn",         new String[]{"Снято: ",                    "Withdrawn: ",                  "Yechildi: "});
        t.put("no_funds",          new String[]{"Недостаточно средств!",      "Insufficient funds!",          "Mablag' yetarli emas!"});
        t.put("below_min",         new String[]{"Баланс не может быть ниже ", "Balance cannot go below ",     "Balans bundan past bo'lolmaydi: "});
        t.put("balance_info",      new String[]{"Счёт: ",                     "Account: ",                    "Hisob: "});
        t.put("balance_label",     new String[]{"Баланс: ",                   "Balance: ",                    "Balans: "});
        t.put("min_label",         new String[]{"Минимум: ",                  "Minimum: ",                    "Minimal: "});
        t.put("history_title",     new String[]{"История транзакций: ",       "Transaction history: ",        "Tranzaksiyalar tarixi: "});
        t.put("history_empty",     new String[]{"История пуста.",             "History is empty.",            "Tarix bo'sh."});
        t.put("cancelled_label",   new String[]{"ОТМЕНЕНО",                   "CANCELLED",                    "BEKOR"});
        t.put("new_name",          new String[]{"Новое название: ",           "New name: ",                   "Yangi nom: "});
        t.put("renamed",           new String[]{"Счёт переименован: ",        "Account renamed: ",            "Hisob qayta nomlandi: "});
        t.put("enter_min",         new String[]{"Минимальный баланс: ",       "Minimum balance: ",            "Minimal balans: "});
        t.put("min_set",           new String[]{"Минимум установлен: ",       "Minimum set: ",                "Minimal o'rnatildi: "});
        t.put("confirm_delete",    new String[]{"Удалить счёт? (да/нет): ",   "Delete account? (yes/no): ",   "Hisobni o'chir? (ha/yoq): "});
        t.put("yes1",              new String[]{"да",  "yes", "ha"});
        t.put("yes2",              new String[]{"да",  "yes", "ha"});
        t.put("deleted",           new String[]{"Счёт удалён!",               "Account deleted!",             "Hisob o'chirildi!"});
        t.put("cancelled_op",      new String[]{"Отменено.",                  "Cancelled.",                   "Bekor qilindi."});
        t.put("no_accounts",       new String[]{"Нет счетов. Создайте первый.","No accounts. Create first.", "Hisob yo'q. Birinchi yarating."});
        t.put("your_accounts",     new String[]{"--- Ваши счета ---",         "--- Your accounts ---",        "--- Hisoblaringiz ---"});
        t.put("create_new",        new String[]{". Создать новый счёт",       ". Create new account",         ". Yangi hisob yaratish"});
        t.put("invalid",           new String[]{"Неверный выбор!",            "Invalid choice!",              "Noto'g'ri tanlov!"});
        t.put("account_name",      new String[]{"Название счёта: ",           "Account name: ",               "Hisob nomi: "});
        t.put("account_created",   new String[]{"Счёт создан: ",              "Account created: ",            "Hisob yaratildi: "});
        t.put("transfer_to",       new String[]{"Перевести на счёт: ",        "Transfer to account: ",        "Hisobga o'tkazish: "});
        t.put("transfer_amount",   new String[]{"Сумма перевода: ",           "Transfer amount: ",            "O'tkazma miqdori: "});
        t.put("acc_not_found",     new String[]{"Счёт не найден!",            "Account not found!",           "Hisob topilmadi!"});
        t.put("no_self",           new String[]{"Нельзя переводить себе!",    "Cannot transfer to yourself!", "O'zingizga o'tkazib bo'lmaydi!"});
        t.put("transfer_ok",       new String[]{"Переведено: ",               "Transferred: ",                "O'tkazildi: "});
        t.put("ai_greeting",       new String[]{"ИИ помощник готов. Введите вопрос (или 'выход'):", "AI ready. Enter question (or 'exit'):", "AI tayyor. Savol kiriting (yoki 'chiqish'):"});
        t.put("ai_you",            new String[]{"Вы: ",                       "You: ",                        "Siz: "});
        t.put("ai_prefix",         new String[]{"Ассистент: ",                "Assistant: ",                  "Yordamchi: "});
        t.put("ai_exit",           new String[]{"выход",                      "exit",                         "chiqish"});
        t.put("ai_key_missing",    new String[]{"API ключ не найден! Создайте файл api_key.txt и вставьте ключ.", "API key missing! Create api_key.txt and paste your key.", "API kalit topilmadi! api_key.txt faylini yarating."});
        t.put("goodbye",           new String[]{"До свидания!",               "Goodbye!",                     "Xayr!"});
        t.put("lang_select",       new String[]{"Выберите язык:",             "Select language:",             "Tilni tanlang:"});
        t.put("lang_changed",      new String[]{"Язык изменён!",              "Language changed!",            "Til o'zgartirildi!"});
        t.put("menu_1",  new String[]{"1.  Показать баланс",          "1.  Show balance",             "1.  Balansni ko'rish"});
        t.put("menu_2",  new String[]{"2.  Пополнить счёт",           "2.  Deposit",                  "2.  Hisob to'ldirish"});
        t.put("menu_3",  new String[]{"3.  Снять деньги",             "3.  Withdraw",                 "3.  Pul yechish"});
        t.put("menu_4",  new String[]{"4.  Перевод между счетами",    "4.  Transfer",                 "4.  O'tkazma"});
        t.put("menu_5",  new String[]{"5.  История транзакций",       "5.  Transaction history",      "5.  Tranzaksiyalar tarixi"});
        t.put("menu_6",  new String[]{"6.  Сменить счёт",             "6.  Change account",           "6.  Hisobni almashtirish"});
        t.put("menu_7",  new String[]{"7.  Переименовать счёт",       "7.  Rename account",           "7.  Hisobni qayta nomlash"});
        t.put("menu_8",  new String[]{"8.  Минимальный баланс",       "8.  Set minimum balance",      "8.  Minimal balansni o'rnatish"});
        t.put("menu_9",  new String[]{"9.  Сменить пин-код",          "9.  Change PIN",               "9.  PIN o'zgartirish"});
        t.put("menu_10", new String[]{"10. Сменить никнейм",          "10. Change nickname",          "10. Taxallus o'zgartirish"});
        t.put("menu_11", new String[]{"11. ИИ Помощник",              "11. AI Assistant",             "11. AI Yordamchi"});
        t.put("menu_12", new String[]{"12. Сменить язык",             "12. Change language",          "12. Tilni o'zgartirish"});
        t.put("menu_13", new String[]{"13. Удалить счёт",             "13. Delete account",           "13. Hisobni o'chirish"});
        t.put("menu_14", new String[]{"14. Выйти",                    "14. Exit",                     "14. Chiqish"});
    }

    public static String get(String key) {
        String[] arr = t.get(key);
        if (arr == null) return key;
        int i = current.equals("ru") ? 0 : current.equals("en") ? 1 : 2;
        return arr[i];
    }

    public static void save() {
        try {
            BufferedWriter w = new BufferedWriter(new FileWriter("language.txt"));
            w.write(current);
            w.close();
        } catch (Exception ignored) {}
    }

    public static void load() {
        try {
            BufferedReader r = new BufferedReader(new FileReader("language.txt"));
            current = r.readLine().trim();
            r.close();
        } catch (Exception e) {
            current = "ru";
        }
    }

    public static boolean isYes(String input) {
        return input.equalsIgnoreCase("да") || input.equalsIgnoreCase("yes") || input.equalsIgnoreCase("ha");
    }
}