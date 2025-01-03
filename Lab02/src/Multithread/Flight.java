package Multithread;

class Flight {
    private int seats;
    public Flight(int seats) {
        this.seats = seats;
    }
    
    public synchronized boolean bookSeat() throws InterruptedException {
        while (seats <= 0) {
            System.out.println(Thread.currentThread().getName() + " 等待空闲");
            wait();
        }
        seats--;
        System.out.println(Thread.currentThread().getName() + " 预定成功，余票：" + seats);
        return true;
    }

    public synchronized void cancelSeat() {
        seats++;
        System.out.println(Thread.currentThread().getName() + " 取消成功，余票: " + seats);
        notifyAll();
    }
}

