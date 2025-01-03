package Multithread;


public class multi {
    public static void main(String[] args) {
        Flight flight = new Flight(20);
        Customer[] customers= new Customer[25];
        for (int i = 0; i < 25; i++) {
            customers[i] = new Customer(flight);
            customers[i].setName("乘客 " + i);
            customers[i].start();
        }
        for (int i = 0; i < 25; i++) {
                try {
                    customers[i].join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }        // 等待每个客户线程执行完毕
            
        }
        System.out.println("预定完毕");
    }
}

