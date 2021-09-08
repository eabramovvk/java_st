package ru.stqa.st.addressbook.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.st.addressbook.model.ContactData;
import ru.stqa.st.addressbook.model.Contacts;
import ru.stqa.st.addressbook.model.GroupData;
import ru.stqa.st.addressbook.model.Groups;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class ContactDeletionFromGroupTests extends TestBase {
    ContactData helpContact = new ContactData()
            .withLastName("test5").withFirstName("test5").withEmail("test5@mail.com");
    GroupData helpGroup = new GroupData().withName("test5");

    @BeforeMethod
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
    public void testContactDeleteFromGroup() {
        ContactData userAfter = null;
        ContactData userSelect;
        GroupData groupSelect = null;
        Contacts contacts = app.db().contacts();
        Groups groups = app.db().groups();
        app.contact().goToHomePage();
        userSelect = contacts.iterator().next();

        for (ContactData currentUser : contacts) {
            Groups currentGroup = currentUser.getGroups();
            if (currentGroup.size() > 0) {
                userSelect = currentUser;
                groupSelect = currentUser.getGroups().iterator().next();
                break;
            }
        }

        if (userSelect.getGroups().size() == 0) {
            groupSelect = groups.iterator().next();
            app.contact().selectedGroup(userSelect, groupSelect);
        }
        app.contact().goToHomePage();
        app.contact().selectGroupFromFilterForDeletion(groupSelect);
        app.contact().selectContactById(userSelect.getId());
        app.contact().clickRemoveContactFromGroup();
        app.contact().goToHomePage();

        Contacts usersAllAfter = app.db().contacts();
        for (ContactData userChoiceAfter : usersAllAfter) {
            if (userChoiceAfter.getId() == userSelect.getId()) {
                userAfter = userChoiceAfter;
            }
            assertThat(userSelect.getGroups(), equalTo(userAfter.getGroups().without(groupSelect)));
        }
    }
}
