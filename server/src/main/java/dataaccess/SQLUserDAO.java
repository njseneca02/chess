package dataaccess;

import model.UserData;

import java.util.Collection;
import java.util.List;

public class SQLUserDAO implements UserDAO{

    public void createUser(UserData u) throws DataAccessException {

    }


    public UserData getUser(String username) throws DataAccessException {
        return null;
    }


    public void clear() throws DataAccessException {

    }


    public Collection<UserData> getDatabase() {
        return List.of();
    }
}
