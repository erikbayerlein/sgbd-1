import models.Directory;

public class Main {
    public static void main(String[] args) {

        // ------------------------- Adicionar lógica de ler in.txt -------------------------

        //----------------------------------------------------------------------------------

        Directory directory = new Directory(2);

        directory.search(2014);

        directory.insert(2014);
        directory.insert(10);
        directory.insert(46);
        directory.insert(34);
//        directory.insert(2000);
//        directory.insert(1987);
//        directory.insert(2024);
//        directory.insert(1994);
//        directory.insert(1999);

        System.out.println("Hello world!");
    }
}