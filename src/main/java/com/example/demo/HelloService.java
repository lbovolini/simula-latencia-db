package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
class HelloService {

    static ExecutorService pool = Executors.newWorkStealingPool(40);

    @Autowired
    HelloRepository helloRepository;

    @Autowired
    TransactionTemplate transaction;

    @Autowired
    JdbcTemplate jdbcTemplate;

//    @PersistenceContext
//    private EntityManager entityManager;

    @Autowired
    private TesteRepository testeRepository;

    public void hello() {
        System.out.printf("Iniciando thread = %s, %d\n", Thread.currentThread().getName(), Thread.currentThread().getId());

        long start = System.nanoTime();
       // template.setPropagationBehaviorName("PROPAGATION_NOT_SUPPORTED");
        //template.setIsolationLevel(TransactionDefinition.ISOLATION_READ_UNCOMMITTED);
        //template.setReadOnly(true);
        transaction.execute(status -> helloRepository.findByName("lucas"));

        System.out.println("TIME " + (System.nanoTime() - start)/1_000_000);

        System.out.printf("Fim thread = %s, %d\n", Thread.currentThread().getName(), Thread.currentThread().getId());
    }

    @Transactional
    public void execute4Thread() {

        CompletableFuture<?> submit1 = CompletableFuture.runAsync(() -> hello(), pool);
        CompletableFuture<?> submit2 = CompletableFuture.runAsync(() -> hello(), pool);
        CompletableFuture<?> submit3 = CompletableFuture.runAsync(() -> hello(), pool);
        CompletableFuture<?> submit4 = CompletableFuture.runAsync(() -> hello(), pool);

        var list = List.of(submit1, submit2, submit3, submit4);

        CompletableFuture.allOf(submit1, submit2, submit3, submit3).join();


//            hello();
//            hello();
//            hello();
//            hello();

    }

    public void execute2() {
        long start = System.nanoTime();
        hello();
        System.out.println("TIME " + (System.nanoTime() - start)/1_000_000);

        hello();
        System.out.println("TIME " + (System.nanoTime() - start)/1_000_000);

    }

    @Transactional
    public void execute2Transcional() {

        long start = System.nanoTime();

        //hello();
        helloRepository.findByName("lucas");
        System.out.println("TIME " + (System.nanoTime() - start)/1_000_000);

        //hello();
        helloRepository.findByName("lucas");
        System.out.println("TIME " + (System.nanoTime() - start)/1_000_000);

    }

    public void execute2Thread() {

//        CompletableFuture<?> submit1 = CompletableFuture.runAsync(() -> hello(), pool);
//        CompletableFuture<?> submit2 = CompletableFuture.runAsync(() -> hello(), pool);
//
//        CompletableFuture.allOf(submit1, submit2).join();

        var f1 = pool.submit(() -> hello());
        //var f2 = pool.submit(() -> hello());

        while (!f1.isDone()) {}
        //while (!f2.isDone()) {}
    }

    @Transactional
    public void execute2ThreadTransacional() {

        CompletableFuture<?> submit1 = CompletableFuture.runAsync(() -> hello(), pool);
        CompletableFuture<?> submit2 = CompletableFuture.runAsync(() -> hello(), pool);

        var list = List.of(submit1, submit2);

        CompletableFuture.allOf(submit1, submit2).join();

    }

    void execute1() {
        //jdbcTemplate.execute("SELECT 1 FROM hello");

//        var entityManager = entityManagerFactory.createEntityManager();
//
//        System.out.println(entityManager.createNativeQuery("SELECT 1 FROM hello where id = 1").getFirstResult());
    }

    @Transactional
    void execute1Transacional() {
        jdbcTemplate.execute("SELECT 1 FROM hello");
        //var query = entityManager.find(Hello.class, 1, LockModeType.NONE, Map.of(QueryHints.HINT_READONLY, true));
    }

    void jdbc() {
        testeRepository.consulta();
    }

    @Transactional
    void j1() {
        jdbcTemplate.execute("SELECT 1 FROM hello");
    }

    void save() {
        transaction.execute(status -> {
            helloRepository.save(new Hello("Java"));
            helloRepository.save(new Hello("JDBC"));
            helloRepository.save(new Hello("JPA"));
            helloRepository.save(new Hello("Hibernate"));
            return status;
        });
    }
}