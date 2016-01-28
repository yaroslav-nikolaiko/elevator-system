package education.elevator.system.entity;

import lombok.Data;
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
        travelToLevel(floor.getLevel());
        Integer destinationLevel = floor.getDestinationLevel();
        int canTake = floor.getPeople() - capacity;
        canTake = canTake < 0 ? floor.getPeople() : canTake;
        floor.setPeople(floor.getPeople() - canTake);
        this.people = canTake;
        travelToLevel(destinationLevel);
        this.people = 0;
    }

    void travelToLevel(Integer level) {
        if(currentLevel - level > 0){
            status = ElevatorStatus.DOWN;
            for(; currentLevel>= level; currentLevel--)
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
        }
        else {
            status = ElevatorStatus.UP;
            for(; currentLevel<= level; currentLevel++)
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
        }

        status = ElevatorStatus.IDLE;
    }
}
