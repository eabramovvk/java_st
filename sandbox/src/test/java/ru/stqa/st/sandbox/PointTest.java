package ru.stqa.st.sandbox;

import org.testng.Assert;
import org.testng.annotations.Test;


public class PointTest {

    @Test

    public void TestArea() {
        Point p1 = new Point(4,9);
        Point p2 = new Point(7,3);
        Assert.assertEquals(p1.distance(p2),6.708203932499369);

    }
}
