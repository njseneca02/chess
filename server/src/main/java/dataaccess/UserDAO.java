package dataaccess;

import model.UserData;

import java.util.Collection;

public interface UserDAO {

    public void createUser(UserData u) throws DataAccessException;

    public UserData getUser(String username) throws DataAccessException;

    public void clear() throws DataAccessException;

    public Collection<UserData> getDatabase();

}
