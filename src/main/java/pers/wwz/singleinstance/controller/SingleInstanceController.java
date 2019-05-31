package pers.wwz.singleinstance.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pers.wwz.singleinstance.thread.SingleInstanceThread;

@RestController
@RequestMapping("/singleInstance")
public class SingleInstanceController {

    @RequestMapping("test")
    public void test(){
        SingleInstanceThread t1 = new SingleInstanceThread();
        SingleInstanceThread t2 = new SingleInstanceThread();
        t1.start();
        t2.start();
    }
}
