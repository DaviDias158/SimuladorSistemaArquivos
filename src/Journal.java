import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.FileOutputStream;
import java.io.IOException;

public class Journal {
    private static final String LOG_FILE = "journal.txt";

    public static void gravar(String operacao) {
        try (FileWriter fw = new FileWriter(LOG_FILE, true);
             PrintWriter pw = new PrintWriter(fw)) {
            pw.println(operacao);
        } catch (IOException e) {
            System.out.println("Erro ao gravar no Journal: " + e.getMessage());
        }
    }

    public static void limpar() {
        try {
            new FileOutputStream(LOG_FILE).close();
        } catch (IOException e) {
            System.out.println("Erro ao limpar Journal.");
        }
    }
}
