package ru.lena.bobr.kafkademo.controller;

import dto.Address;
import dto.UserDto;
import org.apache.kafka.common.network.Send;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("msg")
public class BaseController {

    @Autowired
    private KafkaTemplate<Long, UserDto> kafkaTemplate;


    @PostMapping
    public void sendOrder(Long msgId, UserDto msg){
    ///
        msg.setAddress(new Address( "RUS", "Saratov", "Linina", 1L, 3L));
CompletableFuture<SendResult<Long, UserDto>> future = kafkaTemplate.send("msg", msgId, msg);
future.thenAccept(System.out::println);


        //Simple kafka sending
        //   kafkaTemplate.send("msg", msgId, msg);

        /*CompletableFuture<SendResult<String, String>> future =  kafkaTemplate.send("msg", msgId, msg);
        future.addCallback(System.out::println, System.err::println);
       kafkaTemplate.flush();*/
    }
}
