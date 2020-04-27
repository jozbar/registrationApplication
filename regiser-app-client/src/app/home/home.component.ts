import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from "@angular/router";
import { User } from "../model/user.model";
import { UserService } from "../services/user.service";

@Component({ templateUrl: 'home.component.html' })
export class HomeComponent implements OnInit {

    returnUrl: string;
    error: string;
    success: string;
    users: User[];

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
            .subscribe(users => {debugger;
                this.users = users});
    }
}
