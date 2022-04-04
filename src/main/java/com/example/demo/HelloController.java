package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @Autowired
    HelloService helloService;

    @GetMapping("/")
    void test() {}

    @GetMapping("/1")
    public void um() {
        helloService.execute1();
    }

    @GetMapping("/1-T")
    public void umT() {
        helloService.execute1Transacional();
    }


    @GetMapping("/2")
    public void dois() {
        helloService.execute2();
    }

    @GetMapping("/2-T")
    public void doisT() {
        long start = System.nanoTime();

        helloService.execute2Transcional();

        System.out.println("TIME TOTAL " + (System.nanoTime() - start)/1_000_000);

    }
    @GetMapping("/2-C")
    public void doisC() {
        helloService.execute2Thread();
    }

    @GetMapping("/2-T-C")
    public void doisTC() {
        helloService.execute2ThreadTransacional();
    }


    @GetMapping("/jdbc")
    public void jdbc() {
        helloService.jdbc();
    }

    @GetMapping("/j1")
    public void j1() {
        helloService.j1();
    }
}
