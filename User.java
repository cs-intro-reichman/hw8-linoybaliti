public class User {
    static int maxfCount = 10;
    private String name;
    private String[] follows;
    private int fCount;

    public User(String name) {
        this.name = name;
        this.follows = new String[maxfCount];
        this.fCount = 0;
    }

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

    public boolean follows(String name) {
        if (name == null) return false;
        for (int i = 0; i < fCount; i++) {
            // שימוש ב-equals רגיל (רגיש לאותיות גדולות/קטנות)
            if (follows[i] != null && follows[i].equals(name)) {
                return true;
            }
        }
        return false;
    }

    public boolean addFollowee(String name) {
        // הגנה: לא עוקב אחרי עצמו, רשימה לא מלאה, ולא עוקב פעמיים
        if (name == null || name.equals(this.name) || fCount == maxfCount || follows(name)) {
            return false;
        }
        follows[fCount] = name;
        fCount++;
        return true;
    }

    public boolean removeFollowee(String name) {
        if (name == null) return false;
        for (int i = 0; i < fCount; i++) {
            if (follows[i].equals(name)) {
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

    public boolean isFriendOf(User other) {
        if (other == null) return false;
        return this.follows(other.getName()) && other.follows(this.name);
    }

    public String toString() {
        String ans = name + " -> ";
        for (int i = 0; i < fCount; i++) {
            ans = ans + follows[i] + " ";
        }
        return ans;
    }
}