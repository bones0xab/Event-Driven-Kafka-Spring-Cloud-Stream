package com.example.eventdrivenkafkaspringcloudstreams.web;

import com.example.eventdrivenkafkaspringcloudstreams.model.PageEvent;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.kstream.Grouped;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.Materialized;
import org.apache.kafka.streams.kstream.TimeWindows;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.Date;
import java.util.Random;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.function.Consumer;


@Component
public class PageEventWeb {

    @Bean
    public Supplier<PageEvent> pageEventSupplier() {
        return () -> {
            String[] pages = {"Home", "About", "Services", "Pricing"};
            Random random = new Random();
            String randomPage = pages[random.nextInt(pages.length)];
            return new PageEvent(
                    randomPage,
                Math.random() > 0.5 ? "Abdelkebir" : "Youssefi",
                new Date(),
                10 + new Random().nextInt(1337)
        );
        };
    }

    @Bean
    public Consumer<PageEvent> pageEventConsumer() {
        return (input) -> {
            System.out.println("**********");
            System.out.println("This is the part  : " + input.toString());
            System.out.println("**********");
        };
    }

    @Bean
    public Function<KStream<String, PageEvent>, KStream<String, Long>> kStream(){
        return (stream) ->
                stream
                        .map((k,v) -> new KeyValue<>(v.name(), 0L))
                        .groupByKey(Grouped.with(Serdes.String(), Serdes.Long()))
                        .windowedBy(TimeWindows.of(Duration.ofSeconds(5)))
                        .count(Materialized.as("Abdelkebir-store"))
                        .toStream()
                        .map((k,v)->new KeyValue<>(k.key(), v));
    }


}
