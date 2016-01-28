package education.elevator.system.service;

import education.elevator.system.entity.Elevator;
import education.elevator.system.entity.Floor;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by ynikolaiko on 1/28/16.
 */
public class ElevatorSimulator implements Runnable{
    ElevatorManager elevatorManager;
    ExecutorService elevatorsThreadPool;


    public ElevatorSimulator(ElevatorManager elevatorManager) {
        this.elevatorManager = elevatorManager;
        elevatorsThreadPool = Executors.newFixedThreadPool(ElevatorManager.ELEVATORS);
    }

    @Override
    public void run() {
        for(;;){

            Floor floor = elevatorManager.nextFloor();
            Elevator elevator = getElevator(floor);
            elevatorsThreadPool.execute(() -> elevator.run(floor));
        }
    }

    Elevator getElevator(Floor floor) {
        for(;;) {
            Elevator elevator = elevatorManager.requestElevator(floor);

            if(elevator!=null) return elevator;

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
