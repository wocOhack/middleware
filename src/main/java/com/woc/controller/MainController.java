package com.woc.controller;

import com.woc.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.woc.service.UserService;
import com.woc.entity.Driver;
import com.woc.entity.Pricing;
import com.woc.entity.Rider;
import com.woc.entity.User;
import com.woc.service.WOCService;

@RestController
@EnableAutoConfiguration
@RequestMapping("/woc")
public class MainController {

    @Autowired
    WOCService wocService;

    @Autowired
    UserService userService;

    @GetMapping("/greet")
    @ResponseBody
    public String hello() {
        return "Greetings!";
    }

    @GetMapping("/users")
    @ResponseBody
    public Iterable<User> getUsers() {
        return wocService.getAllUsers();
    }

    @PostMapping("/users")
    public User createNewUser(@RequestBody User newUser) {
        return userService.createUser(newUser);
    }

    @GetMapping("/prices")
    @ResponseBody
    public Iterable<Pricing> getPrices() {
        return wocService.getAllPrices();
    }

    @GetMapping("/available-drivers")
    @ResponseBody
    public Iterable<DriverAvailability> getDriverAvailabilityList() {
        return wocService.getDriverAvailabilityList();
    }

    @GetMapping("/feedbacks")
    @ResponseBody
    public Iterable<Feedback> getFeedbacks() {
        return wocService.getAllFeedbacks();
    }

    @GetMapping("/trips")
    @ResponseBody
    public Iterable<Trip> getTrips() {
        return wocService.getAllTrips();
    }

    @PostMapping("/trips")
    @ResponseBody
    public Trip createTrip(@RequestBody Trip newTrip) {
        return wocService.createTrip(newTrip);
    }

    @GetMapping("/user-locations")
    @ResponseBody
    public Iterable<UserLocation> getUserLocationList() {
        return wocService.getUserLocationList();
    }

    @GetMapping("/vehicles")
    @ResponseBody
    public Iterable<Vehicle> getVehicles() {
        return wocService.getAllVehicles();
    }

    @GetMapping("/drivers")
    @ResponseBody
    public Iterable<Driver> getDrivers() {
        return wocService.getAllDrivers();
    }

    @PostMapping("/rider")
    @ResponseBody
    public void addDriver(@RequestBody Rider rider) {
        // return wocService.getAllDrivers();
        wocService.addRider(rider);
    }

    @PostMapping("/driver")
    @ResponseBody
    public void addDriver(@RequestBody Driver driver) {
        // return wocService.getAllDrivers();
        wocService.addDriver(driver);
    }

    @PostMapping("/user")
    @ResponseBody
    public void addUser(@RequestBody User u) {
        // return wocService.getAllDrivers();
        System.out.println("user request : " + u.toString());
        wocService.addUser(u);
    }

    @GetMapping("/riders")
    @ResponseBody
    public Iterable<Rider> getRiders() {
        return wocService.getAllRiders();
    }

    @PostMapping("/addUserCredentials")
    @ResponseBody
    public void addUserCredentials(@RequestBody UserCredentials uc) {
        System.out.println("user cred request : " + uc.toString());
        userService.createUserCreds(uc);
    }
}