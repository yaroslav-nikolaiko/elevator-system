package education.elevator.system.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.bind.annotation.XmlTransient;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by ynikolaiko on 1/28/16.
 */
@EqualsAndHashCode(of = {"name"})
@RequiredArgsConstructor
@Getter
public class Elevator {
    private static final Logger LOGGER = LoggerFactory.getLogger(Elevator.class);
    final static Integer capacity = 20;
    final String name;
    volatile ElevatorStatus status = ElevatorStatus.IDLE;
    volatile int people = 0;
    volatile int currentLevel = 1;
    @JsonIgnore
    ExecutorService executorService = Executors.newSingleThreadExecutor();

    public void run(Floor floor, Runnable pickUpLevelArrivalCallback, Runnable destinationLevelArrivalCallback) {
        executorService.submit(()->{
            travel(floor.getLevel());
            Integer destinationLevel = floor.getDestinationLevel();
            int canTake = canTakePeople(floor);
            floor.setPeople(floor.getPeople() - canTake);
            pickUpLevelArrivalCallback.run();
            people = canTake;
            travel(destinationLevel);
            people = 0;
            status = ElevatorStatus.IDLE;
            destinationLevelArrivalCallback.run();
        });
    }

    void travel(Integer level) {
        if(currentLevel - level > 0){
            status = ElevatorStatus.DOWN;
            for(; currentLevel > level; LOGGER.info("Elevator "+name+" level " + --currentLevel))
                mockPhysicalMovement();

        }
        else {
            status = ElevatorStatus.UP;
            for(; currentLevel< level; LOGGER.info("Elevator "+name+" level "+ ++currentLevel))
                mockPhysicalMovement();
        }
    }

    int canTakePeople(Floor floor) {
        return floor.getPeople() < capacity ? floor.getPeople() : capacity;
    }

    void mockPhysicalMovement() {
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
