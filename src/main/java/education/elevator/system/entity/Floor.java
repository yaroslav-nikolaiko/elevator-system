package education.elevator.system.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.stereotype.Service;

/**
 * Created by ynikolaiko on 1/28/16.
 */

@Data
@EqualsAndHashCode(of={"level"})
public class Floor {
    final Integer level;
    volatile Integer people;
    volatile Integer destinationLevel;

    public Floor() {
        this.level = -1;
    }

    public Floor(Integer level) {
        this.level = level;
    }
}
