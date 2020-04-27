import { Component, OnInit } from '@angular/core';
import {ActivatedRoute} from "@angular/router";
import {UserService} from "../services";
import {first} from "rxjs/operators";
import {User} from "../model/user.model";

@Component({ templateUrl: 'home.component.html' })
export class HomeComponent {

    returnUrl: string;
    error: string;
    success: string;
    users: User[];
// users =[];

    constructor(
        private route: ActivatedRoute,
        private userService: UserService
    ) {}

    ngOnInit() {
        // get return url from route parameters or default to '/'
        this.returnUrl = this.route.snapshot.queryParams['returnUrl'] || '/';

        // show success message on registration
        if (this.route.snapshot.queryParams['registered']) {
            debugger;
            this.success= 'Succesfully registered new user: ' + this.route.snapshot.queryParams['username'];
        }

        this.loadAllUsers();
    }

    private loadAllUsers() {
        this.userService.getAll()
            // .pipe(first())
            .subscribe(users => {debugger;
                this.users = users});
    }
}
