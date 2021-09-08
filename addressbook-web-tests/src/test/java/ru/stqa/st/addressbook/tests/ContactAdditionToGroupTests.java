package ru.stqa.st.addressbook.tests;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import ru.stqa.st.addressbook.model.ContactData;
import ru.stqa.st.addressbook.model.Contacts;
import ru.stqa.st.addressbook.model.GroupData;
import ru.stqa.st.addressbook.model.Groups;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;


public class ContactAdditionToGroupTests extends TestBase {

    ContactData helpContact = new ContactData()
            .withLastName("test5").withFirstName("test5").withEmail("test5@mail.com");
    GroupData helpGroup = new GroupData().withName("test5");

    @BeforeClass
    public void ensurePreconditions() {
        if (app.db().groups().size() == 0) {
            app.goTo().groupPage();
            app.group().create(helpGroup);
        }
        Groups groups = app.db().groups();

        if (app.db().contacts().size() == 0) {
            app.contact().gotoCreateContactPage();
            app.contact().create(helpContact.inGroup(groups.iterator().next()), true);

        }

    }

    @Test
    public void testContactAddToGroup() {
        Contacts usersAll = app.db().contacts();
        Groups groupsAll = app.db().groups();

        ContactData userAfter = null;
        ContactData userSelect = null;
        GroupData groupSelect = null;


        for (ContactData currentUser : usersAll) {
            Groups groupsOfSelectedUser = currentUser.getGroups();
            if (groupsOfSelectedUser.size() != groupsAll.size()) {
                groupsAll.removeAll(groupsOfSelectedUser);
                groupSelect = groupsAll.iterator().next();
                userSelect = currentUser;
                break;
            }
        }
        if (groupSelect == null) {
            ContactData user = helpContact;
            app.contact().gotoCreateContactPage();
            app.contact().create(user, true);
            app.contact().goToHomePage();
            Contacts userA = app.db().contacts();
            user.withId(userA.stream().mapToInt((g) -> (g).getId()).max().getAsInt());
            userSelect = user;
            groupSelect = groupsAll.iterator().next();
        }
        app.contact().goToHomePage();
        app.contact().allGroupsOnUserPage();
        app.contact().selectedGroup(userSelect, groupSelect);
        app.contact().goToHomePage();
        Contacts usersAllAfter = app.db().contacts();
        for (ContactData currentUserAfter : usersAllAfter) {
            if (currentUserAfter.getId() == userSelect.getId()) {
                userAfter = currentUserAfter;
            }
        }

        assertThat(userSelect.getGroups(), equalTo(userAfter.getGroups().without(groupSelect)));
    }

}