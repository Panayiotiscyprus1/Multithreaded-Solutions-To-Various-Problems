class Table{
    private boolean[] hungry = new boolean[5];
    private boolean[] beingFed = new boolean[5];

    public synchronized requestToFeed(int personID){
        if(beingFed[personID] == true){
            wait();
        }
        
    }
}