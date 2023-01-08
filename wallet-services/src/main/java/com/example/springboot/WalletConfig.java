package com.example.springboot;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.*;

import java.util.Properties;

@Configuration
public class WalletConfig {
    //no redis contain only kafka config
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

    @Bean
    ConsumerFactory<String,String> getConsumerFactory(){
        return new DefaultKafkaConsumerFactory(kafkaProperties());
    }

    @Bean
    ProducerFactory<String, String> getProducerFactory(){
        return new DefaultKafkaProducerFactory(kafkaProperties());
    }

    //this only for consumer as they are listening simultaneously..so this property needs to be there

    @Bean
    ConcurrentKafkaListenerContainerFactory<String, String> concurrentKafkaListenerContainerFactory(){
       ConcurrentKafkaListenerContainerFactory concurrentKafkaListenerContainerFactory=new ConcurrentKafkaListenerContainerFactory();
       concurrentKafkaListenerContainerFactory.setConsumerFactory(getConsumerFactory());
       return concurrentKafkaListenerContainerFactory;
    }

    @Bean
    KafkaTemplate<String,String> kafkaTemplate(){
        return new KafkaTemplate<>(getProducerFactory());
    }
}

//done