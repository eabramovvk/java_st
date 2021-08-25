package ru.stqa.st.addressbook.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.st.addressbook.model.ContactData;
import org.testng.Assert;
import java.util.List;
import java.util.Set;

public class ContactDeletionTests extends TestBase{

    @BeforeMethod
    public void ensurePreconditions(){
        if (app.contact().all().size() == 0){
            app.goTo().goToContactCreation();
            app.contact().create(new ContactData().withFirstName("TestName").withLastName("TestLastName").withAddress("TestAddress").withHomePhone("+79217777777").withEmail("testmail@test.test").withGroup("[none]"), true);
            app.contact().goToHomePage();
        }

    }

    @Test
    public void testContactDeletion(){

        Set<ContactData> before = app.contact().all();
        ContactData deletedContact = before.iterator().next();
        app.contact().delete(deletedContact);
        Set<ContactData> after = app.contact().all();
        Assert.assertEquals(after.size(), before.size() - 1);
        before.remove(deletedContact);
        Assert.assertEquals(before, after);

    }
}
