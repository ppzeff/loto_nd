package com.ppzeff.tinkoff.service;

import com.ppzeff.tinkoff.Config.BotConfig;
import com.ppzeff.tinkoff.model.Model;
import com.ppzeff.tinkoff.model.TelegramUser;
import com.ppzeff.tinkoff.repo.TelegramUserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.ParseMode;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.File;
import java.util.Date;

import static javax.management.timer.Timer.*;


/**
 * Касс является основным пулом взаимодействия с Telegram
 * получение, обработка, отправка сообщений
 */
@Component
public class BotService extends TelegramLongPollingBot {

    final
    BotConfig config;

    @Autowired
    TelegramUserRepo telegramUserRepo;

    @Autowired
    MainService mainService;

    @Autowired
    MakeGraphServiceImp makeGraphService;

    public BotService(BotConfig config) {

        this.config = config;
    }

    public void onUpdateReceived(Update update) {
        update.getUpdateId();
        SendMessage.SendMessageBuilder messageBuilder = SendMessage.builder();
        String messageText;
        String chatId;
        if (update.getMessage() != null && update.getMessage().hasText()) {

            TelegramUser telegramUser = new TelegramUser(
                    update.getMessage().getChatId(),
                    update.getMessage().getFrom().getFirstName(),
                    update.getMessage().getFrom().getLastName(),
                    update.getMessage().getFrom().getUserName(),
                    update.getMessage().getFrom().getIsBot(),
                    new Date());

            System.out.println(telegramUser.toString());
            if (telegramUserRepo.findTelegramUserByUserId(telegramUser.getUserId()) == null) {
                telegramUserRepo.save(telegramUser);
            }

            chatId = update.getMessage().getChatId().toString();
            messageBuilder.chatId(chatId);
            messageBuilder.parseMode(ParseMode.HTML);
            messageText = update.getMessage().getText();
        } else {
            chatId = update.getChannelPost().getChatId().toString();
            messageBuilder.chatId(chatId);
            messageText = update.getChannelPost().getText();
        }

        if (messageText.contains("/hello")) {
            messageBuilder.text("Привет");
            try {
                execute(messageBuilder.build());
            } catch (TelegramApiException e) {
                System.out.println(e.toString());
            }
        }

        if (messageText.contains("/week840")) {

            long start = System.currentTimeMillis();
            String fileName = makeGraphService.makeGraph(840,ONE_WEEK)+".jpg";
            SendPhoto photo = new SendPhoto();
            photo.setChatId(chatId);
            photo.setPhoto(new InputFile(new File(fileName)));
            photo.setCaption("График сформирован за: " +  (System.currentTimeMillis()-start) + "милисекунд");
            try {
                execute(photo);
//                execute(messageBuilder.build());
            } catch (TelegramApiException e) {
                System.out.println(e.toString());
            }
        }

        if (messageText.contains("/day840")) {

            long start = System.currentTimeMillis();
            String fileName = makeGraphService.makeGraph(840,ONE_DAY)+".jpg";
            SendPhoto photo = new SendPhoto();
            photo.setChatId(chatId);
            photo.setPhoto(new InputFile(new File(fileName)));
            photo.setCaption("График сформирован за: " +  (System.currentTimeMillis()-start) + "милисекунд");
            try {
                execute(photo);
//                execute(messageBuilder.build());
            } catch (TelegramApiException e) {
                System.out.println(e.toString());
            }
        }

        if (messageText.contains("/hour840")) {

            long start = System.currentTimeMillis();
            String fileName = makeGraphService.makeGraph(840,ONE_HOUR)+".jpg";
            SendPhoto photo = new SendPhoto();
            photo.setChatId(chatId);
            photo.setPhoto(new InputFile(new File(fileName)));
            photo.setCaption("График сформирован за: " +  (System.currentTimeMillis()-start) + "милисекунд");
            try {
                execute(photo);
//                execute(messageBuilder.build());
            } catch (TelegramApiException e) {
                System.out.println(e.toString());
            }
        }

        if (messageText.contains("/rates")) {
            Model model = mainService.cashModelMap.get(840);
            String rates = "Текущий курс: \n";
            rates = rates.concat(model.toTeleString());
            rates = rates.concat("\n");

            model = mainService.cashModelMap.get(978);
            rates = rates.concat(model.toTeleString());
            rates = rates.concat("\n");

            model = mainService.cashModelMap.get(826);
            rates = rates.concat(model.toTeleString());

            messageBuilder.text(rates);

            try {
                execute(messageBuilder.build());
            } catch (TelegramApiException e) {
                System.out.println(e.toString());
            }
        }

        if (messageText.contains("/chartId")) {
            messageBuilder.text("ID Канала : <b>" + chatId + "</b>");
            try {
                execute(messageBuilder.build());
            } catch (TelegramApiException e) {
                System.out.println(e.toString());
            }
        }
    }


    @Override
    public String getBotUsername() {
        return config.getBotUserName();
    }

    @Override
    public String getBotToken() {
        return config.getToken();
    }
}

//rates - получить текущие курсы
//graph - получить график
//hour840 - график $ за час
//day840 - график $ за день
//week840 - график $ за неделю