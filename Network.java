public class Network {
    private User[] users;
    private int userCount;

    public Network(int maxUserCount) {
        this.users = new User[maxUserCount];
        this.userCount = 0;
    }

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

    public User getUser(String name) {
        if (name == null) return null;
        for (int i = 0; i < userCount; i++) {
            // תיקון קריטי: equals רגיל כדי שחיפוש 'baz' לא ימצא את 'Baz'
            if (users[i].getName().equals(name)) {
                return users[i];
            }
        }
        return null;
    }

    public boolean addUser(String name) {
        if (name == null || userCount == users.length || getUser(name) != null) {
            return false;
        }
        users[userCount] = new User(name);
        userCount++;
        return true;
    }

    public boolean addFollowee(String name1, String name2) {
        if (name1 == null || name2 == null || name1.equals(name2)) {
            return false;
        }
        User user1 = getUser(name1);
        User user2 = getUser(name2);
        if (user1 == null || user2 == null) {
            return false;
        }
        return user1.addFollowee(name2);
    }

    public String recommendWhoToFollow(String name) {
        User user1 = getUser(name);
        if (user1 == null) return null;

        String bestName = null;
        int maxCommon = -1;

        for (int i = 0; i < userCount; i++) {
            String currentName = users[i].getName();
            if (currentName.equals(name) || user1.follows(currentName)) {
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

    private int followeeCount(String name) {
        int counter = 0;
        for (int i = 0; i < userCount; i++) {
            if (users[i].follows(name)) {
                counter++;
            }
        }
        return counter;
    }

    public String toString() {
        // הוספת הנקודתיים הנדרשות
        String ans = "Network:";
        for (int i = 0; i < userCount; i++) {
            ans += "\n" + users[i].toString();
        }
        return ans;
    }
}