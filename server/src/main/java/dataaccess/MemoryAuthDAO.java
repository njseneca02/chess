package dataaccess;

import model.AuthData;

import java.util.ArrayList;

public class MemoryAuthDAO implements AuthDAO{

    private ArrayList<AuthData> authDatabase;

    public MemoryAuthDAO(){
        this.authDatabase = new ArrayList<>();
    }

    public void createAuth(AuthData u) throws DataAccessException{
        authDatabase.add(u);
    }

    public AuthData getAuth(String token) throws DataAccessException{
        for(AuthData e: authDatabase){
            if(e.authToken().equals(token)){
                return e;
            }
        }
        return null;
    }

    public void deleteAuth(AuthData u) throws DataAccessException{
        authDatabase.remove(u);
    }

    public void clear() throws DataAccessException{
        authDatabase.clear();
    }

}
