package com.example.springboot;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.*;
import org.springframework.web.client.RestTemplate;

import java.util.Properties;

@Configuration
public class TransactionConfig {
    //1.kafka properties
    @Bean
    Properties kafkaProperties(){
        Properties properties=new Properties();

        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,StringSerializer.class);
        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,"localhost:9092");

        properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,StringDeserializer.class);
        properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG,"localhost:9092");

        properties.put(ConsumerConfig.GROUP_ID_CONFIG,"friends_group");

        return properties;
    }

    //2. factories -> for producer and consumer

    @Bean
    ProducerFactory<String, String> getProducerfactory(){
        return new DefaultKafkaProducerFactory(kafkaProperties());
    }

    @Bean
    ConsumerFactory<String, String> getConsumerfactory(){
        return new DefaultKafkaConsumerFactory(kafkaProperties());
    }

    //3. ConcurrentKafkaListner which is used for only conumser to listen the massages

    @Bean
    ConcurrentKafkaListenerContainerFactory<String, String> concurrentKafkaListenerContainerFactory(){
        ConcurrentKafkaListenerContainerFactory concurrentKafkaListenerContainerFactory=new ConcurrentKafkaListenerContainerFactory<>();
        concurrentKafkaListenerContainerFactory.setConsumerFactory(getConsumerfactory());
        return  concurrentKafkaListenerContainerFactory;

    }

    //4. kafka template for producer factory
    @Bean
    KafkaTemplate<String, String> kafkaTemplate(){
        return new KafkaTemplate<>(getProducerfactory());
    }

    @Bean
    RestTemplate getRestTemplate(){
        return new RestTemplate();
    }
}
//done