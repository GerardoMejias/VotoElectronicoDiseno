package cr.ac.itcr.votoelectronico1;

public class Voto {
    private String tituloPropuesta;
    private String correoVotante;

    public Voto(String tituloPropuesta, String correoVotante)
    {
        this.tituloPropuesta = tituloPropuesta;
        this.correoVotante = correoVotante;
    }

    public String getTituloPropuesta() {
        return tituloPropuesta;
    }

    public void setTituloPropuesta(String tituloPropuesta) {
        this.tituloPropuesta = tituloPropuesta;
    }

    public String getCorreoVotante() {
        return correoVotante;
    }

    public void setCorreoVotante(String correoVotante) {
        this.correoVotante = correoVotante;
    }
}
