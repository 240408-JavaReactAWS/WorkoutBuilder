package com.revature.services;

import com.revature.exceptions.NoSuchUserException;
import com.revature.models.Plan;
import com.revature.models.User;
import com.revature.repos.PlanDAO;
import com.revature.repos.UserDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PlanService {

    private final PlanDAO pd;

    private final UserDAO ud;

    @Autowired
    public PlanService(PlanDAO pd, UserDAO ud) {
        this.pd = pd;
        this.ud = ud;
    }

    public List<Plan> getPlansByUser(String username) throws NoSuchUserException {
        Optional<User> possibleUser = ud.findUserByUsername(username);

        if (possibleUser.isEmpty()){
            throw new NoSuchUserException("No user found with username: " + username);
        }

        User returnedUser = possibleUser.get();

        return pd.getPlansByOwner(returnedUser);

    }
}
