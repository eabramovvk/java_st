package ru.stqa.st.addressbook.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.st.addressbook.model.ContactData;
import ru.stqa.st.addressbook.model.Contacts;
import ru.stqa.st.addressbook.model.Groups;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactDeletionTests extends TestBase{

    private Properties properties;

    @BeforeMethod
    public void ensurePreconditions() throws IOException {
        Groups groups = app.db().groups();
        properties = new Properties();
        properties.load(new FileReader(new File(String.format("src/test/resources/local.properties"))));
        if (app.db().contacts().size() == 0){
            app.goTo().goToContactCreation();
            app.contact().create(new ContactData().withFirstName(properties.getProperty("web.firstName"))
                    .withLastName(properties.getProperty("web.lastName")).withAddress(properties.getProperty("web.address"))
                    .withHomePhone(properties.getProperty("web.homephone")).withEmail(properties.getProperty("web.email"))
                    .inGroup(groups.iterator().next()), true);
            app.contact().goToHomePage();
        }

    }

    @Test
    public void testContactDeletion(){
        Contacts before = app.db().contacts();
        ContactData deletedContact = before.iterator().next();
        app.contact().delete(deletedContact);
        assertThat(app.contact().count(), equalTo(before.size()-1));
        Contacts after = app.db().contacts();
        assertThat(after,equalTo(before.without(deletedContact)));
        verifyContactListInUI();
    }
}
