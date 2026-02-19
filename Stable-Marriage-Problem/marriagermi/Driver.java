import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.util.ArrayList;
import java.util.List;

public class Driver {
    public static int counter = 0;
    public static final int N = 7;
    
    public static synchronized void trace(String s){
        System.out.println(s);
    }

    public static synchronized void inc(){
        counter++;
    }

    public static void main(String[] args) throws Exception, RemoteException{
        LocateRegistry.createRegistry(1099);
        trace("RMI REGISTRY STARTED ON 1099!");
        
        Woman[] women = new Woman[N];
        for (int i=0; i<N; i++){
            women[i] = new Woman(i);
            Naming.rebind("rmi://localhost/Woman" + i, women[i]);
        }
        
        Man[] men = new Man[N];
        for (int i=0; i<N; i++){
            men[i] = new Man(i);
            Naming.rebind("rmi://localhost/Man" + i, men[i]);
        }

        List<Thread> threads = new ArrayList<>();
        for (int i=0; i<N; i++){
            final int id = i;
            try {
                Thread t = new Thread(() -> {
                    try {
                        men[id].proposeNext();
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                }, "ManThread-" + id);
                threads.add(t);
                t.start();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        while(true){
            if(counter == N){
                trace("All engagements are done!");
                break;
            }
            Thread.sleep(50);
        }

        trace("=== FINAL MATCHING ===");
        for (int i = 0; i < N; i++) {
            trace("M" + i + " - W" + men[i].getPartnerId());
        }

        // optional: stop threads (they'll be sleeping)
        System.exit(0);
    }
}

