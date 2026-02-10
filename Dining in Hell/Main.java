public class Main{
    public static void main(String[] args) {
        Table t = new Table();

        Person[] people = new Person[6];
        for(int i = 0; i < 6; i++){
            people[i] = new Person(i, t);
            people[i].start();
        }
    }
}