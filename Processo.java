public class Processo {
    private static int pidBase = 1;
    private int pidProcesso;
    private String dataNomeProcesso;
    private String cargaDeTrabalho;
    private int activeState;

    public Processo() {
        pidProcesso = pidBase++;
    }

    public int getpIdProcesso() {
        return pidProcesso;
    }

    public void setDataNomeProcesso(String dataNomeProcesso) {
        this.dataNomeProcesso = dataNomeProcesso;
    }

    public String getDataNomeProcesso() {
        return dataNomeProcesso;
    }

    public boolean checkCargaTrabalhoCPU() {
        boolean retorno;
        if (this.cargaDeTrabalho.charAt(0) == 'A' || this.cargaDeTrabalho.charAt(0) == 'B') {
            retorno = true;
        } else {
            retorno = false;
        }
        return retorno;
    }

    public void execCargaTrabalho() {
        if (this.cargaDeTrabalho.charAt(0) == 'A' || this.cargaDeTrabalho.charAt(0) == 'C') {
            this.cargaDeTrabalho = this.cargaDeTrabalho.substring(1);
        } else if (this.cargaDeTrabalho.charAt(0) == 'B') {
            this.cargaDeTrabalho = this.cargaDeTrabalho.replaceFirst("B", "A");
        } else if (this.cargaDeTrabalho.charAt(0) == 'D') {
            this.cargaDeTrabalho = this.cargaDeTrabalho.replaceFirst("D", "C");
        }
    }

    public void setCargaDeTrabalho(String cargaDeTrabalho) {
        this.cargaDeTrabalho = cargaDeTrabalho;
    }

    public String getCargaDeTrabalho() {
        return cargaDeTrabalho;
    }


}
