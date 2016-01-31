import {Component, OnInit} from 'angular2/core';
import {HTTP_PROVIDERS}    from 'angular2/http';
import {Floor} from './floor';
import {ElevatorComponent} from './elevator.component';
import {FloorService} from './floor.service';
@Component({
    selector: 'my-app',
    templateUrl: "template/app.html",
    styleUrls: ["css/app.css"],
    directives: [ElevatorComponent],
    providers: [HTTP_PROVIDERS, FloorService]
})
export class AppComponent implements OnInit {
    public title = 'Elevator System';
    public floors: Floor[];
    constructor(private _floorService: FloorService) { }
    getFloors() {
        this._floorService.getFloors().subscribe(
            floors => this.floors = floors,
            error => alert(`Server error. Try again later`));
    }

    ngOnInit() {
        this.getFloors();
    }

    onSelect(floor: Floor) {
        this._floorService.update(floor)
    }
}
