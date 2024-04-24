import models.Directory;

public class Main {
    public static void main(String[] args) {

        // ------------------------- Adicionar lógica de ler in.txt -------------------------

        //----------------------------------------------------------------------------------

        Directory directory = new Directory(2);

        directory.insert(2014);
        directory.insert(1995);
        directory.insert(2012);
        directory.insert(2013);
        directory.insert(1992);
        directory.insert(2020);
        directory.insert(2024);
        directory.insert(1994);
        directory.insert(1998);

        directory.search(2014);
        directory.search(1995);
        directory.search(2012);
        directory.search(2013);
        directory.search(1992);
        directory.search(2020);
        directory.search(2024);
        directory.search(1994);
        directory.search(1998);

        directory.remove(2014);
        directory.remove(1995);
        directory.remove(2012);
        directory.remove(2013);
        directory.remove(1992);
        directory.remove(2020);
        directory.remove(2024);
        directory.remove(1994);
        directory.remove(1998);

        directory.search(2014);
        directory.search(1995);
        directory.search(2012);
        directory.search(2013);
        directory.search(1992);
        directory.search(2020);
        directory.search(2024);
        directory.search(1994);
        directory.search(1998);

        System.out.println("Hello world!");
    }
}