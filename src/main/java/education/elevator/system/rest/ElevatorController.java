package education.elevator.system.rest;

import education.elevator.system.entity.Elevator;
import education.elevator.system.entity.Floor;
import education.elevator.system.service.ElevatorManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

/**
 * Created by ynikolaiko on 1/28/16.
 */
@RestController
public class ElevatorController {
    @Autowired
    ElevatorManager elevatorManager;

    @RequestMapping(value = "/floors", method = RequestMethod.GET)
    Collection<Floor> getFloors() {
        return elevatorManager.getFloors();
    }

    @RequestMapping(value = "/elevators", method = RequestMethod.GET)
    Collection<Elevator> getElevators() {
        return elevatorManager.getElevators();
    }

    @RequestMapping(value = "/floors", method = RequestMethod.PUT)
    void getElevators(@RequestBody Floor floor) {
        elevatorManager.updateFloor(floor);
    }
}
