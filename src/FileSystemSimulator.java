public class FileSystemSimulator {
    private static final Diretorio raiz = new Diretorio("/");
    private static Diretorio diretorioAtual = raiz;

    public static String getCaminhoAtual() {
        return diretorioAtual == raiz ? "/" : "/" + diretorioAtual.nome;
    }

    public static void criarDiretorio(String nome) {
        Journal.gravar("START: mkdir " + nome);
        if (diretorioAtual.filhos.containsKey(nome)) {
            System.out.println("Erro: O diretório ou arquivo já existe.");
            return;
        }
        diretorioAtual.filhos.put(nome, new Diretorio(nome));
        Journal.gravar("COMMIT: mkdir " + nome);
        System.out.println("Diretório '" + nome + "' criado com sucesso.");
    }

    public static void apagarDiretorio(String nome) {
        Journal.gravar("START: rmdir " + nome);
        if (diretorioAtual.filhos.containsKey(nome) && diretorioAtual.filhos.get(nome) instanceof Diretorio) {
            diretorioAtual.filhos.remove(nome);
            Journal.gravar("COMMIT: rmdir " + nome);
            System.out.println("Diretório '" + nome + "' apagado.");
        } else {
            System.out.println("Erro: Diretório não encontrado.");
        }
    }

    public static void criarArquivo(String nome, String conteudo) {
        Journal.gravar("START: touch " + nome);
        diretorioAtual.filhos.put(nome, new Arquivo(nome, conteudo));
        Journal.gravar("COMMIT: touch " + nome);
        System.out.println("Arquivo '" + nome + "' criado/atualizado.");
    }

    public static void apagarArquivo(String nome) {
        Journal.gravar("START: rm " + nome);
        if (diretorioAtual.filhos.containsKey(nome) && diretorioAtual.filhos.get(nome) instanceof Arquivo) {
            diretorioAtual.filhos.remove(nome);
            Journal.gravar("COMMIT: rm " + nome);
            System.out.println("Arquivo '" + nome + "' apagado.");
        } else {
            System.out.println("Erro: Arquivo não encontrado.");
        }
    }

    public static void renomear(String antigo, String novo) {
        Journal.gravar("START: rename " + antigo + " para " + novo);
        if (diretorioAtual.filhos.containsKey(antigo)) {
            Componente c = diretorioAtual.filhos.remove(antigo);
            c.nome = novo;
            diretorioAtual.filhos.put(novo, c);
            Journal.gravar("COMMIT: rename " + antigo + " para " + novo);
            System.out.println("Modificado de '" + antigo + "' para '" + novo + "'.");
        } else {
            System.out.println("Erro: Elemento não encontrado.");
        }
    }

    public static void copiarArquivo(String origem, String destino) {
        Journal.gravar("START: cp " + origem + " para " + destino);
        if (diretorioAtual.filhos.containsKey(origem) && diretorioAtual.filhos.get(origem) instanceof Arquivo) {
            Arquivo original = (Arquivo) diretorioAtual.filhos.get(origem);
            Arquivo copia = new Arquivo(destino, original.conteudo);
            diretorioAtual.filhos.put(destino, copia);
            Journal.gravar("COMMIT: cp " + origem + " para " + destino);
            System.out.println("Arquivo copiado para '" + destino + "'.");
        } else {
            System.out.println("Erro: Arquivo de origem não encontrado.");
        }
    }

    public static void listarDiretorio() {
        if (diretorioAtual.filhos.isEmpty()) {
            System.out.println("(Diretório vazio)");
            return;
        }
        for (Componente c : diretorioAtual.filhos.values()) {
            if (c instanceof Diretorio) {
                System.out.println("[DIR]  " + c.nome);
            } else if (c instanceof Arquivo) {
                System.out.println("[ARQ]  " + c.nome + "  - Conteúdo: (" + ((Arquivo) c).conteudo + ")");
            }
        }
    }
}
