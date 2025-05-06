package uz.duol.ecopharmwarehouse.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {
    public static final String USER_CREATED_QUEUE = "user.created.wms.queue";
    public static final String PRODUCT_CREATED_QUEUE = "product.created.wms.queue";
    public static final String UNIT_CREATED_QUEUE = "unit.created.wms.queue";
    public static final String UNIT_CONVERSION_CREATED_QUEUE = "unit.conversion.created.wms.queue";

    public static final String USER_CREATED_EVENT_EXCHANGE = "user.events.exchange";
    public static final String PRODUCT_CREATED_EVENT_EXCHANGE = "product.events.exchange";
    public static final String UNIT_CREATED_EVENT_EXCHANGE = "unit.events.exchange";
    public static final String UNIT_CONVERSION_CREATED_EVENT_EXCHANGE = "unit.conversion.events.exchange";

    public static final String USER_CREATED_ROUTING_KEY = "user.created";
    public static final String PRODUCT_CREATED_ROUTING_KEY = "product.created";
    public static final String UNIT_CREATED_ROUTING_KEY = "unit.created";
    public static final String UNIT_CONVERSION_CREATED_ROUTING_KEY = "unit.conversion.created";

    @Bean
    public Queue userCreateQueue() {
        return new Queue(USER_CREATED_QUEUE, true);
    }

    @Bean
    public Queue productCreateQueue() {
        return new Queue(PRODUCT_CREATED_QUEUE, true);
    }

    @Bean
    public Queue unitCreateQueue() {
        return new Queue(UNIT_CREATED_QUEUE, true);
    }

    @Bean
    public Queue unitConversionCreateQueue() {
        return new Queue(UNIT_CONVERSION_CREATED_QUEUE, true);
    }

    @Bean
    public TopicExchange userCreateExchange() {
        return new TopicExchange(USER_CREATED_EVENT_EXCHANGE);
    }

    @Bean
    public TopicExchange productCreateExchange() {
        return new TopicExchange(PRODUCT_CREATED_EVENT_EXCHANGE);
    }

    @Bean
    public TopicExchange unitCreateExchange() {
        return new TopicExchange(UNIT_CREATED_EVENT_EXCHANGE);
    }

    @Bean
    public TopicExchange unitConversionCreateExchange() {
        return new TopicExchange(UNIT_CONVERSION_CREATED_EVENT_EXCHANGE);
    }

    @Bean
    public Binding bindingUserCreate() {
        return BindingBuilder
                .bind(userCreateQueue())
                .to(userCreateExchange())
                .with(USER_CREATED_ROUTING_KEY);
    }

    @Bean
    public Binding bindingProductCreate() {
        return BindingBuilder
                .bind(productCreateQueue())
                .to(productCreateExchange())
                .with(PRODUCT_CREATED_ROUTING_KEY);
    }

    @Bean
    public Binding bindingUnitCreate() {
        return BindingBuilder
                .bind(unitCreateQueue())
                .to(unitCreateExchange())
                .with(UNIT_CREATED_ROUTING_KEY);
    }

    @Bean
    public Binding bindingUnitConversionCreate() {
        return BindingBuilder
                .bind(unitConversionCreateQueue())
                .to(unitConversionCreateExchange())
                .with(UNIT_CONVERSION_CREATED_ROUTING_KEY);
    }

    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(jsonMessageConverter());
        return rabbitTemplate;
    }
}
