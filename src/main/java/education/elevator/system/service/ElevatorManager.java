package education.elevator.system.service;

import education.elevator.system.entity.Elevator;
import education.elevator.system.entity.Floor;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.*;
import java.util.concurrent.*;
import java.util.stream.Collectors;

import static java.lang.Math.abs;

/**
 * Created by ynikolaiko on 1/28/16.
 */
@Service
public class ElevatorManager implements Runnable {
    static final int ELEVATORS=4;
    static final int FLOORS=10;
    Set<Elevator> elevatorsPool = Collections.newSetFromMap(new ConcurrentHashMap<>(ELEVATORS));
    List<Elevator> elevators = new ArrayList<>(ELEVATORS);
    BlockingQueue<Integer> levelsQueue = new LinkedBlockingQueue<>(FLOORS);
    ConcurrentHashMap<Integer, Floor> floors = new ConcurrentHashMap<>(FLOORS);


    public ElevatorManager(){
        initializeFloors();
        initializeElevators();
    }

    @PostConstruct
    synchronized void start() {
        initFloorsQueue();
        Thread simulatorThread = new Thread(this, "Elevator Simulator Thread");
        simulatorThread.setUncaughtExceptionHandler((thread, ex) -> start());
        simulatorThread.start();
    }

    public Collection<Floor> getFloors() {
        return floors.values();
    }

    public Collection<Elevator> getElevators() {
        return elevators;
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
            elevator.run(floor,
                    () -> elevatorsPool.add(elevator),
                    () -> {
                        if(floor.getPeople()>0) levelsQueue.add(floor.getLevel());
                    });
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
        //if(elevatorsPool.isEmpty()) return null;
        Elevator elevator = Collections.min(elevatorsPool,
                (e1, e2) -> abs(level - e1.getCurrentLevel()) - abs(level - e2.getCurrentLevel()));
        return elevatorsPool.remove(elevator) ? elevator : null;
    }

    private void initializeFloors() {
        for (int i=1; i<=FLOORS; i++)
            floors.put(i, new Floor(i));
    }

    private void initializeElevators() {
        elevators.add(new Elevator("A"));
        elevators.add(new Elevator("B"));
        elevators.add(new Elevator("C"));
        elevators.add(new Elevator("D"));

        elevatorsPool.addAll(elevators);
    }

    public void initFloorsQueue() {
        levelsQueue.clear();
        levelsQueue.addAll(floors.entrySet().stream()
                .filter(e->e.getValue().getPeople() > 0)
                .map(Map.Entry::getKey)
                .collect(Collectors.toSet()));
    }
}
