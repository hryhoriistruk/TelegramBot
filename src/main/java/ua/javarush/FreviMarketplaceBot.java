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
        return "7132694347:AAGhYn2sGZqWnK4GHK4J2VDhCyNnECFWyTk";
    }

    @Override
    public void onUpdateReceived(Update update) {
        Long chatId = getChatId(update);

        if (update.hasMessage() && update.getMessage().hasText()) {
            String text = update.getMessage().getText();

            if (text.equals("/start")) {
                sendWelcomeMessage(chatId);
            } else if (text.equalsIgnoreCase("категорії послуг")) {
                sendCategories(chatId);
            } else if (text.equalsIgnoreCase("мої замовлення")) {
                sendMyOrders(chatId);
            } else if (text.equalsIgnoreCase("допомога")) {
                sendHelp(chatId);
            } else if (text.equalsIgnoreCase("зв'язатися з підтримкою")) {
                sendSupportContact(chatId);
            }
        }
    }

    private void sendWelcomeMessage(Long chatId) {
        SendMessage message = createMessage(chatId, FreviBotContent.WELCOME_TEXT);
        message.setParseMode(ParseMode.MARKDOWN);
        try {
            execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    private void sendCategories(Long chatId) {
        SendMessage message = createMessage(chatId, FreviBotContent.CATEGORIES_TEXT);
        message.setParseMode(ParseMode.MARKDOWN);
        try {
            execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    private void sendMyOrders(Long chatId) {
        SendMessage message = createMessage(chatId, FreviBotContent.MY_ORDERS_TEXT);
        message.setParseMode(ParseMode.MARKDOWN);
        try {
            execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    private void sendHelp(Long chatId) {
        SendMessage message = createMessage(chatId, FreviBotContent.HELP_TEXT);
        message.setParseMode(ParseMode.MARKDOWN);
        try {
            execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    private void sendSupportContact(Long chatId) {
        SendMessage message = createMessage(chatId, FreviBotContent.SUPPORT_TEXT);
        message.setParseMode(ParseMode.MARKDOWN);
        try {
            execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws TelegramApiException {
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
        telegramBotsApi.registerBot(new FreviMarketplaceBot());
    }
}