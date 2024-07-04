import java.util.Scanner;

public class App {
    public static void main(String[] args) throws Exception {

        String CurrentUser = "";
        boolean loggedIn = false;
        boolean exit = false;

        Scanner sc = new Scanner(System.in, "Cp850");

        ReadArch RWelcome = new ReadArch();
        ReadArch RPainelLogin = new ReadArch();

        while (!exit) {
            if (!loggedIn){
                RWelcome.Welcome();
                System.out.printf("|    ===> ");
                String Option = sc.nextLine();

                switch (Option) {
                    case "1":
                        System.out.println();
                        System.out.printf("|    CPF: ");
                        String input_cpf = sc.nextLine();
                        System.out.printf("|    Password: ");
                        String input_senha = sc.nextLine();

                        Authentic auth = new Authentic(input_cpf, input_senha);

                        if (auth.autenticar() == false) {
                            System.out.println();
                            System.out.println("|   Aperte enter para continuar");
                            sc.nextLine();
                            continue;
                        } else {
                            loggedIn = true;
                            CurrentUser = auth.getCpf();
                            System.out.println();
                            System.out.println("|   Aperte enter para continuar");
                            sc.nextLine();
                        }
                        break;
                    case "2":
                        User newuser = new User();
                        if (newuser.cadastrar(sc) == false) {
                            continue;
                        } else {
                            loggedIn = true;
                            CurrentUser = newuser.getCpf();
                        }
                        break;
                    case "3":
                        System.out.println(System.lineSeparator().repeat(50));
                        exit = true;
                        System.out.println("|   Finalzy Programer");
                        System.out.println();
                        break;
                    default:
                        System.out.println(">> OPÇÃO DIGITADA INVÁLIDA.");

                }
            }  else if (loggedIn) {
                RWelcome.Welcome();
                System.out.printf("|    ===> ");
                String Option = sc.nextLine();

                switch (Option) {
                    case "1":
 
                        break;
                    case "2":

                        break;
                    case "3":
                       
                    case "4":
    
                        break;
                    case "5":

                        break;
                    default:
                        System.out.println(">> OPÇÃO DIGITADA INVÁLIDA.");
                }
            }
        }
    }
}
