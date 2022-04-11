package uj.java.kindergarten;

public final class ChildImpl extends Child implements Runnable {

    private Object leftFork;
    private Object rightFork;
    private ChildImpl leftCChild;
    private ChildImpl rightChild;

    public ChildImpl(String name, int hungerSpeedMs) {
        super(name, hungerSpeedMs);
    }

    public void meetNeighbors(ChildImpl leftCChild, ChildImpl rightChild){
        this.leftCChild = leftCChild;
        this.rightChild = rightChild;
    }

    public void pickForks(Object leftFork, Object rightFork){
        this.leftFork = leftFork;
        this.rightFork = rightFork;
    }

    public void changeForks(){
        var tempFork = leftFork;
        leftFork = rightFork;
        rightFork = tempFork;
    }

    @SuppressWarnings({"SynchronizeOnNonFinalField", "InfiniteLoopStatement"})
    @Override
    public void run() {
        while (true) {
            if (happiness() < leftCChild.happiness() && happiness() < rightChild.happiness())
                synchronized (leftFork) {
                    synchronized (rightFork) {
                        eat();
                    }
                }
            }
    }
}
