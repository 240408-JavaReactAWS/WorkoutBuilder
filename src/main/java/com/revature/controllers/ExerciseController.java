package com.revature.controllers;

import com.revature.models.Exercise;
import com.revature.services.ExerciseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("exercises")
public class ExerciseController {

    private final ExerciseService es;

    @Autowired
    public ExerciseController(ExerciseService es) {
        this.es = es;
    }

    @GetMapping
    public List<Exercise> getAllExercisesHandler(){
        return es.getAllExercises();
    }
}
