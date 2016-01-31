import {Floor} from './floor';
import {Elevator} from './elevator';
export var FLOORS: Floor[] = [
    {"level": 1, "people": 0, "destinationLevel": 10},
    {"level": 2, "people": 0, "destinationLevel": 10},
    {"level": 3, "people": 0, "destinationLevel": 10},
    {"level": 4, "people": 0, "destinationLevel": 10},
    {"level": 5, "people": 0, "destinationLevel": 10},
    {"level": 6, "people": 0, "destinationLevel": 10},
    {"level": 7, "people": 0, "destinationLevel": 10},
    {"level": 8, "people": 0, "destinationLevel": 10},
    {"level": 9, "people": 0, "destinationLevel": 10},
    {"level": 10, "people": 0, "destinationLevel": 10}
];

export var ELEVATORS: Elevator[] = [
    {"name": "A", "status": "IDLE", "people": 0, "currentLevel": 0},
    {"name": "B", "status": "IDLE", "people": 0, "currentLevel": 0},
    {"name": "C", "status": "IDLE", "people": 0, "currentLevel": 0},
    {"name": "D", "status": "IDLE", "people": 0, "currentLevel": 0}
];
