package jh.jh.springstatemachine;

import jh.jh.springstatemachine.domain.ClusterEvent;
import jh.jh.springstatemachine.domain.ClusterState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.statemachine.StateMachine;

@SpringBootApplication
public class SpringstatemachineApplication implements CommandLineRunner {

    @Autowired
    private StateMachine<ClusterState, ClusterEvent> stateMachine;

    public static void main(String[] args) {
        SpringApplication.run(SpringstatemachineApplication.class, args);
    }


    @Override
    public void run(String... args) throws Exception {
        System.out.println("========> INIT PROGRAM");
        stateMachine.sendEvent(ClusterEvent.CREATE);
        stateMachine.sendEvent(ClusterEvent.BOOTSTRAP);
    }
}
