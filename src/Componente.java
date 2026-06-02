import java.util.HashMap;
import java.util.Map;

abstract class Componente {
    String nome;
    public Componente(String nome) { this.nome = nome; }
}

class Arquivo extends Componente {
    String conteudo;
    public Arquivo(String nome, String conteudo) {
        super(nome);
        this.conteudo = conteudo;
    }
}

class Diretorio extends Componente {
    Map<String, Componente> filhos = new HashMap<>();
    public Diretorio(String nome) { super(nome); }
}
