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

        public class MyFirstTelegramBot extends TelegramLongPollingBot {
    @Override
    public String getBotUsername() {
                // TODO:додай ім'я бота в лапки нижче
                return "qwerty32332_jru_bot";
            }

            @Override
    public String getBotToken() {
                // TODO: додай токен бота в лапки нижче
                return "7132694347:AAGhYn2sGZqWnK4GHK4J2VDhCyNnECFWyTk";
            }

    @Override
    public void onUpdateReceived(Update update) {
                Long chatId = getChatId(update);
                String text = update.getMessage().getText();

                if (text.equalsIgnoreCase("Привіт, як тебе звуть?")) {
                        SendMessage message = createMessage(chatId, "Радий знайомству, я *Кіт*");
                        message.setParseMode(ParseMode.MARKDOWN);
                        try {
                                execute(message);
                            } catch (TelegramApiException e) {
                                e.printStackTrace();
                            }
                    }
            }

            public static void main (String[]args) throws TelegramApiException {
                    TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
                    telegramBotsApi.registerBot(new MyFirstTelegramBot());
             }
}