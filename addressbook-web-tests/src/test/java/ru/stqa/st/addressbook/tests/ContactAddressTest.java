package ru.stqa.st.addressbook.tests;

import org.testng.annotations.Test;
import ru.stqa.st.addressbook.model.ContactData;

import java.util.Arrays;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactAddressTests extends TestBase {

    @Test
    public void testContactAddress() {
        app.contact().goToHomePage();
        ContactData contact = app.contact().all().iterator().next();
        ContactData contactInfoFromEditForm = app.contact().infoFromEditForm(contact);
        assertThat(contact.getAllAddresses(), equalTo(mergeAddresses(contactInfoFromEditForm)));
    }
    private String mergeAddresses(ContactData contact) {
        return Arrays.asList(contact.getAddress())
                .stream().filter((s)-> !s.equals(""))
                .collect(Collectors.joining("\n"));
    }

}