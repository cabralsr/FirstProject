import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class User {
    private String nome, cpf, email, senha;

    public String getNome() {
        return nome;
    }

    public boolean setNome(String nome) {
        if (nome.matches(
                "^(?=.{1,40}$)[a-zA-Z]+(?:[-'\\s][a-zA-Z]+)*$")) {
            String nomeCapitalized = Arrays.stream(nome.split("\\s"))
                    .map(palavra -> Character.toTitleCase(palavra.charAt(0)) + palavra.substring(1))
                    .collect(Collectors.joining(" "));
            this.nome = nomeCapitalized;
            return true;
        } else {
            return false;
        }
    }

    public String getCpf() {
        return cpf;
    }

    public boolean setCpf(String cpf) {
        if (cpf.matches(
                "([0-9]{2}[\\.]?[0-9]{3}[\\.]?[0-9]{3}[\\/]?[0-9]{4}[-]?[0-9]{2})|([0-9]{3}[\\.]?[0-9]{3}[\\.]?[0-9]{3}[-]?[0-9]{2})")) {
            this.cpf = cpf;
            return true;
        } else {
            return false;
        }
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public boolean setSenha(String senha) {
        if (senha.isBlank()) {
            return false;
        }
        final String regex = "\\|";
        final Pattern pattern = Pattern.compile(regex, Pattern.MULTILINE);
        final Matcher matcher = pattern.matcher(senha);
        if (matcher.find()) {
            return false;
        } else {
            this.senha = senha;
            return true;
        }
    }

    public User() {

    }

    private boolean RegisterConfirm() {
        try {
            File dir = new File("data");
            dir.mkdirs();

            File usersfile = new File(dir, "users.data");

            usersfile.createNewFile();

            Scanner fsc = new Scanner(usersfile);

            while (fsc.hasNextLine()) {
                String[] userdata = fsc.nextLine().split(Pattern.quote("|"));
                if (userdata[0].equals(this.getCpf())) {
                    return true;
                }
            }

            fsc.close();
            return false;
        } catch (IOException e) {
            e.printStackTrace();
            return true;
        }
    }

    private boolean RegisterCampValidy(Scanner sc) {

        String input_password, input_confirmPassword = "";
        boolean ValidyCPF = false;
        boolean ValidyName = false;
        boolean ValidyPassword = false;
        boolean PermitPassword = true;
        char tryagain = 'S';

        while ((!ValidyCPF || !ValidyName || !ValidyPassword || !PermitPassword)
                && tryagain == 'S') {
            System.out.println("============|Insira as informações solicitadas a baixo para cadastrar|============");
            System.out.println();

            System.out.printf("|    CPF (sem pontuação): ");
            ValidyCPF = this.setCpf(sc.nextLine());

            System.out.printf("|    Name: ");
            ValidyName = this.setNome(sc.nextLine());

            System.out.printf("|    Password: ");
            input_password = sc.nextLine();

            System.out.printf("|    Confirm Password: ");
            input_confirmPassword = sc.nextLine();
            if (input_password.equals(input_confirmPassword)) {
                PermitPassword = this.setSenha(input_password);
                ValidyPassword = true;
            } else {
                PermitPassword = true;
                ValidyPassword = false;
            }

            System.out.println();

            if (!ValidyCPF) {
                System.out.println("|Erro: CPF Invalidy|");
            }

            if (!ValidyName) {
                System.out.println("|Erro: Name Invalidy|");
            }

            if (!ValidyPassword) {
                System.out.println("|Erro: Password Invalidy|");
            }

            if (!PermitPassword) {
                System.out.println("|Erro: As senhas não coincidem|");
            }

            if ((!ValidyCPF || !ValidyName || !ValidyPassword || !PermitPassword)
                    && tryagain == 'S') {
                do {
                    try {
                        System.out.println();
                        System.out.println("Deseja tentar realizar o cadastro novamente? (S/N)");
                        tryagain = sc.nextLine().toUpperCase().charAt(0);
                    } catch (Exception e) {
                        tryagain = Character.MIN_VALUE;
                    }

                } while (tryagain != 'N' && tryagain != 'S');

                if (tryagain == 'N') {
                    return false;
                } else {
                    System.out.println("\n|Tentando Novamente|\n====================================");
                }
            }
        }

        if (ValidyCPF && ValidyName && ValidyPassword && PermitPassword) {
            if (RegisterConfirm()) {
                System.out.println();
                System.out.println(
                        ">>> ERRO: ESSE USUÁRIO JÁ POSSUI CADASTRO.\nAperte enter e faça login, ou tente novamente.");
                sc.nextLine();
                return false;
            } else {
                return true;
            }

        } else {
            return false;
        }
    }

    private void UserArmarzem() {
        try {
            File dir = new File("data");
            dir.mkdirs();

            File usersfile = new File(dir, "users.data");
            usersfile.createNewFile();

            FileWriter fw = new FileWriter(usersfile, true);
            fw.write(this.getCpf() + "|" + this.getSenha() + "|" + this.getNome());
            fw.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean cadastrar(Scanner sc) {
        boolean validacao = RegisterCampValidy(sc);
        if (validacao == false) {
            System.out.println("\n|Cadastro Mal Sucedido\nVoltando para a tela inicial...");
            return false;
        } else {
            UserArmarzem();
            System.out.println("\n|Cadastro Realizado com Sucesso|\nAperte enter para continuar!");
            sc.nextLine();
            return true;
        }
    }

    public static boolean autentic(String cpf, String senha) {
        while (true) {

            try {

                File dir = new File("data");
                dir.mkdirs();

                File usersfile = new File(dir, "users.data");
                usersfile.createNewFile();

                Scanner fsc = new Scanner(usersfile);

                while (fsc.hasNextLine()) {
                    String[] userdata = fsc.nextLine().split(Pattern.quote("|"));
                    if (userdata[0].equals(cpf) && userdata[1].equals(senha)) {
                        System.out.println(System.lineSeparator().repeat(50));
                        System.out.println("|Login Realizado com Sucesso|");
                        return true;
                    }
                }

                fsc.close();
                System.out.println("\n|Login ou/e Senha Inválida|");
                return false;
            } catch (IOException e) {
                System.out.println("|Tentando Novamente|");
                e.printStackTrace();
                return false;
            }
        }
    }
}
