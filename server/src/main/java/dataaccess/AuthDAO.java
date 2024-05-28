package dataaccess;

import model.AuthData;

import java.util.Collection;

public interface AuthDAO {

    public void createAuth(AuthData u) throws DataAccessException;

    public AuthData getAuth(String token) throws DataAccessException;

    public void deleteAuth(AuthData u) throws DataAccessException;

    public void clear() throws DataAccessException;

    public Collection<AuthData> getDatabase();


}
