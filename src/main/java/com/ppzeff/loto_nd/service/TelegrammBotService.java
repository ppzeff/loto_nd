package com.ppzeff.loto_nd.service;


import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.SendResponse;
import com.ppzeff.loto_nd.property.MyConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

@Service
public class TelegrammBotService {

    @Autowired
    IpService ipService;

    @Value("${TELEGRAM_BOT_TOKEN}")
    private String telegrambotkey;
    private long chatId;
    // Create your bot passing the token received from @BotFather
    //    TelegramBot bot = new TelegramBot("BOT_TOKEN");

    public void telBot() {
        TelegramBot bot = new TelegramBot(telegrambotkey);

        bot.setUpdatesListener(updates -> {
            if (!updates.isEmpty()) {
                updates.stream().map(Update::message).forEach(message -> {
                    chatId = message.chat().id();
                    System.out.print("chat " + message.chat().id());
                    System.out.println(" user " + message.from().id());

                });
            }
            SendResponse response = bot.execute(new SendMessage(chatId, ipService.getIpAddress()));
            bot.execute(new SendMessage(chatId,
                    "https://dehssisfs.herokuapp.com/setip/" + ipService.getIpAddress() + "/"));

            return UpdatesListener.CONFIRMED_UPDATES_ALL;
        });

    }
}