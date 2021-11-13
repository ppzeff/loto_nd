package com.ppzeff.loto_nd;

import com.ppzeff.loto_nd.property.FileStorageProperties;
import com.ppzeff.loto_nd.service.IpService;
import com.ppzeff.loto_nd.service.TelegrammBotService;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

import java.io.IOException;
import java.util.Date;

@SpringBootApplication
@EnableScheduling
@EnableConfigurationProperties({
        FileStorageProperties.class
})
public class LotoNdApplication {
//    String ip = "";
    @Autowired
    TelegrammBotService telegrammBotService;
//    @Autowired
//    IpService ipService;

    public static void main(String[] args) {

        SpringApplication.run(LotoNdApplication.class, args);
    }

    @Bean
    public TaskScheduler taskScheduler() {
        final ThreadPoolTaskScheduler scheduler = new ThreadPoolTaskScheduler();
        scheduler.setPoolSize(10);
        return scheduler;
    }


//    @Scheduled(fixedDelay = 60 * 1000L, initialDelay = 3 * 1000L)
//    public void scheduled1() throws IOException {
////просто каждые 60 секунд обновляет IP
////        new HttpGet("https://dehssisfs.herokuapp.com/setip/" + ipService.getIpAddress() + "/");
//
////        if (!ip.equals(ipService.getIpAddress())) {
//        System.out.println(new Date() + ", old IP:" + ip + " : " + "new IP:" + ipService.getIpAddress());
//
//        HttpGet get = new HttpGet("https://dehssisfs.herokuapp.com/setip/" + ipService.getIpAddress() + "/");
//        HttpClient client = new DefaultHttpClient();
//        HttpResponse response = client.execute(get);
//        System.out.println(new Date() + ", " + response.getStatusLine());
//        ip = ipService.getIpAddress();
////        }
//
//    }

    @Scheduled(fixedDelay = 1_000_000 * 1000L)
    public void scheduled2() {
        System.out.println("start telebot: " + new Date());
        telegrammBotService.telBot();

        // do something on container startup
    }


//    @Scheduled(fixedDelay = 3 * 1000L, initialDelay = 3 * 1000L)
//    public void scheduled2() throws InterruptedException {
//        System.out.println(new Date() + " " + Thread.currentThread().getName() + ": scheduled2");
//        Thread.sleep(1000);
//    }

}
