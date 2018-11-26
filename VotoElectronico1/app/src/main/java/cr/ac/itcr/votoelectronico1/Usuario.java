package cr.ac.itcr.votoelectronico1;

/**
 * Created by Gerardo on 8/6/2018.
 */
public class Usuario {

    String nombre;
    String apellidos;
    String correo;
    String clave;
    boolean rol;
    String Uid;
    boolean esActivo = true;

    public Usuario(){}

    public Usuario(String nombre, String apellidos, String correo, String clave, boolean rol) {
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.correo = correo;
        this.clave = clave;
        this.rol = rol;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public boolean getRol() {
        return rol;
    }

    public void setRol(boolean rol) {
        this.rol = rol;
    }
    public void setUid(String id)
    {
        Uid = id;
    }
    public String getUid()
    {
        return Uid;
    }
}
