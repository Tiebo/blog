import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        while (sc.hasNext()) {
            String name = sc.next();
            double price = sc.nextDouble();
            books books = new books(name, price);
            System.out.println(books);
        }
        System.out.println("图书总册数为:" + books.id);
    }


    static class books {
        String name;
        int Tid;
        static int id = 0;
        double price;
        static int number = 0;

        public books(String name, double price) {
            this.name = name;
            this.price = price;
            books.id ++;
            books.number ++ ;
            this.Tid = books.id;
        }

        @Override
        public String toString() {
            return "书名:" + this.name +
                    ",书号:"+ this.Tid +
                    ",书价:" + this.price;
        }
    }
}
