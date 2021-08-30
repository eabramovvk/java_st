package ru.stqa.st.addressbook.tests;


import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.st.addressbook.model.ContactData;
import ru.stqa.st.addressbook.model.Contacts;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactModificationTests extends TestBase {

    @BeforeMethod
    public void ensurePreconditions(){
        if (app.contact().all().size() == 0){
            app.goTo().goToContactCreation();
            app.contact().create(new ContactData().withFirstName("TestName").withLastName("TestLastName").withAddress("TestAddress").withHomePhone("+79217777777").withEmail("testmail@test.test").withGroup("[none]"), true);
            app.contact().goToHomePage();
        }
    }

    @Test
    public void testContactModification(){

        Contacts before = app.contact().all();
        ContactData modifiedContact =  before.iterator().next();
        ContactData contact = new ContactData().withId(modifiedContact.getId()).withFirstName("TestName").withLastName("TestLastName").withAddress("TestAddress").withHomePhone("+79217777777").withEmail("testmail@test.test").withGroup("[none]");
        app.contact().modify(contact);
        assertThat(app.contact().count(), equalTo(before.size()));
        Contacts after = app.contact().all();
        assertThat(after, equalTo(before.without(modifiedContact).withAdded(contact)));
    }
}
