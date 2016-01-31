import {Component} from 'angular2/core';
import {Elevator} from './elevator';
import {ElevatorService} from './elevator.service';
@Component({
    selector: 'elevators',
    templateUrl: "template/elevators.html",
    providers: [ElevatorService]

})
export class ElevatorComponent {
    public elevators: Elevator[];
    constructor(private _elevatorService: ElevatorService) { }
    getElevators() {
        this._elevatorService.getElevators().subscribe(
            elevators => this.elevators = elevators,
            error => alert(`Server error. Try again later`));
    }

    ngOnInit() {
        this.getElevators();
    }
}
