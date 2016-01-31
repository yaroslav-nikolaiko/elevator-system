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
    private timerId: number;
    constructor(private _elevatorService: ElevatorService, private window: Window) { }
    getElevators() {
        this._elevatorService.getElevators().subscribe(
            elevators => this.elevators = elevators,
            error => window.clearInterval(this.timerId));
    }

    ngOnInit() {
        var self = this;
        this.timerId = window.setInterval(()=>self.getElevators(), 1000);
    }
}
