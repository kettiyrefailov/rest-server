package com.dev.controllers;

import com.dev.objects.User;
import com.dev.persists.UserPersist;
import com.dev.responses.BasicResponse;
import com.dev.responses.LoginResponse;
import com.dev.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import static com.dev.utils.Errors.*;

@RestController
public class LoginController {


    @Autowired
    private Utils utils;

    @Autowired
    private UserPersist userPersist;

    @RequestMapping (value = "login", method = RequestMethod.POST)
    public BasicResponse login (String username, String password) {
        BasicResponse basicResponse = new BasicResponse();
        boolean success = false;
        Integer errorCode = null;
        if (username != null) {
            if (password != null) {
                String token = utils.createHash(username, password);
                User fromDb = userPersist.getUserByUsernameAndToken(username, token);
                if (fromDb != null) {
                    success = true;
                    basicResponse = new LoginResponse(token);

                } else {
                    errorCode = ERROR_WRONG_LOGIN_CREDS;
                }
            } else {
                errorCode = ERROR_MISSING_PASSWORD;
            }
        } else {
            errorCode = ERROR_MISSING_USERNAME;
        }
        basicResponse.setSuccess(success);
        basicResponse.setErrorCode(errorCode);
        return basicResponse;
    }
}
