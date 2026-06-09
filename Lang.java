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
        t.put("wrong_pin",         new String[]{"Неверно! Попыток осталось: ", "Wrong! Attempts left: ",       "Noto'g'ri! Urinish qoldi: "});
        t.put("blocked",           new String[]{"Счёт заблокирован!",         "Account blocked!",             "Hisob bloklandi!"});
        t.put("create_pin",        new String[]{"Создайте пин-код (4 цифры): ","Create PIN (4 digits): ",    "PIN yarating (4 raqam): "});
        t.put("repeat_pin",        new String[]{"Повторите пин-код: ",        "Repeat PIN: ",                 "PIN takrorlang: "});
        t.put("pins_no_match",     new String[]{"Пин-коды не совпадают!",     "PINs don't match!",            "PIN-kodlar mos kelmadi!"});
        t.put("pin_set",           new String[]{"Пин-код успешно установлен!","PIN successfully set!",        "PIN-kod muvaffaqiyatli o'rnatildi!"});
        t.put("invalid_pin_format",new String[]{"Ошибка! Пин-код должен состоять из 4 цифр.","Error! PIN must be 4 digits.","Xato! PIN-kod 4 ta raqamdan iborat bo'lishi kerak."});
        t.put("enter_nickname",    new String[]{"Введите ваш никнейм: ",      "Enter your nickname: ",        "Taxallusingizni kiriting: "});
        t.put("nickname_set",      new String[]{"Никнейм успешно установлен!","Nickname successfully set!",   "Taxallus muvaffaqiyatli o'rnatildi!"});
        t.put("your_accounts",     new String[]{"Ваши счета:",                "Your accounts:",               "Sizning hisoblaringiz:"});
        t.put("create_new",        new String[]{". Создать новый счёт",       ". Create new account",         ". Yangi hisob yaratish"});
        t.put("choose",            new String[]{"Выберите вариант: ",         "Choose an option: ",           "Variantni tanlang: "});
        t.put("invalid",           new String[]{"Неверный выбор!",            "Invalid choice!",              "Noto'g'ri tanlov!"});
        t.put("account_name",      new String[]{"Введите название нового счёта: ","Enter new account name: ", "Yangi hisob nomini kiriting: "});
        t.put("account_created",   new String[]{"Счёт успешно создан: ",      "Account created: ",            "Hisob muvaffaqiyatli yaratildi: "});
        t.put("lang_select",       new String[]{"Выберите язык / Select language / Tilni tanlang:", "Choose language:", "Tilni tanlang:"});
        t.put("no_accounts",       new String[]{"У вас нет счетов. Давайте создадим первый!","No accounts found. Let's create the first one!","Sizda hisoblar yo'q. Birinchisini yaratamiz!"});

        t.put("menu_1",  new String[]{"1. Показать баланс",         "1. Show balance",              "1. Balansni ko'rsatish"});
        t.put("menu_2",  new String[]{"2. Пополнитель счёт",        "2. Deposit",                   "2. Hisobni to'ldirish"});
        t.put("menu_3",  new String[]{"3. Снять наличные",          "3. Withdraw",                  "3. Naqd pul yechish"});
        t.put("menu_4",  new String[]{"4. Перевод на другой счёт",  "4. Transfer to account",       "4. Boshqa hisobga o'tkazish"});
        t.put("menu_5",  new String[]{"5. Сменить счёт",            "5. Switch account",            "5. Hisobni almashtirish"});
        t.put("menu_6",  new String[]{"6. Показать историю",        "6. Show history",              "6. Tarixni ko'rsatish"});
        t.put("menu_7",  new String[]{"7. Переименовать счёт",      "7. Rename account",            "7. Hisob nomini o'zgartirish"});
        t.put("menu_8",  new String[]{"8. Установить мин. баланс",  "8. Set min balance",           "8. Minimal balansni o'rnatish"});
        t.put("menu_9",  new String[]{"9. Перевод на карту",        "9. Transfer to card",          "9. Kartaga o'tkazma"});
        t.put("menu_10", new String[]{"10. Изменить никнейм",       "10. Change nickname",          "10. Taxallus o'zgartirish"});
        t.put("menu_11", new String[]{"11. ИИ Помощник",            "11. AI Assistant",             "11. AI Yordamchi"});
        t.put("menu_12", new String[]{"12. Сменить язык",           "12. Change language",          "12. Tilni o'zgartirish"});
        t.put("menu_13", new String[]{"13. Удалить счёт",           "13. Delete account",           "13. Hisobni o'chirish"});
        t.put("menu_14", new String[]{"14. Оборот средств",         "14. Financial turnover",       "14. Pul aylanmasi"});
        t.put("menu_15", new String[]{"15. Выйти",                  "15. Exit",                     "15. Chiqish"});

        t.put("balance_is",        new String[]{"Текущий баланс: ",           "Current balance: ",            "Joriy balans: "});
        t.put("min_balance_is",    new String[]{" (Минимальный лимит: ",      " (Minimum limit: ",            " (Minimal limit: "});
        t.put("enter_deposit",     new String[]{"Введите сумму для пополнения: ","Enter deposit amount: ",    "To'ldirish summasini kiriting: "});
        t.put("deposited",         new String[]{"Счёт успешно пополнен на ",  "Successfully deposited ",      "Hisob muvaffaqiyatli to'ldirildi: "});
        t.put("enter_withdraw",    new String[]{"Введите сумму для снятия: ", "Enter withdraw amount: ",      "Yechish summasini kiriting: "});
        t.put("withdrawn",         new String[]{"Вы успешно сняли ",          "Successfully withdrew ",       "Muvaffaqiyatli yechildi "});
        t.put("insufficient",      new String[]{"Ошибка! Недостаточно средств (учитывая лимит минимального баланса).", "Error! Insufficient funds (considering minimum balance limit).", "Xato! Mablag' yetarli emas (minimal balans limitini hisobga olgan holda)."});
        t.put("enter_target",      new String[]{"Введите имя счёта получателя: ","Enter target account name: ", "Qabul qiluvchi hisob nomini kiriting: "});
        t.put("enter_transfer",    new String[]{"Введите сумму перевода: ",   "Enter transfer amount: ",      "O'tkazma summasini kiriting: "});
        t.put("target_not_found",  new String[]{"Ошибка! Счёт получателя не найден.","Error! Target account not found.","Xato! Qabul qiluvchi hisob topilmadi."});
        t.put("transfer_success",  new String[]{"Перевод успешно выполнен. Отправлено ", "Transfer successful. Sent ", "O'tkazma muvaffaqiyatli bajarildi. Yuborildi "});
        t.put("to_account",        new String[]{" на счёт ",                  " to account ",                 " hisobiga "});
        t.put("history_empty",     new String[]{"История транзакций пуста.",  "Transaction history is empty.","Tranzaksiyalar tarixi bo'sh."});
        t.put("enter_new_name",    new String[]{"Введите новое имя для этого счёта: ","Enter new name for this account: ","Ushbu hisob uchun yangi nom kiriting: "});
        t.put("renamed",           new String[]{"Счёт успешно переименован в: ","Account successfully renamed to: ","Hisob muvaffaqiyatli o'zgartirildi: "});
        t.put("enter_min_balance", new String[]{"Введите сумму минимального баланса: ","Enter minimum balance amount: ","Minimal balans summasini kiriting: "});
        t.put("min_set",           new String[]{"Минимальный баланс установлен на ","Minimum balance set to ", "Minimal balans belgilandi: "});
        t.put("account_deleted",   new String[]{"Счёт успешно удалён.",        "Account successfully deleted.", "Hisob muvaffaqiyatli o'chirildi."});
        t.put("goodbye",           new String[]{"Спасибо за использование нашего банка! До свидания.","Thank you for using our bank! Goodbye.","Bankimizdan foydalanganingiz uchun rahmat! Xayr."});
        t.put("invalid_amount",    new String[]{"Ошибка! Сумма должна быть больше нуля.", "Error! Amount must be greater than zero.", "Xato! Summa noldan katta bo'lishi kerak."});

        t.put("ai_key_missing",    new String[]{"Ошибка: файл api_key.txt не найден или пуст! Сначала добавьте ваш Groq API ключ.", "Error: api_key.txt not found or empty! Add your Groq API key first.", "Xato: api_key.txt topilmadi yoki bo'sh! Avval Groq API kalitini kiriting."});
        t.put("ai_greeting",       new String[]{"ИИ Финансовый Помощник запущен. Напишите ваш вопрос (или 'выход' для возврата в меню):", "AI Financial Assistant started. Type your question (or 'exit' to return):", "AI Moliyaviy Yordamchi ishga tushdi. Savolingizni yozing (chiqish uchun 'exit' deb yozing):"});
        t.put("ai_you",            new String[]{"Вы: ",                       "You: ",                        "Siz: "});
        t.put("ai_prefix",         new String[]{"ИИ Помощник: ... думает ...\r", "AI Assistant: ... thinking ...\r", "AI Yordamchi: ... o'ylamoqda ...\r"});

        t.put("enter_card",        new String[]{"Введите 16-значный номер карты: ", "Enter 16-digit card number: ", "16 raqamli karta raqamini kiriting: "});
        t.put("invalid_card",      new String[]{"Операция невозможна! Номер карты должен состоять строго из 16 цифр без букв и спецсимволов.", "Operation cancelled! Card number must be exactly 16 digits without letters or special characters.", "Operatsiya imkonsiz! Karta raqami faqat 16 ta raqamdan iborat bo'lishi kerak."});
        t.put("quick_cards_title",    new String[]{"--- Быстрый выбор (последние карты) ---", "--- Quick selection (recent cards) ---", "--- Tezkor tanlov (oxirgi kartalar) ---"});
        t.put("enter_new_card_opt",   new String[]{"Ввести новую карту",      "Enter a new card",             "Yangi karta kiritish"});
        t.put("transfer_menu_history", new String[]{"История переводов",       "Transfer history",             "O'tkazmalar tarixi"});
        t.put("quick_cards_history_title", new String[]{"--- Быстрый выбор (последние 10 карт из истории) ---", "--- Quick selection (last 10 cards from history) ---", "--- Tezkor tanlov (tarixdagi oxirgi 10 ta karta) ---"});

        t.put("turnover_menu_title",  new String[]{"--- Меню оборота средств ---", "--- Financial Turnover Menu ---", "--- Pul aylanmasi menyusi ---"});
        t.put("turnover_opt_1",       new String[]{"1. Оборот текущего счёта", "1. Turnover of the current account", "1. Joriy hisob aylanmasi"});
        t.put("turnover_opt_2",       new String[]{"2. Оборот всех счетов (каждого отдельно)", "2. Turnover of all accounts (individually)", "2. Barcha hisoblar aylanmasi (alohida)"});
        t.put("turnover_opt_3",       new String[]{"3. Суммарный оборот выбранных счетов", "3. Combined turnover of selected accounts", "3. Tanlangan hisoblarning umumiy aylanmasi"});
        t.put("turnover_select_hint", new String[]{"Введите номера счетов через пробел (для завершения нажмите Enter, например: 1 3): ", "Enter account numbers separated by spaces (e.g. 1 3): ", "Hisob raqamlarini bo'shliq bilan kiriting (masalan: 1 3): "});
        t.put("turnover_total",       new String[]{"Общий оборот выбранных счетов: ", "Total turnover of selected accounts: ", "Tanlangan hisoblarning umumiy aylanmasi: "});
        t.put("turnover_result",      new String[]{"Оборот счёта ", "Turnover of account ", "Hisob aylanmasi "});

        t.put("invalid_number_format", new String[]{"Операция отклонена! Пожалуйста, вводите только числовые значения.", "Operation declined! Please enter numeric values only.", "Operatsiya rad etildi! Iltimos, faqat raqamli qiymatlarni kiriting."});
        t.put("confirm_prompt",    new String[]{"Вы уверены, что хотите выполнить это действие? (Y/N): ", "Are you sure you want to perform this action? (Y/N): ", "Haqiqatan ham ushbu amalni bajarmoqchimisiz? (Y/N): "});
        t.put("action_cancelled",  new String[]{"Операция отменена пользователем.", "Operation cancelled by user.", "Operatsiya foydalanuvchi tomonidan bekor qilindi."});

        t.put("error_rename_exists", new String[]{"Ошибка! Счёт с таким именем уже существует.", "Error! Account with this name already exists.", "Xato! Bunday nomli hisob allaqachon mavjud."});
        t.put("error_io_failed",     new String[]{"Системная ошибка работы с файлами.", "System I/O file error.", "Fayllar bilan ishlashda tizim xatosi."});
    }

    public static String get(String key) {
        String[] arr = t.get(key);
        if (arr == null) return key;
        int i = current.equals("ru") ? 0 : current.equals("en") ? 1 : 2;
        return arr[i];
    }

    public static void save() {
        try (BufferedWriter w = new BufferedWriter(new FileWriter("language.txt"))) {
            w.write(current);
        } catch (Exception ignored) {}
    }

    public static void load() {
        try (BufferedReader r = new BufferedReader(new FileReader("language.txt"))) {
            current = r.readLine().trim();
        } catch (Exception e) {
            current = "ru";
        }
    }
}
