package education.elevator.system.service;

import education.elevator.system.entity.Elevator;
import education.elevator.system.entity.ElevatorStatus;
import education.elevator.system.entity.Floor;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Collection;
import java.util.Collections;
import java.util.Set;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by ynikolaiko on 1/28/16.
 */
@Service
public class ElevatorManager {
    static final int ELEVATORS=4;
    static final int FLOORS=10;
    Set<Elevator> elevatorsPool = Collections.newSetFromMap(new ConcurrentHashMap<>(ELEVATORS));
    BlockingQueue<Integer> levelsQueue = new LinkedBlockingQueue<>(FLOORS);
    ConcurrentHashMap<Integer, Floor> floors = new ConcurrentHashMap<>(FLOORS);
    public ElevatorManager(){
        initializeFloors();
        initializeElevators();
    }

    @PostConstruct
    void start(){
        new Thread(new ElevatorSimulator(this)).start();
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

    public Floor nextFloor() {
        Integer level = null;
        try {
            level = levelsQueue.take();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return floors.get(level);
    }

    public Elevator requestElevator(Floor floor) {
        Integer level = floor.getLevel();
        return  elevatorsPool.stream()
                .filter(elevator -> elevator.getStatus() == ElevatorStatus.IDLE)
                .sorted((e1, e2) -> Math.abs(level - e1.getCurrentLevel()) - Math.abs(level - e2.getCurrentLevel()))
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
