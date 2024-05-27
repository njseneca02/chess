package dataaccess;

import model.UserData;

import javax.xml.crypto.Data;

public interface UserDAO {

    public void createUser(UserData u) throws DataAccessException;

    public UserData getUser(String username) throws DataAccessException;

    public void clear() throws DataAccessException;

}
