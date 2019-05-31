package pers.wwz.singleinstance.thread;

import pers.wwz.singleinstance.config.RocketMQConfig;
import pers.wwz.singleinstance.utils.SpringUtil;

public class SingleInstanceThread extends Thread {

    @Override
    public void run() {
        synchronized (SingleInstanceThread.class){
            System.out.println("RocketMQConfig初始化...");
            RocketMQConfig.init();
            long time =System.currentTimeMillis();
            for (int i = 0; i < 100; i++) {
                System.out.println(time+",SENDER:"+((RocketMQConfig) SpringUtil.getBean("rocketMQConfig")).SENDER);
            }
            try{
                Thread.sleep(2000);
                notify();
            }catch (Exception e){

            }
        }


    }
}
