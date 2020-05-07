package com.habittracker.Managers.IntegrationTests;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

import static org.junit.Assert.assertEquals;

import com.habittracker.Managers.UserManager;
import com.habittracker.Database.DB.UserDB;
import com.habittracker.Database.UserInterface;
import com.habittracker.Utils.TestUtils;


public class UserManagerIT {
    private File tempDB;

    @Before
    public void setUp() throws IOException {
        tempDB = TestUtils.copyDB();
        UserInterface userInterface = new UserDB(tempDB.getAbsolutePath().replace(".script",""));
        new UserManager( userInterface );
    }

    @Test
    public void testLogin(){
        //able to fetch the user from db
        assertEquals(UserManager.SUCCESS, UserManager.login("userA", "pass"));
    }

    @Test
    public void testRegister(){
        UserManager.register("fake", "123");
        //
        //should be able to fetch the newly registered user
        assertEquals(UserManager.SUCCESS, UserManager.login("fake","123"));
    }

    @Test
    public void testChangePassword(){
        UserManager.changePassword("userA", "update");

        //old info shouldn't work anymore
        assertEquals(UserManager.DB_FAIL, UserManager.login("userA", "pass"));
        //should be able to login with the updated password
        assertEquals(UserManager.SUCCESS, UserManager.login("userA", "update"));
    }

    @After
    public void tearDown() {
        // reset DB
        tempDB.delete();
    }
}
