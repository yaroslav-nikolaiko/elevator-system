package education.elevator.system.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by ynikolaiko on 1/28/16.
 */
@EqualsAndHashCode(of = {"name"})
@RequiredArgsConstructor
@Getter
public class Elevator {
    final static Integer capacity = 20;
    final String name;
    volatile ElevatorStatus status = ElevatorStatus.IDLE;
    AtomicInteger people = new AtomicInteger(0);
    AtomicInteger currentLevel = new AtomicInteger(1);

    public void run(Floor floor) {
        travel(floor.getLevel());
        Integer destinationLevel = floor.getDestinationLevel();
        int canTake = canTakePeople(floor);
        floor.setPeople(floor.getPeople() - canTake);
        people.set(canTake);
        travel(destinationLevel);
        people.set(0);
        status = ElevatorStatus.IDLE;
    }

    void travel(Integer level) {
        if(currentLevel.get() - level > 0){
            status = ElevatorStatus.DOWN;
            for(; currentLevel.get() > level; currentLevel.decrementAndGet())
                mockPhysicalMovement();
        }
        else {
            status = ElevatorStatus.UP;
            for(; currentLevel.get() < level; currentLevel.incrementAndGet())
                mockPhysicalMovement();
        }
    }

    int canTakePeople(Floor floor) {
        return floor.getPeople() < capacity ? floor.getPeople() : capacity;
    }

    void mockPhysicalMovement() {
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
