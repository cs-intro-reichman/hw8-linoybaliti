/** Represents a user in a social network. A user is characterized by a name,
 * a list of user names that s/he follows, and the list's size. */
 public class User {

    // Maximum number of users that a user can follow
    static int maxfCount = 10;

    private String name;       // name of this user
    private String[] follows;  // array of user names that this user follows
    private int fCount;        // actual number of followees (must be <= maxfCount)

    /** Creates a user with an empty list of followees. */
    public User(String name) {
        this.name = name;
        follows = new String[maxfCount]; 
        fCount = 0;                      
    }

    /** Creates a user with some followees. */
    public User(String name, boolean gettingStarted) {
        this(name);
        follows[0] = "Foo";
        follows[1] = "Bar";
        follows[2] = "Baz";
        fCount = 3;
    }

    public String getName() {
        return name;
    }

    public String[] getfFollows() {
        return follows;
    }

    public int getfCount() {
        return fCount;
    }

    /** If this user follows the given name, returns true; otherwise returns false. */
    public boolean follows(String name) {
        if (name == null) return false; // הגנה מפני קריסה
        for (int i = 0; i < fCount; i++) {
            // שימוש ב-equalsIgnoreCase כדי להיות בטוחים בטסטים
            if (follows[i] != null && follows[i].equalsIgnoreCase(name)) {
                return true;
            }
        }
        return false;
    }

    /** Makes this user follow the given name. */
    public boolean addFollowee(String name) {
        // תיקון: בדיקה אם המשתמש מנסה לעקוב אחרי עצמו או שהרשימה מלאה או שהוא כבר עוקב
        if (name == null || name.equalsIgnoreCase(this.name) || fCount == maxfCount || follows(name)) {
            return false;
        }
        follows[fCount] = name;
        fCount++;
        return true;
    }
    
    /** Removes the given name from the follows list. */
    public boolean removeFollowee(String name) {
        if (name == null) return false;
        for (int i = 0; i < fCount; i++) {
            if (follows[i].equalsIgnoreCase(name)) {
                // הזזת האיברים שמאלה
                for (int j = i; j < fCount - 1; j++) {
                    follows[j] = follows[j + 1];
                }
                follows[fCount - 1] = null;
                fCount--;
                return true;
            }
        }
        return false;
    }

    /** Counts mutual followees. */
    public int countMutual(User other) {
        if (other == null) return 0;
        int counter = 0;
        for (int i = 0; i < this.fCount; i++) {
            if (other.follows(this.follows[i])) {
                counter++;
            }
        }
        return counter;
    }

    /** Checks if two users are friends. */
    public boolean isFriendOf(User other) {
        if (other == null) return false;
        return this.follows(other.getName()) && other.follows(this.name);
    }

    /** Returns this user's name, and the names that s/he follows. */
    public String toString() {
        String ans = name + " -> ";
        for (int i = 0; i < fCount; i++) {
            ans = ans + follows[i] + " ";
        }
        return ans;
    }
}