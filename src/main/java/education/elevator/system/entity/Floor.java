package education.elevator.system.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.stereotype.Service;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by ynikolaiko on 1/28/16.
 */

@Data
@EqualsAndHashCode(of={"level"})
public class Floor {
    final Integer level;
    AtomicInteger people = new AtomicInteger(0);
    AtomicInteger destinationLevel = new AtomicInteger(1);

    public Floor() {
        this.level = -1;
    }

    public Floor(Integer level) {
        this.level = level;
    }

    public int getPeople() {
        return people.get();
    }

    public void setPeople(int people) {
        this.people.set(people);
    }

    public int getDestinationLevel() {
        return destinationLevel.get();
    }

    public void setDestinationLevel(int destinationLevel) {
        this.destinationLevel.set(destinationLevel);
    }
}
