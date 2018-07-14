import java.util.Scanner;

public class GerenciadorDeProcessos {
    private int contClicloExec;
    private int quantum;
    private static int QUANTUM;
    private boolean continua = true;

    private int processoAtual;
    private String nomeProcessoAtual;
    private String cargaProcessoAtual;
    private Processo processoPronto;
    private Processo processoExecucao;
    private Processo processoEspera;
    private Processo processoFinalizado;

    public Processo createProcess() {
        return new Processo();
    }

    public void addFilaPronto(Processo processo) {
        this.processoPronto = processo;
        this.processoAtual = this.processoPronto.getpIdProcesso();
        this.nomeProcessoAtual = this.processoPronto.getDataNomeProcesso();
        this.cargaProcessoAtual = this.processoPronto.getCargaDeTrabalho();
    }

    public void setQUANTUM(int QUANTUM) {
        GerenciadorDeProcessos.QUANTUM = QUANTUM;
        this.quantum = QUANTUM;
    }

    public boolean process(){
        boolean retorno = true;
        Scanner seguinte = new Scanner(System.in);
        // Não se pode fazer uma só chamada do método showProcess(), pois a cada entrada no if, é feito um processamento diferente.
        if (continua) {
            if (processoPronto != null && processoExecucao == null && processoEspera == null) {
                showProcess();
                if(processoPronto.checkCargaTrabalhoCPU()) {
                    processoExecucao = processoPronto;
                    System.out.println("Carga: " + processoExecucao.getCargaDeTrabalho());
                } else {
                    processoEspera = processoPronto;
                    System.out.println("Carga: " + processoEspera.getCargaDeTrabalho());
                }
                System.out.println("Próximo passo: " + "Selecionar um processo para executar!");
            } else if (processoPronto != null && processoExecucao != null) {
                processoPronto = null;
                showProcess();
                System.out.println("Carga: " + processoExecucao.getCargaDeTrabalho());
                System.out.println("Próximo passo: " + "Executar um ciclo de CPU");
                contClicloExec++;
            } else if (processoPronto != null && processoEspera != null) {
                processoPronto = null;
                showProcess();
                System.out.println("Carga: " + processoEspera.getCargaDeTrabalho());
                System.out.println("Próximo passo: " + "Aguardando um cliclo de E/S");
                contClicloExec++;
            } else if (processoExecucao != null || processoEspera != null) {
                if (processoExecucao != null) {
                    if (quantum > 0) {
                        if (!processoExecucao.getCargaDeTrabalho().equals("")) {
                            processoExecucao.execCargaTrabalho();
                            quantum--;
                            if (!processoExecucao.getCargaDeTrabalho().equals("") && processoExecucao.checkCargaTrabalhoCPU()) {
                                showProcess();
                                System.out.println("Carga: " + processoExecucao.getCargaDeTrabalho());
                                System.out.println("Próximo passo: " + "Executar um ciclo de CPU");
                            } else if (processoExecucao.getCargaDeTrabalho().equals("")) {
                                processoFinalizado = processoExecucao;
                                processoExecucao = null;
                                showProcess();
                                System.out.println("Carga: " + processoFinalizado.getCargaDeTrabalho());
                                System.out.println("Próximo passo: " + "Terminando");
                            } else {
                                processoEspera = processoExecucao;
                                processoExecucao = null;
                                showProcess();
                                System.out.println("Carga: " + processoEspera.getCargaDeTrabalho());
                                System.out.println("Próximo passo: " + "Aguardando um ciclo do E/S");
                            }
                            contClicloExec++;
                        } else {
                            finishProcess();
                            System.out.println("Carga: -");
                            System.out.println("Próximo passo: " + "O processo será excluído");
                        }
                    } else {
                        processoPronto = processoExecucao;
                        processoExecucao = null;
                        quantum = QUANTUM;
                        return false;
                        // showProcess();
                    }
                } else if (processoEspera != null) {
                    if (quantum > 0) {
                        if(!processoEspera.getCargaDeTrabalho().equals("")) {
                            processoEspera.execCargaTrabalho();
                            quantum--;
                            if (!processoEspera.getCargaDeTrabalho().equals("") && !processoEspera.checkCargaTrabalhoCPU()) {
                                showProcess();
                                System.out.println("Carga: " + processoEspera.getCargaDeTrabalho());
                                System.out.println("Próximo passo: " + "Aguardando um ciclo do E/S");
                            } else if (processoEspera.getCargaDeTrabalho().equals("")) {
                                processoFinalizado = processoEspera;
                                processoEspera = null;
                                showProcess();
                                System.out.println("Carga: " + processoFinalizado.getCargaDeTrabalho());
                                System.out.println("Próximo passo: " + "Terminando");
                            } else {
                                processoPronto = processoEspera;
                                processoEspera = null;
                                showProcess();
                                System.out.println("Carga: " + processoPronto.getCargaDeTrabalho());
                                System.out.println("Próximo passo: " + "Executar um ciclo de CPU");
                            }
                            contClicloExec++;
                        } else {
                            finishProcess();
                            System.out.println("Carga: -");
                            System.out.println("Próximo passo: " + "O processo será excluído");
                        }
                    } else {
                        processoPronto = processoEspera;
                        processoEspera = null;
                        quantum = QUANTUM;
                        return false;
                        // showProcess();
                    }
                }
            } else {
                String valor;
                System.out.println("+======================================================+");
                System.out.println("Processo excluído");
                continua = false;
            }
            System.out.println("Pressione qualquer Tecla para continuar");
            seguinte.nextLine();
        }
        return retorno;
    }

    private void showProcess() {
        System.out.println("+------------------------------------------------------+");
        System.out.println("|                    GERENCIADOR                       |");
        System.out.println("+------------------------------------------------------+");
        System.out.println("| Contador de ciclo de execu��o : " + contClicloExec);
        System.out.println("| Processos na fila de pronto (" + (processoPronto != null && processoExecucao == null ? 1 + ") : " + processoPronto.getpIdProcesso() : 0 + ") : -"));
        System.out.println("| Processo em execu��o : " + (processoExecucao != null ? processoExecucao.getpIdProcesso() : "-"));
        System.out.println("| Tempo restante : " + (processoPronto == null ? quantum : "-"));
        System.out.println("| Carga : " + (processoExecucao != null && !processoExecucao.getCargaDeTrabalho().equals("") ? processoExecucao.getCargaDeTrabalho() : "-"));
        System.out.println("| Processos esperando : " + (processoEspera != null ? processoEspera.getpIdProcesso() : "-"));
        System.out.println("| Carga : " + (processoEspera != null ? processoEspera.getCargaDeTrabalho() : "-"));
        System.out.println("| Processos finalizados : " + (processoFinalizado != null ? processoFinalizado.getpIdProcesso() : "-"));
        System.out.println("+------------------------------------------------------+");
        System.out.println("Processo " + this.processoAtual + " " + this.nomeProcessoAtual + " / Estado Atual : " + (processoPronto != null && processoExecucao == null ? "Pronto" : processoExecucao != null ? "Executando" : processoEspera != null ? "Esperando" : "Terminando"));
    }

    private void finishProcess() {
        if (processoExecucao != null) {
            processoFinalizado = processoExecucao;
        } else if (processoEspera != null) {
            processoFinalizado = processoEspera;
        }
        processoExecucao = null;
        processoEspera = null;
        showProcess();
    }
}
