/** Represents a social network. The network has users, who follow other uesrs.
 *  Each user is an instance of the User class. */
public class Network {

    // Fields
    private User[] users;  // the users in this network (an array of User objects)
    private int userCount; // actual number of users in this network

    /** Creates a network with a given maximum number of users. */
    public Network(int maxUserCount) {
        this.users = new User[maxUserCount];
        this.userCount = 0;
    }

    /** Creates a network  with some users. The only purpose of this constructor is 
     *  to allow testing the toString and getUser methods, before implementing other methods. */
    public Network(int maxUserCount, boolean gettingStarted) {
        this(maxUserCount);
        users[0] = new User("Foo");
        users[1] = new User("Bar");
        users[2] = new User("Baz");
        userCount = 3;
    }

    public int getUserCount() {
        return this.userCount;
    }

    /** Finds in this network, and returns, the user that has the given name.
     *  If there is no such user, returns null.
     *  Notice that the method receives a String, and returns a User object. */
    public User getUser(String name) {
        for (int i=0; i<userCount; i++){
             if (users[i].getName().equals(name) ){
                return users[i];
             }
        }
        return null;
    }

    /** Adds a new user with the given name to this network.
    *  If ths network is full, does nothing and returns false;
    *  If the given name is already a user in this network, does nothing and returns false;
    *  Otherwise, creates a new user with the given name, adds the user to this network, and returns true. */
    public boolean addUser(String name) {
        if ((users.length == userCount) || getUser(name) != null){
            return false;
            }
            else {
                users[this.userCount] = new User(name);
                userCount++;
            }   
         return true;
    }

    /** Makes the user with name1 follow the user with name2. If successful, returns true.
     *  If any of the two names is not a user in this network,
     *  or if the "follows" addition failed for some reason, returns false. */
   public boolean addFollowee(String name1, String name2) {
    // 1. קודם שולפים את האובייקטים פעם אחת בלבד
    User user1 = getUser(name1);
    User user2 = getUser(name2);
    if (user1 == null || user2 == null) {
        return false;
    }
    return user1.addFollowee(name2);
    }
    
    /** For the user with the given name, recommends another user to follow. The recommended user is
     *  the user that has the maximal mutual number of followees as the user with the given name. */
   public String recommendWhoToFollow(String name) {
    User user1 = getUser(name);
    // אם המשתמש בכלל לא קיים ברשת, אין את מי להמליץ
    if (user1 == null) {
        return null;
    }

    String bestName = null;
    int maxCounter = -1;

    for (int i = 0; i < userCount; i++) {
        // 1. אנחנו לא רוצים להמליץ למשתמש על עצמו
        if (!users[i].getName().equals(name)) {
            
            // 2. קריאה לפונקציית העזר שבנית
            int currentCommon = MutualFollows(name, users[i].getName());

            // 3. עדכון ה"שיא" - אם מצאנו מישהו עם יותר משותפים
            if (currentCommon > maxCounter) {
                maxCounter = currentCommon;
                bestName = users[i].getName();
            }
        }
    }
    
    // החזרת השם של המשתמש בעל מספר המשותפים הגבוה ביותר
    return bestName;
}
   public int MutualFollows(String name1, String name2) {
    User u1 = getUser(name1);
    User u2 = getUser(name2);
    int counter = 0;

    if (u1 == null || u2 == null) return 0;

    // רצים על כל המשתמשים שיש לנו ברשת (השמות הקיימים)
    for (int i = 0; i < userCount; i++) {
        String nameToCheck = users[i].getName();
        // שואלים את u1 וגם את u2 אם הם עוקבים אחריו
        if (u1.follows(nameToCheck) && u2.follows(nameToCheck)) {
            counter++;
        }
    }
    return counter;
    }

    /** Computes and returns the name of the most popular user in this network: 
     *  The user who appears the most in the follow lists of all the users. */
    public String mostPopularUser() {
        String MostPopUser = null;
        int MaxAppears = -1;

        if (userCount == 0) {
        return null;
        }
        for (int i =0; i<userCount; i++){
            String currentName = users[i].getName();
            int currentCount = followeeCount(currentName);
            if (currentCount > MaxAppears) {
            MaxAppears = currentCount;
            MostPopUser = currentName;
             }
        }
        return MostPopUser;
    }

    /** Returns the number of times that the given name appears in the follows lists of all
     *  the users in this network. Note: A name can appear 0 or 1 times in each list. */
    private int followeeCount(String name) {
        int counter = 0;
        if (name == null || name.isEmpty()) {
        return 0;
        }
        for (int i=0; i<userCount; i++){
            if (users[i].follows(name) ){
                counter++;
            }
        }
        return counter;
    }

    // Returns a textual description of all the users in this network, and who they follow.
    public String toString() {
       String ans = "Network";
       for (int i=0; i<userCount; i++){
        ans += "\n" + users[i].toString();
       }
       return ans;
    }

}
