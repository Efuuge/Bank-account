# 🏦 Banking Console Application

> A multi-account banking system built with Java — featuring PIN protection, multi-language support, and transaction history.

---

## 🇬🇧 English

### About
A console-based banking application written in Java. Supports multiple accounts, secure PIN authentication, deposits, withdrawals, transfers between accounts, and full transaction history — all saved to local files.

### Features
- 🔐 **PIN protection** — 3 attempts before lockout
- 👤 **Nickname system** — personalized greeting
- 💳 **Multiple accounts** — create, rename, delete
- 💰 **Deposit / Withdraw** — with minimum balance enforcement
- 🔁 **Transfers** — between accounts with validation
- 📋 **Transaction history** — saved per account
- 🌍 **3 languages** — Russian, English, Uzbek
- 💾 **File persistence** — all data saved in `.txt` files

### Project Structure
```
src/
├── Main.java          # Entry point, main menu
├── BankAccount.java   # Core banking logic
├── Lang.java          # Multi-language support
└── AIAssistant.java   # AI assistant (Groq API)
```

### How to Run
1. Clone the repository
```bash
git clone https://github.com/Efuuge/banking-app.git
```
2. Open in **IntelliJ IDEA**
3. Run `Main.java`
4. *(Optional)* Create `api_key.txt` in project root with your [Groq API key](https://console.groq.com) to enable AI Assistant

### Requirements
- Java 17+
- IntelliJ IDEA (recommended)

### Tech Stack
`Java` `OOP` `File I/O` `REST API` `IntelliJ IDEA` `Git`

---

## 🇷🇺 Русский

### О проекте
Консольное банковское приложение на языке Java. Поддерживает несколько счетов, PIN-защиту, пополнение, снятие, переводы между счетами и историю транзакций — всё сохраняется в локальные файлы.

### Функционал
- 🔐 **PIN-защита** — 3 попытки до блокировки
- 👤 **Никнейм** — персонализированное приветствие
- 💳 **Несколько счетов** — создание, переименование, удаление
- 💰 **Пополнение / Снятие** — с проверкой минимального баланса
- 🔁 **Переводы** — между счетами с валидацией
- 📋 **История транзакций** — сохраняется по каждому счёту
- 🌍 **3 языка** — русский, английский, узбекский
- 💾 **Сохранение данных** — все данные хранятся в `.txt` файлах

### Структура проекта
```
src/
├── Main.java          # Точка входа, главное меню
├── BankAccount.java   # Основная логика банка
├── Lang.java          # Многоязычность
└── AIAssistant.java   # ИИ-ассистент (Groq API)
```

### Запуск
1. Клонируй репозиторий
```bash
git clone https://github.com/Efuuge/banking-app.git
```
2. Открой в **IntelliJ IDEA**
3. Запусти `Main.java`
4. *(Опционально)* Создай файл `api_key.txt` в корне проекта и вставь ключ от [Groq](https://console.groq.com) для работы ИИ-ассистента

### Требования
- Java 17+
- IntelliJ IDEA (рекомендуется)

### Технологии
`Java` `ООП` `File I/O` `REST API` `IntelliJ IDEA` `Git`

---

## 📁 Data Files (auto-generated)

| File | Description |
|------|-------------|
| `pin.txt` | Hashed PIN code |
| `nickname.txt` | User nickname |
| `accounts.txt` | List of accounts |
| `balance_<name>.txt` | Balance per account |
| `history_<name>.txt` | Transaction history per account |
| `api_key.txt` | Groq API key *(manual)* |
| `language.txt` | Selected language |

---

*Made by Tursunboev Rahmonali · IT Park University · 2025*
