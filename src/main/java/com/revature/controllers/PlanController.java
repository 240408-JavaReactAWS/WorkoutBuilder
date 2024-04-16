package com.revature.controllers;

import com.revature.exceptions.NoSuchUserException;
import com.revature.models.Plan;
import com.revature.services.PlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedList;
import java.util.List;

@RestController
@RequestMapping("plans")
public class PlanController {

    private final PlanService ps;

    @Autowired
    public PlanController(PlanService ps) {
        this.ps = ps;
    }

    @GetMapping
    public ResponseEntity<List<Plan>> getAllPlansFromUserHandler(
            @RequestHeader(name = "user", required = false) String username){

        if (username.isEmpty()){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        List<Plan> plans = new LinkedList<>();

        try{
            plans = ps.getPlansByUser(username);
        } catch (NoSuchUserException e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(plans, HttpStatus.OK);
    }
}
