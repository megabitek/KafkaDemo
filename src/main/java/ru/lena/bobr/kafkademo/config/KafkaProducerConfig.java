package ru.lena.bobr.kafkademo.config;

import dto.UserDto;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.LongSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaProducerConfig {

    private String kafkaServer="localhost:9092";

    @Bean
    public Map<String, Object> producerConfigs() {
        Map<String, Object> props = new HashMap<>();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,
                kafkaServer);
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG,
                LongSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,
                JsonSerializer.class);
        return props;
    }
/**Для того, чтобы создать DefaultKafkaProducerFactory,
 * нам нужно в его конструктор передать Map, содержащий настройки продюсера.***/
    @Bean
    public ProducerFactory<Long, UserDto> producerFactory() {
        return new DefaultKafkaProducerFactory<>(producerConfigs());
    }


    /**Если мы попробуем вручную создать объект KafkaTemplate, то увидим,
     * что в конструктор в качестве параметра передаётся объект интерфейса
     * ProducerFactory<K, V>, например DefaultKafkaProducerFactory<>.***/
    @Bean
    public KafkaTemplate<Long, UserDto> kafkaTemplate() {
        return new KafkaTemplate<>(producerFactory());
    }
}
