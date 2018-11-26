package cr.ac.itcr.votoelectronico1;

/**
 * Created by Gerardo on 6/6/2018.
 */
public class Propuesta {

    private String uid;
    private String titulo;
    private String autor;
    private String fechaLimite;
    private String horaLimite;
    private String descripcion;
    private String correos;
    private boolean finalizado;
    private int aprueban;
    private int rechazan;
    private int nulos;
    private int blancos;

    /*public Propuesta(String tit, String aut, String fl, String hl, String desc, String cor) {
        this.titulo = tit;
        this.autor = aut;
        this.fechaLimite = fl;
        this.horaLimite = hl;
        this.descripcion = desc;
        this.correos = cor;
        finalizado = false;
        aprueban = 0;
        rechazan = 0;
        nulos = 0;
        blancos = 0;
    }*/

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public String getFechaLimite() {
        return fechaLimite;
    }

    public void setFechaLimite(String fechaLimite) {
        this.fechaLimite = fechaLimite;
    }

    public String getHoraLimite() {
        return horaLimite;
    }

    public void setHoraLimite(String horaLimite) {
        this.horaLimite = horaLimite;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getCorreos() {
        return correos;
    }

    public void setCorreos(String correos) {
        this.correos = correos;
    }

    public boolean getFinalizado() {
        return finalizado;
    }

    public void setFinalizado(boolean finalizado) {
        this.finalizado = finalizado;
    }

    public int getAprueban() {
        return aprueban;
    }

    public void setAprueban(int aprueban) {
        this.aprueban = aprueban;
    }

    public int getRechazan() {
        return rechazan;
    }

    public void setRechazan(int rechazan) {
        this.rechazan = rechazan;
    }

    public int getNulos() {
        return nulos;
    }

    public void setNulos(int nulos) {
        this.nulos = nulos;
    }

    public int getBlancos() {
        return blancos;
    }

    public void setBlancos(int blancos) {
        this.blancos = blancos;
    }

    @Override
    public String toString() {
        return titulo;
    }
}
