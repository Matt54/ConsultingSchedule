package consultingschedule;

public class CurrentUser {
    private CurrentUser() {}
    private User user;

    private static final CurrentUser instance = new CurrentUser();

    public static CurrentUser getInstance() {
        return instance;
    }
    
    public synchronized User getUser(){
        return user;
    }
    
    public synchronized void setUser(User _user){
        user = _user;
    }

}