package dataaccess;

import model.AuthData;

public interface AuthDAO {

    public void createAuth(AuthData u);

    public AuthData getAuth(String token);

    public void deleteAuth();

}
