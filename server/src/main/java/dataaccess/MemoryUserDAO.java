package dataaccess;

import model.UserData;

import java.util.HashSet;

public class MemoryUserDAO implements UserDAO{

    private HashSet<UserData> userDatabase;

    public MemoryUserDAO(){
        this.userDatabase = new HashSet<>();
    }

    public void createUser(UserData u) throws DataAccessException{
        userDatabase.add(u);
    }

    public UserData getUser(String username) throws DataAccessException{
        for(UserData itr : userDatabase){
            if(itr.username().equals(username)){
                return itr;
            }
        }
        return null;
    }
    public void clear(){
        userDatabase.clear();
    }

}
