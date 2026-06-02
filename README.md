# Simulador de Sistema de Arquivos com Journaling

**Aluno:** Davi Dias Vale  
**Link do Repositório:** [Link do GitHub](https://github.com/DaviDias158/SimuladorSistemaArquivos)  
**Disciplina:** Proj de Sistema Operacional

---

## Resumo
Este projeto consiste no desenvolvimento de um simulador de sistema de arquivos em Java. O objetivo principal é demonstrar de forma prática como um Sistema Operacional gerencia arquivos e diretórios em memória, utilizando a técnica de **Journaling** para garantir a consistência e integridade dos dados contra falhas repentinas.

---

## Metodologia
O simulador foi desenvolvido utilizando a linguagem **Java (POO)**. O sistema funciona de duas formas complementares:
1. **Chamadas de Métodos:** Execuções diretas programadas para testar funcionalidades isoladas.
2. **Modo Avançado (Shell):** Uma interface de linha de comando no terminal que aceita instruções do usuário em tempo real.

Antes de qualquer alteração na estrutura de diretórios, a operação é registrada em um arquivo de log (`journal.txt`). Caso ocorra uma queda simulada, o sistema é capaz de se recuperar na próxima inicialização lendo o Journal.

---

## Parte 1: Introdução ao Sistema de Arquivos com Journaling

### O que é um Sistema de Arquivos?
É a estrutura lógica que o Sistema Operacional utiliza para organizar, armazenar e recuperar dados em um dispositivo de armazenamento (como HD ou SSD). Sem ele, os dados seriam apenas uma sequência de bytes brutos impossível de ser interpretada pelo usuário.

### O Conceito de Journaling
O *Journaling* (ou diário de bordo) é uma técnica criada para evitar a corrupção de dados em caso de desligamentos repentinos (quedas de energia ou *crashes* do sistema). Ele funciona mantendo um registro (log) das alterações que **estão prestes a acontecer** antes que elas sejam modificadas no disco real.

### Tipos de Journaling
* **Write-Ahead Logging (WAL) / Metadata Journaling:** Apenas os metadados (nomes de arquivos, permissões, caminhos) são gravados no log antes de irem para o disco. É o mais comum devido ao equilíbrio entre desempenho e segurança.
* **Full Journaling (Data Journaling):** Tanto os metadados quanto o conteúdo real dos arquivos são gravados no log. É extremamente seguro, porém muito mais lento.
* **Log-Structured File Systems:** O próprio sistema de arquivos é tratado como um log contínuo. As novas alterações são sempre gravadas no final do log, otimizando a escrita.

---

## Parte 2: Arquitetura do Simulador

### Estrutura de Dados
O sistema foi modelado utilizando o padrão de projetos *Composite*, onde arquivos e diretórios compartilham uma base comum:

* **Classe `File`:** Representa a menor unidade de dados, contendo nome, tamanho e conteúdo.
* **Classe `Directory`:** Contém uma lista dinâmica (`List<FileComponent>`) que pode armazenar tanto arquivos quanto outros subdiretórios, gerando uma árvore hierárquica.
* **Classe `FileSystemSimulator`:** Mantém a referência para o diretório raiz (`/`) e o diretório atual (`current`).

### Funcionamento do Journaling no Simulador
A classe `Journal` manipula um arquivo físico chamado `journal.log`. O fluxo de uma operação segue estritamente o protocolo:
1. O usuário solicita: `mkdir /documentos`.
2. O simulador chama o `Journal.write("START: MKDIR | /documentos")`.
3. O simulador executa a criação do diretório na memória.
4. O simulador chama o `Journal.write("COMMIT: MKDIR | /documentos")`.

Se o programa fechar entre o passo 2 e 3, o Journal saberá que a operação ficou incompleta e a descartará ou refará na próxima inicialização.

---

## Parte 3: Implementação em Java

Abaixo está a descrição das principais classes implementadas:

* **`File.java`:** Atributos (`String nome`, `String conteudo`).
* **`Directory.java`:** Atributos (`String nome`, `Map<String, FileComponent> filhos`). Métodos para adicionar e remover componentes.
* **`Journal.java`:** Métodos `gravarOperacao(String op)`, `limparLog()` e `recuperarSistema()`.
* **`FileSystemSimulator.java`:** Concentra os métodos solicitados:
    * `criarDiretorio(String nome)`
    * `apagarDiretorio(String nome)`
    * `renomearDiretorio(String antigo, String novo)`
    * `copiarArquivo(String origem, String destino)`
    * `apagarArquivo(String nome)`
    * `renomearArquivo(String antigo, String novo)`
    * `listarDiretorio()`

---

Você tem toda razão! Da forma como eu havia colocado antes, ficou confuso e incompleto. Se o seu professor for corrigir abrindo o projeto no Eclipse, no VS Code ou direto pelo terminal, ele precisará de instruções claras para cada cenário.

Para o relatório ficar perfeito, profissional e à prova de falhas (independente de como o professor decida testar), nós devemos detalhar as duas formas principais de execução: **via Terminal** e **via IDE**.

Aqui está a versão corrigida e completa da **Parte 4** para você substituir no seu arquivo `README.md`. Ela já cobre o uso do IntelliJ, Eclipse, VS Code e Terminal, além de estar atualizada com os comandos em português!

---

## Parte 4: Instalação e Funcionamento

### Pré-requisitos
* Java JDK 17 ou superior instalado e configurado nas variáveis de ambiente.
* IDE de sua preferência (IntelliJ IDEA, Eclipse, VS Code) ou terminal do sistema.

---

### Como Executar

#### Opção 1: Execução direta via Terminal (Prompt, PowerShell ou Bash)
Se você preferir rodar o sistema sem abrir nenhuma IDE, utilize os comandos nativos do Java:

1. **Clonar o repositório:**
   ```bash
   git clone https://github.com/DaviDias158/SimuladorSistemaArquivos
   cd SimuladorSistemaArquivos/src

```

2. **Compilar todos os arquivos `.java` juntos:**
```bash
javac *.java

```


3. **Executar o programa a partir da classe principal (`Main`):**
```bash
java Main

```



#### Opção 2: Execução via IDE (IntelliJ IDEA, Eclipse ou VS Code)

Se optar por usar um ambiente de desenvolvimento:

1. Abra a sua IDE de preferência.
2. Importe ou abra a pasta raiz do projeto (`simulador-sistema-arquivos`).
3. Certifique-se de que a IDE reconheceu a pasta `src` como o diretório de códigos-fonte.
4. Abra o arquivo **`Main.java`**.
5. Clique no botão **Run** (ícone de triângulo verde / Play) ou use o atalho padrão da sua IDE para iniciar o programa.
6. O simulador será executado dentro do console/terminal integrado da própria IDE.

---

### Comandos Disponíveis no Modo Shell

Assim que o simulador iniciar, você verá o prompt `SistemasOperacionais@Simulador:/$`. Os comandos devem ser digitados em português seguindo a sintaxe abaixo:

* `criarpasta [nome]` - Cria um novo diretório.
* `apagarpasta [nome]` - Remove um diretório existente.
* `criararquivo [nome] [conteudo]` - Cria um arquivo contendo o texto informado.
* `apagararquivo [nome]` - Apaga um arquivo.
* `listar` - Lista o conteúdo do diretório atual (mostra o que é arquivo e o que é pasta).
* `renomear [nome_antigo] [nome_novo]` - Altera o nome de um arquivo ou pasta.
* `copiar [origem] [destino]` - Duplica um arquivo.
* `sair` - Encerra o simulador com segurança.

```
