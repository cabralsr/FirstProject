import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

class ReadArch {

    public void Welcome() {
        try {
            File myTxt = new File("src\\txt\\welcome.txt");
            Scanner myReader = new Scanner(myTxt);

            while (myReader.hasNextLine()) {
                String welcome = myReader.nextLine();
                System.out.println(welcome);
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("Aconteceu um erro.");
            e.printStackTrace();
        }
    }

    public void PainelLogin() {
        try {
            File myTxt = new File("src\\txt\\PainelLogin.txt");
            Scanner myReader = new Scanner(myTxt);

            while (myReader.hasNextLine()) {
                String painellogin = myReader.nextLine();
                System.out.println(painellogin);
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("Aconteceu um erro.");
            e.printStackTrace();
        }
    }
}