public class Main {
    public static void main(String[] args) {
        Person s1 = new Student("Шевченко", "Тарас", 19, "ІП-21", "STU-1001");
        Person s2 = new Student("Коваль", "Ольга", 20, "ІП-21", "STU-1002");
        Person l1 = new Lecturer("Петров", "Іван", 45, "Математики", 12000.50);
        Person l2 = new Lecturer("Гринь", "Марія", 38, "Інформатики", 13500.0);
        Person p1 = new Person("Мельник", "Олена", 30);

        Person[] people = new Person[] { s1, s2, l1, l2, p1 };

        for (Person person : people) {
            System.out.println(person.toString());
        }
    }
}
