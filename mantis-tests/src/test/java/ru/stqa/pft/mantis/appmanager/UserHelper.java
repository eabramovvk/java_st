package ru.stqa.pft.mantis.appmanager;

import ru.stqa.pft.mantis.model.UserData;
import ru.stqa.pft.mantis.model.Users;

public class UserHelper extends HelperBase {

    public UserHelper(ApplicationManager app) {
        super(app);
    }

    public UserData getAnyUserFromBD() {
        Users users = app.db().getUserFromBD();
        return users.stream().filter((u) -> u.getAccessLevel() != 90).iterator().next();
    }

    public UserData getUserByIdFromBD(int id) {
        Users users = app.db().getUserFromBD();
        return users.stream().filter((u) -> u.getId() == id).findFirst().get();
    }
}