package Multithread;
import java.util.Random;

class Customer extends Thread {
    private Flight flight;
    public Customer(Flight flight) {
        this.flight = flight;
    }
    @Override
    public void run()  {
        try {
            if (flight.bookSeat()) {
                Thread.sleep(new Random().nextInt(500));        
                if (new Random().nextBoolean()) {
                    flight.cancelSeat();
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
