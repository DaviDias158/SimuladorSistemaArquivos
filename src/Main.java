import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("==============================================");
        System.out.println("   SIMULADOR DE SISTEMA DE ARQUIVOS (SHELL)   ");
        System.out.println("==============================================");
        System.out.println("Comandos: criarpasta, apagarpasta, criararquivo, apagararquivo, listar, renomear, copiar, sair\n");

        while (true) {
            System.out.print("SistemasOperacionais@Simulador:" + FileSystemSimulator.getCaminhoAtual() + "$ ");
            String linha = scanner.nextLine().trim();
            if (linha.isEmpty()) continue;

            String[] partes = linha.split(" ", 3);
            String comando = partes[0];

            if (comando.equals("sair")) {
                System.out.println("Fechando o simulador...");
                break;
            }

            ShellInterpreter.interpretar(comando, partes);
        }
        scanner.close();
    }
}
