import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean continuarExecucao = true;

        while (continuarExecucao) {

            gerarCabecalho();

            continuarExecucao = abstrairConteudoParaExecucao(scanner);
        }
    }

    private static boolean abstrairConteudoParaExecucao(Scanner scanner) {
        int quantum;
        String nomeDoProcesso;
        String cargaDeTrabalho;
        boolean continuarExecucao;
        GerenciadorDeProcessos gerenciadorDeProcessos = new GerenciadorDeProcessos();
        Processo processo = gerenciadorDeProcessos.createProcess();

        PegarInfo pegarInfo = new PegarInfo(scanner).invoke();
        quantum = pegarInfo.getQuantum();
        nomeDoProcesso = pegarInfo.getNomeDoProcesso();
        cargaDeTrabalho = pegarInfo.getCargaDeTrabalho();

        setInfo(quantum, nomeDoProcesso, cargaDeTrabalho, gerenciadorDeProcessos, processo);

        gerenciadorDeProcessos.addFilaPronto(processo);

        printProcesso(processo);

        continuarExecucao = gerenciadorDeProcessos.process();

        continuarExecucao = testContinuarExecucao(scanner, continuarExecucao);
        return continuarExecucao;
    }

    private static boolean testContinuarExecucao(Scanner scanner, boolean continuarExecucao) {
        if (continuarExecucao) {
            System.out.println("Criar outro processo (S/N)");
            continuarExecucao = scanner.next().equals("S");
        }
        return continuarExecucao;
    }

    private static void printProcesso(Processo processo) {
        System.out.println("| Processo " + processo.getpIdProcesso() + " foi criado com sucesso!");
        System.out.println("| Iniciando simulação...");
    }

    private static void setInfo(int quantum, String nomeDoProcesso, String cargaDeTrabalho, GerenciadorDeProcessos gerenciadorDeProcessos, Processo processo) {
        processo.setDataNomeProcesso(nomeDoProcesso);
        processo.setCargaDeTrabalho(cargaDeTrabalho);
        gerenciadorDeProcessos.setQUANTUM(quantum);
    }

    private static void gerarCabecalho() {
        System.out.println("+------------------------------------------------------+");
        System.out.println("|                   GERENCIADOR                        |");
        System.out.println("+------------------------------------------------------+");
    }

    private static class PegarInfo {
        private Scanner scanner;
        private int quantum;
        private String nomeDoProcesso;
        private String cargaDeTrabalho;

        public PegarInfo(Scanner scanner) {
            this.scanner = scanner;
        }

        public int getQuantum() {
            return quantum;
        }

        public String getNomeDoProcesso() {
            return nomeDoProcesso;
        }

        public String getCargaDeTrabalho() {
            return cargaDeTrabalho;
        }

        public PegarInfo invoke() {
            System.out.print("| Especificar quantum: ");
            quantum = scanner.nextInt();
            System.out.print("| Nome do processo: ");
            nomeDoProcesso = scanner.next();
            System.out.print("| Especificar carga de trabalho: ");
            cargaDeTrabalho = scanner.next();
            return this;
        }
    }
}