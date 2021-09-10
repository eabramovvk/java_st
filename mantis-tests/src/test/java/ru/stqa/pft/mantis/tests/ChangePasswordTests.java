package ru.stqa.pft.mantis.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.annotations.AfterMethod;
import ru.lanwen.verbalregex.VerbalExpression;
import ru.stqa.pft.mantis.model.MailMessage;
import ru.stqa.pft.mantis.model.UserData;
import ru.stqa.pft.mantis.appmanager.HttpSession;
import javax.mail.MessagingException;
import java.io.IOException;
import java.util.List;

import static org.testng.AssertJUnit.assertTrue;

public class ChangePasswordTests extends TestBase {

    @BeforeMethod
    public void startMailServer() {
        app.mail().start();
    }

    @Test
    public void testChangePassword() throws IOException, MessagingException {
        String newPassword = "newpassword";
        app.getNavigationHelper().loginUI();
        app.getNavigationHelper().manageUsersPage();
        UserData changedUser = app.getUserHelper().getAnyUserFromBD();
        app.getNavigationHelper().goToUserPage(changedUser.getId());
        app.getNavigationHelper().resetPassword(changedUser.getId());
        List<MailMessage> mailMessages = app.mail().waitForMail(1, 10000);
        String confirmationLink = findConfirmationLink(mailMessages, changedUser.getEmail());
        app.registration().finish(confirmationLink, newPassword);
        UserData user = app.getUserHelper().getUserByIdFromBD(changedUser.getId());
        HttpSession sessionUser = app.newSession();
        assertTrue(sessionUser.login(user.getUsername()));
        assertTrue(sessionUser.isLoggedInAs(user.getUsername()));
    }

    private String findConfirmationLink(List<MailMessage> mailMessages, String email) {
        MailMessage mailMessage = mailMessages.stream().filter((m) -> m.to.equals(email)).findFirst().get();
        VerbalExpression regex = VerbalExpression.regex().find("http://").nonSpace().oneOrMore().build();
        return regex.getText(mailMessage.text);
    }

    @AfterMethod(alwaysRun = true)
    public void stopMailServer() {
        app.mail().stop();
    }
}