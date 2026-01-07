/** Represents a social network. The network has users, who follow other users.
 * Each user is an instance of the User class. */
public class Network {

    // Fields
    private User[] users;  // the users in this network (an array of User objects)
    private int userCount; // actual number of users in this network

    /** Creates a network with a given maximum number of users. */
    public Network(int maxUserCount) {
        this.users = new User[maxUserCount];
        this.userCount = 0;
    }

    /** Creates a network with some users. The only purpose of this constructor is 
     * to allow testing the toString and getUser methods, before implementing other methods. */
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
     * If there is no such user, returns null. */
    public User getUser(String name) {
        if (name == null) return null; // הגנה מפני null
        for (int i = 0; i < userCount; i++) {
            if (users[i].getName().equalsIgnoreCase(name)) { // שימוש ב-equalsIgnoreCase עדיף
                return users[i];
            }
        }
        return null;
    }

    /** Adds a new user with the given name to this network. */
    public boolean addUser(String name) {
        if (name == null || userCount == users.length || getUser(name) != null) {
            return false;
        }
        users[userCount] = new User(name);
        userCount++;
        return true;
    }

    /** Makes the user with name1 follow the user with name2. */
    public boolean addFollowee(String name1, String name2) {
        // תיקון חשוב: בדיקה שמשתמש לא עוקב אחרי עצמו
        if (name1 == null || name2 == null || name1.equalsIgnoreCase(name2)) {
            return false;
        }
        
        User user1 = getUser(name1);
        User user2 = getUser(name2);
        
        if (user1 == null || user2 == null) {
            return false;
        }
        
        return user1.addFollowee(name2);
    }
    
    /** recommends another user to follow. */
    public String recommendWhoToFollow(String name) {
        User user1 = getUser(name);
        if (user1 == null) return null;

        String bestName = null;
        int maxCommon = -1;

        for (int i = 0; i < userCount; i++) {
            String currentName = users[i].getName();
            // לא ממליצים על עצמו ולא על מישהו שהוא כבר עוקב אחריו
            if (currentName.equalsIgnoreCase(name) || user1.follows(currentName)) {
                continue;
            }
            
            int common = MutualFollows(name, currentName);
            if (common > maxCommon) {
                maxCommon = common;
                bestName = currentName;
            }
        }
        return bestName;
    }

    /** Computes mutual follows. */
    public int MutualFollows(String name1, String name2) {
        User u1 = getUser(name1);
        User u2 = getUser(name2);
        if (u1 == null || u2 == null) return 0;

        int counter = 0;
        for (int i = 0; i < userCount; i++) {
            String checkName = users[i].getName();
            if (u1.follows(checkName) && u2.follows(checkName)) {
                counter++;
            }
        }
        return counter;
    }

    /** Returns the most popular user. */
    public String mostPopularUser() {
        if (userCount == 0) return null;

        String mostPop = null;
        int maxFollowers = -1;

        for (int i = 0; i < userCount; i++) {
            int count = followeeCount(users[i].getName());
            if (count > maxFollowers) {
                maxFollowers = count;
                mostPop = users[i].getName();
            }
        }
        return mostPop;
    }

    /** Counts how many follow this user. */
    private int followeeCount(String name) {
        int counter = 0;
        for (int i = 0; i < userCount; i++) {
            if (users[i].follows(name)) {
                counter++;
            }
        }
        return counter;
    }

    /** Returns a textual description of the network. */
    public String toString() {
       // תיקון קריטי: הוספת הנקודתיים אחרי המילה Network
       String ans = "Network:"; 
       for (int i = 0; i < userCount; i++) {
           ans += "\n" + users[i].toString();
       }
       return ans;
    }
}