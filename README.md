# Simulador de Sistema de Arquivos com Journaling

**Aluno:** Davi Dias Vale  
**Link do Repositório:** [Link do GitHub](https://github.com/DaviDias158/SimuladorSistemaArquivos)  
**Professor:** Me. Izequiel Norões  
**Disciplina:** Proj de Sistema Operacional

---

## Resumo
Este projeto consiste no desenvolvimento de um simulador de sistema de arquivos em Java. O objetivo principal é demonstrar de forma prática como um Sistema Operacional gerencia arquivos e diretórios em memória, utilizando a técnica de **Journaling** para garantir a consistência e integridade dos dados contra falhas repentinas.

---

## Metodologia
O simulador foi desenvolvido utilizando a linguagem **Java**. O sistema funciona de duas formas complementares:
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

### Estrutura de Dados e Persistência
Para criar o nosso próprio "sistema independente", o simulador foi estruturado utilizando o padrão de projeto *Composite*, permitindo mapear pastas e subpastas de forma hierárquica na memória RAM enquanto o programa roda:

* **Classe Abstrata `Componente`:** Componente base que obriga todos os elementos do sistema a possuírem um atributo `nome`. Implementa a interface `Serializable`.
* **Classe `Arquivo`:** Especialização de `Componente` que armazena a propriedade `conteudo` (texto do arquivo).
* **Classe `Diretorio`:** Especialização de `Componente` que gerencia um mapa dinâmico de filhos (`Map<String, Componente>`), permitindo aninhar recursivamente tanto arquivos quanto outros diretórios em formato de árvore.

#### O Disco Virtual (`filesystem.dat`)
Diferente de abordagens que criam pastas reais no sistema operacional do computador, este simulador centraliza toda a sua árvore de diretórios em um único arquivo binário proprietário chamado **`filesystem.dat`**. Usando a técnica de **Serialização de Objetos em Java**, a árvore inteira de objetos é convertida em um fluxo de bytes oculto e salva neste arquivo, impedindo que o usuário veja ou edite as pastas criadas diretamente pelo explorador do Windows ou Linux.

### Funcionamento do Journaling no Simulador
A classe `Journal` gerencia um arquivo de texto visível na raiz chamado `journal.txt`. O fluxo atende estritamente ao protocolo transacional:
1. O usuário digita: `criarpasta Faculdade`.
2. O simulador invoca o método `Journal.gravar("START: mkdir Faculdade")`.
3. O simulador altera a estrutura em memória e commita as alterações no disco virtual binário `filesystem.dat`.
4. O simulador encerra a transação com `Journal.gravar("COMMIT: mkdir Faculdade")`.

---

## Parte 3: Implementação em Java

O projeto foi modularizado em arquivos separados para garantir a manutenibilidade e boas práticas arquiteturais:

* **`Componente.java`:** Contém a definição da classe abstrata `Componente` e as classes herdeiras `Arquivo` e `Diretorio`.
* **`Journal.java`:** Classe utilitária responsável pelos métodos estáticos `gravar(String operacao)` e `limpar()`, gerenciando a saída de texto do log de integridade.
* **`FileSystemSimulator.java`:** Centraliza o núcleo operacional do sistema de arquivos e os mecanismos de Entrada/Saída do disco virtual. Métodos implementados:
  * `carregarSistema()`: Lê o arquivo `filesystem.dat` se ele existir, restaurando o estado anterior do sistema de arquivos na inicialização.
  * `salvarSistema()`: Serializa o estado atual da árvore de objetos para o arquivo `filesystem.dat`.
  * `criarDiretorio(String nome)`
  * `apagarDiretorio(String nome)`
  * `criarArquivo(String nome, String conteudo)`
  * `apagarArquivo(String nome)`
  * `renomear(String antigo, String novo)`
  * `copiarArquivo(String origem, String destino)`
  * `listarDiretorio()`
* **`ShellInterpreter.java`:** Classe responsável por realizar o *parsing* (interpretação) das strings digitadas no console e rotear para o método respectivo da lógica de negócio.
* **`Main.java`:** Classe de entrada simplificada que gerencia unicamente a captura do input do usuário através do objeto `Scanner` e mantém o loop ativo da interface de linha de comando.

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



2. **Compilar todos os arquivos `.java` juntos:**
   ```bash
    javac *.java




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

Ao iniciar, você será recebido pelo prompt customizado `SistemasOperacionais@Simulador:/$`. Digite as instruções em português conforme os padrões abaixo:

* `criarpasta [nome]` - Cria um novo diretório no local atual.
* `apagarpasta [nome]` - Remove um diretório existente.
* `criararquivo [nome] [conteudo]` - Cria um arquivo e injeta um texto dentro dele.
* `apagararquivo [nome]` - Remove permanentemente um arquivo do disco.
* `listar` - Exibe de forma tabular todo o conteúdo (arquivos `[ARQ]` e diretórios `[DIR]`) presentes no caminho atual.
* `renomear [nome_antigo] [nome_novo]` - Altera o nome de uma pasta ou arquivo existente.
* `copiar [origem] [destino]` - Duplica um arquivo mantendo o conteúdo idêntico.
* `sair` - Salva os dados finais e encerra a execução do simulador.

---


### Resultados Esperados

Através deste projeto prático, foi possível simular com fidelidade o comportamento interno de estruturas de dados e armazenamento de um Sistema Operacional. O uso da serialização no arquivo binário `filesystem.dat` isolou com sucesso o escopo do nosso sistema em relação ao sistema operacional hospedeiro.

Paralelamente, a visualização transparente do arquivo `journal.txt` permitiu constatar como os estágios `START` e `COMMIT` blindam o ecossistema contra inconsistências lógicas, consolidando de maneira clara os conceitos teóricos fundamentais ministrados em sala de aula.


