package controller.Amministratore;

public class Operazione {

    private final String taxCodeDiabetologo;
    private final String operazione;
    private final String taxCodePaziente;
    private final String dataOperazione;

    public Operazione(String taxCodeDiabetologo, String operazione, String taxCodePaziente, String dataOperazione) {
        this.taxCodeDiabetologo = taxCodeDiabetologo;
        this.operazione = operazione;
        this.taxCodePaziente = taxCodePaziente;
        this.dataOperazione = dataOperazione;
    }

    public String getTaxCodeDiabetologo() { return taxCodeDiabetologo; }
    public String getOperazione() { return operazione; }
    public String getTaxCodePaziente() { return taxCodePaziente; }
    public String getDataOperazione() { return dataOperazione; }
}
