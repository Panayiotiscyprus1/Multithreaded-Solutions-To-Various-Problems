public class Person extends Thread{

    private int id;
    private Table t;

    public Person(int id, Table t){
        this.id = id;
        this.t = t;
    }

    @Override
    public void run() {
        try {
            while (true) {
                int target = t.Feed(id);
                t.Feeding(id, target);
                t.Done(target);
            }
        } catch (InterruptedException e) {
            // thread interrupted -> end
        }
    }

}