package jh.jh.springstatemachine.config;

import jh.jh.springstatemachine.domain.ClusterEvent;
import jh.jh.springstatemachine.domain.ClusterState;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.config.EnableStateMachine;
import org.springframework.statemachine.config.EnumStateMachineConfigurerAdapter;
import org.springframework.statemachine.config.builders.StateMachineConfigurationConfigurer;
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;
import org.springframework.statemachine.listener.StateMachineListener;
import org.springframework.statemachine.listener.StateMachineListenerAdapter;
import org.springframework.statemachine.state.State;

import java.util.EnumSet;

@Configuration
@EnableStateMachine
@Slf4j
public class ClusterStateMachineConfig
        extends EnumStateMachineConfigurerAdapter<ClusterState, ClusterEvent> {

    @Override
    public void configure(StateMachineConfigurationConfigurer<ClusterState, ClusterEvent> config) throws Exception {
        config
                .withConfiguration()
                .autoStartup(true)
                .listener(listener());
    }

    @Override
    public void configure(StateMachineStateConfigurer<ClusterState, ClusterEvent> states) throws Exception {
        states
                .withStates()
                .initial(ClusterState.NOT_CREATED)
                .states(EnumSet.allOf(ClusterState.class));
    }

    @Override
    public void configure(StateMachineTransitionConfigurer<ClusterState, ClusterEvent> transitions)
            throws Exception {
        transitions
                .withExternal()
                .source(ClusterState.NOT_CREATED).target(ClusterState.CREATED).event(ClusterEvent.CREATE)

                .and()
                .withExternal()
                .source(ClusterState.CREATED).target(ClusterState.BOOTSTRAPED).event(ClusterEvent.BOOTSTRAP)

                .and()
                .withExternal()
                .source(ClusterState.BOOTSTRAPED).target(ClusterState.CREATED).event(ClusterEvent.READY)

                .and()
                .withExternal()
                .source(ClusterState.CREATED).target(ClusterState.FINISHED).event(ClusterEvent.FINISH);
    }

    @Bean
    public StateMachineListener<ClusterState, ClusterEvent> listener() {
        return new StateMachineListenerAdapter<ClusterState, ClusterEvent>() {
            @Override
            public void stateChanged(State<ClusterState, ClusterEvent> from, State<ClusterState, ClusterEvent> to) {
                System.out.println("******************");
                System.out.println("=======> State change to " + to.getId());
                System.out.println("********* state from: " + from.toString() + " to: " +  to.toString());
                System.out.println("******************");

            }
        };
    }
}
