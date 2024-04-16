package com.revature.services;

import com.revature.exceptions.InvalidCredentialsException;
import com.revature.exceptions.NoSuchExerciseException;
import com.revature.exceptions.NoSuchUserException;
import com.revature.models.Exercise;
import com.revature.models.Role;
import com.revature.models.User;
import com.revature.repos.ExerciseDAO;
import com.revature.repos.UserDAO;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ExerciseService {

    private final ExerciseDAO ed;

    private final UserDAO ud;

    @Autowired
    public ExerciseService(ExerciseDAO ed, UserDAO ud) {
        this.ed = ed;
        this.ud = ud;
    }


    public List<Exercise> getAllExercises() {
        return ed.findAll();
    }

    public Exercise saveNewExercise(String username, Exercise exercise) throws
            NoSuchUserException, InvalidCredentialsException {

        Optional<User> possibleUser = ud.findUserByUsername(username);

        if (possibleUser.isEmpty()){
            throw new NoSuchUserException("No user found with username: " + username);
        }
        User returnedUser = possibleUser.get();

        if (!returnedUser.getRole().equals(Role.ADMIN)){
            throw new InvalidCredentialsException("User: " +username +" is not an admin");
        }

        return ed.save(exercise);
    }

    public Exercise updateExercise(String username, Exercise exercise) throws
            NoSuchUserException, InvalidCredentialsException, NoSuchExerciseException {

        Optional<User> possibleUser = ud.findUserByUsername(username);

        if (possibleUser.isEmpty()){
            throw new NoSuchUserException("No user found with username: " + username);
        }
        User returnedUser = possibleUser.get();

        if (!returnedUser.getRole().equals(Role.ADMIN)){
            throw new InvalidCredentialsException("User: " +username +" is not an admin");
        }

        // Updating logic
        Optional<Exercise> possibleExercise = ed.findById(exercise.getExerciseId());

        if (possibleExercise.isEmpty()){
            throw new NoSuchExerciseException("No exercise with id: " + exercise.getExerciseId());
        }

        return ed.save(exercise);
    }

    public boolean deleteExerciseById(String username, int id) throws
            NoSuchUserException, InvalidCredentialsException, NoSuchExerciseException {

        Optional<User> possibleUser = ud.findUserByUsername(username);

        if (possibleUser.isEmpty()){
            throw new NoSuchUserException("No user found with username: " + username);
        }
        User returnedUser = possibleUser.get();

        if (!returnedUser.getRole().equals(Role.ADMIN)){
            throw new InvalidCredentialsException("User: " +username +" is not an admin");
        }

        Optional<Exercise> possibleExercise = ed.findById(id);

        if (possibleExercise.isEmpty()){
            throw new NoSuchExerciseException("No exercise with id: " + id);
        }

        ed.deleteById(id);

        return !ed.existsById(id);

    }
}
