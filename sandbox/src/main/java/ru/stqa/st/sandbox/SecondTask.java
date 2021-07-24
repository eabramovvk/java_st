package ru.stqa.st.sandbox;

public class SecondTask {
    public static void main(String[] args) {
        Point p1 = new Point(4,9);
        Point p2 = new Point(7,3);

        System.out.println("Расстояние между двумя точками = " + p1.distance(p2));
    }
}
