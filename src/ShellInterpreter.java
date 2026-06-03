public class ShellInterpreter {
    public static void interpretar(String comando, String[] partes) {
        try {
            switch (comando) {
                case "criarpasta":
                    if (partes.length < 2) { System.out.println("Uso: criarpasta [nome]"); break; }
                    FileSystemSimulator.criarDiretorio(partes[1]);
                    break;

                case "apagarpasta":
                    if (partes.length < 2) { System.out.println("Uso: apagarpasta [nome]"); break; }
                    FileSystemSimulator.apagarDiretorio(partes[1]);
                    break;

                case "criararquivo":
                    if (partes.length < 2) { System.out.println("Uso: criararquivo [nome] [conteudo_opcional]"); break; }
                    String conteudo = partes.length == 3 ? partes[2] : "";
                    FileSystemSimulator.criarArquivo(partes[1], conteudo);
                    break;

                case "apagararquivo":
                    if (partes.length < 2) { System.out.println("Uso: apagararquivo [nome]"); break; }
                    FileSystemSimulator.apagarArquivo(partes[1]);
                    break;

                case "listar":
                    FileSystemSimulator.listarDiretorio();
                    break;

                case "renomear":
                    if (partes.length < 3) { System.out.println("Uso: renomear [antigo] [novo]"); break; }
                    FileSystemSimulator.renomear(partes[1], partes[2]);
                    break;

                case "copiar":
                    if (partes.length < 3) { System.out.println("Uso: copiar [origem] [destino]"); break; }
                    FileSystemSimulator.copiarArquivo(partes[1], partes[2]);
                    break;

                default:
                    System.out.println("Comando não reconhecido.");
            }
        } catch (Exception e) {
            System.out.println("Erro ao interpretar comando: " + e.getMessage());
        }
    }
}
