class Buffer {
    private final int size; // the buffer bound
    private final int[] store;
    Semaphore spaces;
    Semaphore elements;
    private int inptr = 0;
    private int outptr = 0;

    public Buffer(int size) {
        this.size = size;
        this.store = new int[size];
        this.spaces = new Semaphore(size);
        this.elements = new Semaphore(0);
    }

    public void produce(int value) {
        spaces.WAIT();
        store[inptr] = value;
        inptr = (inptr + 1) % size;
        elements.NOTIFY();
    }

    public int consume() {
        int value;
        elements.WAIT();
        value = store[outptr];
        outptr = (outptr + 1) % size;
        spaces.NOTIFY();
        return value;
    }
}