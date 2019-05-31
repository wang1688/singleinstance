package pers.wwz.singleinstance.config;

import com.alibaba.rocketmq.client.consumer.DefaultMQPushConsumer;
import com.alibaba.rocketmq.client.exception.MQClientException;
import com.alibaba.rocketmq.client.producer.DefaultMQProducer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import java.util.UUID;

/**
 * mq初始化类
 */
@Configuration
public class RocketMQConfig {

    private static final Logger logger = LoggerFactory.getLogger(RocketMQConfig.class);

    /**
     * 单例消息生产者
     */
    public static DefaultMQProducer SENDER;

    private static String NAME_SERVER = "43.249.223.101:9876";
    private static int SEND_MSG_TIMEOUT = 3000;

    @Value("${rocketmq.producer.namesrvAddr}")
    public void setNameServer(String nameServer) {
        NAME_SERVER = nameServer;
        logger.debug("NAME_SERVER = {}", NAME_SERVER);
    }


    @Value("${rocketmq.producer.sendMsgTimeout}")
    public void setSendMsgTimeout(int sendMsgTimeout) {
        SEND_MSG_TIMEOUT = sendMsgTimeout;
        logger.debug("SEND_MSG_TIMEOUT = {}", SEND_MSG_TIMEOUT);
    }

    /**
     * 生产者组名
     */
    private final static String PRODUCER_GROUP_NAME = "faceShowMQ";

    /**
     * 消费者组名
     */
    private final static String CONSUMER_GROUP_NAME = "faceShowMQ";

    /**
     * 生成生产者
     *
     * @return
     */
    public static DefaultMQProducer init() {
        if (SENDER != null) {
            return SENDER;
        }

        synchronized (RocketMQConfig.class) {
            if (SENDER != null) {
                return SENDER;
            }

            logger.info("Producer init name_server:" + NAME_SERVER);

            SENDER = new DefaultMQProducer(PRODUCER_GROUP_NAME);
            SENDER.setNamesrvAddr(NAME_SERVER);
            SENDER.setInstanceName(UUID.randomUUID().toString());
            try {
                SENDER.start();
            } catch (MQClientException e) {
                logger.error("单例生产者start异常", e);
            }

            SENDER.setSendMsgTimeout(SEND_MSG_TIMEOUT);
            return SENDER;
        }

    }

    /**
     * 生成消费者
     *
     * @return
     */
    public static DefaultMQPushConsumer initConsumer() {
        return initConsumer(CONSUMER_GROUP_NAME);
    }

    /**
     * 生成消费者
     *
     * @return
     */
    public static DefaultMQPushConsumer initConsumer(String consumerGroup) {
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer(consumerGroup);
        consumer.setNamesrvAddr(NAME_SERVER);
        consumer.setConsumeMessageBatchMaxSize(10);
        logger.info("mq消费者 name_server:" + NAME_SERVER + "  生成consumerGroup:" + consumerGroup);
        return consumer;
    }


}
