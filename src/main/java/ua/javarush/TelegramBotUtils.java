package ua.javarush;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TelegramBotUtils {
    private static final Map<Long, Integer> orderCountStorage = new HashMap<>();

    public static Long getChatId(Update update) {
        if (update.hasMessage()) {
            return update.getMessage().getFrom().getId();
        }

        if (update.hasCallbackQuery()) {
            return update.getCallbackQuery().getFrom().getId();
        }

        return null;
    }

    public static SendMessage createMessage(Long chatId, String text) {
        SendMessage message = new SendMessage();
        message.setText(text); // Використовуйте text без конвертації
        message.setParseMode("markdown");
        message.setChatId(chatId.toString()); // Переконайтеся, що chatId передається як String
        return message;
    }


    public static SendMessage createMessage(Long chatId, String text, Map<String, String> buttons) {
        SendMessage message = createMessage(chatId, text);
        if (buttons != null && !buttons.isEmpty())
            attachButtons(message, buttons);
        return message;
    }

    public static SendMessage createMessageWithKeyboard(Long chatId, String text, List<String> keyboardOptions) {
        SendMessage message = createMessage(chatId, text);
        attachReplyKeyboard(message, keyboardOptions);
        return message;
    }

    private static void attachButtons(SendMessage message, Map<String, String> buttons) {
        InlineKeyboardMarkup markup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> keyboard = new ArrayList<>();

        for (String buttonName : buttons.keySet()) {
            String buttonValue = buttons.get(buttonName);

            InlineKeyboardButton button = new InlineKeyboardButton();
            button.setText(buttonName); // Виправлено
            button.setCallbackData(buttonValue);

            keyboard.add(List.of(button));
        }

        markup.setKeyboard(keyboard);
        message.setReplyMarkup(markup);
    }

    private static void attachReplyKeyboard(SendMessage message, List<String> keyboardOptions) {
        ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();
        List<KeyboardRow> keyboard = new ArrayList<>();

        KeyboardRow row = new KeyboardRow();
        for (String option : keyboardOptions) {
            row.add(option);
            if (row.size() == 2) {
                keyboard.add(row);
                row = new KeyboardRow();
            }
        }
        if (!row.isEmpty()) {
            keyboard.add(row);
        }

        keyboardMarkup.setKeyboard(keyboard);
        keyboardMarkup.setResizeKeyboard(true);
        keyboardMarkup.setOneTimeKeyboard(false);

        message.setReplyMarkup(keyboardMarkup);
    }

    public static SendPhoto createPhotoMessage(Long chatId, String name) {
        try {
            SendPhoto photo = new SendPhoto();
            InputFile inputFile = new InputFile();
            inputFile.setMedia(Files.newInputStream(Path.of("images/" + name + ".jpg")), name);
            photo.setPhoto(inputFile);
            photo.setChatId(chatId);
            return photo;
        } catch (IOException e) {
            throw new RuntimeException("Can't create photo message!");
        }
    }

    public static int getOrderCount(Long chatId) {
        return orderCountStorage.getOrDefault(chatId, 0);
    }

    public static void addOrder(Long chatId) {
        orderCountStorage.put(chatId, getOrderCount(chatId) + 1);
    }

    public static void clearOrders(Long chatId) {
        orderCountStorage.remove(chatId);
    }
}
