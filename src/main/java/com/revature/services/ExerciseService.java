package com.revature.services;

import com.revature.models.Exercise;
import com.revature.repos.ExerciseDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExerciseService {

    private final ExerciseDAO ed;

    @Autowired
    public ExerciseService(ExerciseDAO ed) {
        this.ed = ed;
    }


    public List<Exercise> getAllExercises() {
        return ed.findAll();
    }
}
