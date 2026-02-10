class Table{
    private boolean[] hungry = new boolean[5];
    private boolean[] beingFed = new boolean[5];

    public synchronized requestToFeed(int personID){
        if(beingFed[personID] == true){
            wait();
        }
        if(hungry[personID] == false){
            wait();
        }else{
            beingFed[personID] == true;
        }

    }

    public synchronized getPerson(){
        // get someone from the queue of ppl waitin to eat
        return personID
    }
}