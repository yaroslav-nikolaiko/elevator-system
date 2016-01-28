package education.elevator.system.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

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
    volatile Integer people = 0;
    volatile Integer currentLevel = 0;

    public void run(Floor floor) {
        travel(floor.getLevel());
        Integer destinationLevel = floor.getDestinationLevel();
        int canTake = canTakePeople(floor);
        floor.setPeople(floor.getPeople() - canTake);
        this.people = canTake;
        travel(destinationLevel);
        this.people = 0;
    }

    void travel(Integer level) {
        if(currentLevel - level > 0){
            status = ElevatorStatus.DOWN;
            for(; currentLevel >= level; currentLevel--)
                mockPhysicalMovement();
        }
        else {
            status = ElevatorStatus.UP;
            for(; currentLevel <= level; currentLevel++)
                mockPhysicalMovement();
        }
        status = ElevatorStatus.IDLE;
    }

    int canTakePeople(Floor floor) {
        return floor.getPeople() < capacity ? floor.getPeople() : capacity;
    }

    void mockPhysicalMovement() {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
