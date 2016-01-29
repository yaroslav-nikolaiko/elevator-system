package education.elevator.system.service;

import education.elevator.system.entity.Elevator;
import education.elevator.system.entity.ElevatorStatus;
import education.elevator.system.entity.Floor;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Collection;
import java.util.Collections;
import java.util.Set;
import java.util.concurrent.*;

/**
 * Created by ynikolaiko on 1/28/16.
 */
@Service
public class ElevatorManager implements Runnable {
    static final int ELEVATORS=4;
    static final int FLOORS=10;
    Set<Elevator> elevatorsPool = Collections.newSetFromMap(new ConcurrentHashMap<>(ELEVATORS));
    BlockingQueue<Integer> levelsQueue = new LinkedBlockingQueue<>(FLOORS);
    ConcurrentHashMap<Integer, Floor> floors = new ConcurrentHashMap<>(FLOORS);
    ExecutorService elevatorsThreadPool;


    public ElevatorManager(){
        elevatorsThreadPool = Executors.newFixedThreadPool(ELEVATORS);
        initializeFloors();
        initializeElevators();
    }

    @PostConstruct
    void start(){
        new Thread(this).start();
    }

    public Collection<Floor> getFloors() {
        return floors.values();
    }

    public Collection<Elevator> getElevators() {
        return elevatorsPool;
    }

    public void updateFloor(Floor floor) {
        floors.put(floor.getLevel(), floor);
        if (! levelsQueue.contains(floor.getLevel())) {
            levelsQueue.add(floor.getLevel());
        }
    }

    @Override
    public void run() {
        for(;;){
            Floor floor = nextFloor();
            Elevator elevator = requestElevator(floor);
            elevatorsThreadPool.execute(() -> elevator.run(floor));
        }
    }

    Floor nextFloor() {
        Integer level = null;
        try {
            level = levelsQueue.take();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return floors.get(level);
    }


    Elevator requestElevator(Floor floor) {
        for(;;) {
            Elevator elevator = getAvailableElevator(floor);

            if(elevator!=null) return elevator;

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    Elevator getAvailableElevator(Floor floor) {
        Integer level = floor.getLevel();
        return  elevatorsPool.stream()
                .filter(elevator -> elevator.getStatus() == ElevatorStatus.IDLE)
                .sorted((e1, e2) -> Math.abs(level - e1.getCurrentLevel().get()) - Math.abs(level - e2.getCurrentLevel().get()))
                .findFirst()
                .orElse(null);
    }

    private void initializeFloors() {
        for (int i=1; i<=FLOORS; i++)
            floors.put(i, new Floor(i));
    }

    private void initializeElevators() {
        elevatorsPool.add(new Elevator("A"));
        elevatorsPool.add(new Elevator("B"));
        elevatorsPool.add(new Elevator("C"));
        elevatorsPool.add(new Elevator("D"));
    }
}
