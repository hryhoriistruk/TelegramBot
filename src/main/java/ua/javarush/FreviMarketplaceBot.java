package ua.javarush;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.ParseMode;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import static ua.javarush.TelegramBotUtils.createMessage;
import static ua.javarush.TelegramBotUtils.getChatId;

public class FreviMarketplaceBot extends TelegramLongPollingBot {
    @Override
    public String getBotUsername() {
        return "frevi_marketplace_bot";
    }

    @Override
    public String getBotToken() {
        return "8272752900:AAEgjToKcCbVToThh_WXQ0PVjKUzdDG4rD4"; // ОНОВЛЕНИЙ ТОКЕН
    }

    @Override
    public void onUpdateReceived(Update update) {
        Long chatId = getChatId(update);
        System.out.println("=== NEW UPDATE RECEIVED ===");
        System.out.println("Chat ID: " + chatId);

        if (update.hasMessage() && update.getMessage().hasText()) {
            String text = update.getMessage().getText();
            System.out.println("Message text: '" + text + "'");

            if (text.equals("/start")) {
                System.out.println("Processing /start command");
                sendWelcomeMessage(chatId);
            } else if (text.equalsIgnoreCase("категорії послуг")) {
                System.out.println("Processing categories command");
                sendCategories(chatId);
            } else if (text.equalsIgnoreCase("мої замовлення")) {
                System.out.println("Processing my orders command");
                sendMyOrders(chatId);
            } else if (text.equalsIgnoreCase("допомога")) {
                System.out.println("Processing help command");
                sendHelp(chatId);
            } else if (text.equalsIgnoreCase("зв'язатися з підтримкою")) {
                System.out.println("Processing support command");
                sendSupportContact(chatId);
            } else {
                System.out.println("Unknown command: " + text);
                sendUnknownCommand(chatId);
            }
        } else {
            System.out.println("Update doesn't contain text message");
        }
        System.out.println("=== UPDATE PROCESSING COMPLETE ===\n");
    }

    private void sendWelcomeMessage(Long chatId) {
        System.out.println("Sending welcome message to chat: " + chatId);
        SendMessage message = createMessage(chatId, "Ласкаво просимо до Frevi Marketplace! 🛍️\n\nВикористовуйте команди:\n• /start - початок роботи\n• категорії послуг\n• мої замовлення\n• допомога\n• зв'язатися з підтримкою");
        message.setParseMode(ParseMode.MARKDOWN);
        try {
            execute(message);
            System.out.println("Welcome message sent successfully to chat: " + chatId);
        } catch (TelegramApiException e) {
            System.out.println("ERROR sending welcome message: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void sendCategories(Long chatId) {
        System.out.println("Sending categories to chat: " + chatId);
        SendMessage message = createMessage(chatId, "📋 *Категорії послуг:*\n\n• 💻 IT та програмування\n• 🎨 Дизайн та креатив\n• 📝 Тексти та переклади\n• 🎵 Аудіо та відео\n• 🔧 Бізнес та консалтинг");
        message.setParseMode(ParseMode.MARKDOWN);
        try {
            execute(message);
            System.out.println("Categories sent successfully to chat: " + chatId);
        } catch (TelegramApiException e) {
            System.out.println("ERROR sending categories: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void sendMyOrders(Long chatId) {
        System.out.println("Sending my orders to chat: " + chatId);
        SendMessage message = createMessage(chatId, "📦 *Мої замовлення:*\n\nНаразі замовлень немає. Створіть перше замовлення!");
        message.setParseMode(ParseMode.MARKDOWN);
        try {
            execute(message);
            System.out.println("My orders sent successfully to chat: " + chatId);
        } catch (TelegramApiException e) {
            System.out.println("ERROR sending my orders: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void sendHelp(Long chatId) {
        System.out.println("Sending help to chat: " + chatId);
        // Виправлено: Екрановано символ підкреслення в @frevi_support
        SendMessage message = createMessage(chatId, "❓ *Допомога:*\n\nТелефон: +380 (67) 123-45-67\nEmail: support@frevi.ua\nTelegram: @frevi\\_support");
        message.setParseMode(ParseMode.MARKDOWN);
        try {
            execute(message);
            System.out.println("Help sent successfully to chat: " + chatId);
        } catch (TelegramApiException e) {
            System.out.println("ERROR sending help: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void sendSupportContact(Long chatId) {
        System.out.println("Sending support contact to chat: " + chatId);
        // Виправлено: Екрановано символ підкреслення в @frevi_support
        SendMessage message = createMessage(chatId, "📞 *Зв'язок з підтримкою:*\n\n@frevi\\_support\n\nГрафік роботи:\nПн-Пт: 9:00-18:00\nСб: 10:00-16:00");
        message.setParseMode(ParseMode.MARKDOWN);
        try {
            execute(message);
            System.out.println("Support contact sent successfully to chat: " + chatId);
        } catch (TelegramApiException e) {
            System.out.println("ERROR sending support contact: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void sendUnknownCommand(Long chatId) {
        System.out.println("Sending unknown command response to chat: " + chatId);
        SendMessage message = createMessage(chatId, "❌ Невідома команда. Використовуйте:\n/start - початок\nкатегорії послуг\nмої замовлення\nдопомога\nзв'язатися з підтримкою");
        try {
            execute(message);
            System.out.println("Unknown command response sent successfully to chat: " + chatId);
        } catch (TelegramApiException e) {
            System.out.println("ERROR sending unknown command response: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        System.out.println("Starting Frevi Marketplace Bot...");
        try {
            TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
            telegramBotsApi.registerBot(new FreviMarketplaceBot());
            System.out.println("Bot started successfully! Waiting for messages...");
        } catch (TelegramApiException e) {
            System.out.println("ERROR starting bot: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
