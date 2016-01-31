export interface Elevator {
    name: string;
    status: string;
    people: number;
    currentLevel: number;
}

export interface Floor {
    level: number;
    people: number;
    destinationLevel: number;
}