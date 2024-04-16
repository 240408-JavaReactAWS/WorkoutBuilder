package com.revature.controllers;

import com.revature.exceptions.InvalidCredentialsException;
import com.revature.exceptions.NoSuchPlanException;
import com.revature.exceptions.NoSuchUserException;
import com.revature.models.Plan;
import com.revature.services.PlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

        if (username == null){
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

    @GetMapping("{id}")
    public ResponseEntity<Plan> getPlanByIdAndUserHandler(
            @RequestHeader(name = "user", required = false) String username,
            @PathVariable int id
    ){
        if (username == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        Plan returnedPlan;

        try{
            returnedPlan = ps.getPlanByUserAndId(username, id);
        } catch( InvalidCredentialsException e){
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        } catch (NoSuchUserException | NoSuchPlanException e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(returnedPlan, HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<Plan> updatePlanByUserHandler(
            @RequestHeader(name = "user", required = false) String username,
            @RequestBody Plan plan
    ){
        if (username == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        Plan updatedPlan;

        try{
            updatedPlan = ps.updatePlanByUser(plan, username);
        } catch( InvalidCredentialsException e){
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        } catch (NoSuchUserException | NoSuchPlanException e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(updatedPlan, HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Object> deletePlansByUserHandler(
            @RequestHeader(name = "user", required = false) String username,
            @PathVariable int id
    ){
        if (username == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        boolean successfullyDeleted = false;

        try{
            successfullyDeleted = ps.deleteByUserAndId(username, id);
        } catch( InvalidCredentialsException e){
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        } catch (NoSuchUserException | NoSuchPlanException e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(successfullyDeleted, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Plan> createPlanByUser(
            @RequestHeader(name = "user", required = false) String username,
            @RequestBody Plan plan
    ){
        if (username == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        Plan savedPlan;

        try{
            savedPlan = ps.savePlanByUser(plan, username);
        } catch (NoSuchUserException e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(savedPlan, HttpStatus.OK);
    }
}
