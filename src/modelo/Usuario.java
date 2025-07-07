package modelo;

public class Usuario {
    private int id;
    private String nombre;
    private String email;
    private String contraseña;
    private String tipo;  // 'administrador' o 'vendedor'
    private String rol;   // 'ADMIN' o 'CLIENTE'

    public Usuario(int id, String nombre, String email, String contraseña, String rol) {
        this.id = id;
        this.nombre = nombre;
        this.email = email;
        this.contraseña = contraseña;
       
        this.rol = rol;
    }

    // Getters y setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getContraseña() { return contraseña; }
    public void setContraseña(String contraseña) { this.contraseña = contraseña; }

    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }

    public String getRol() { return rol; }
    public void setRol(String rol) { this.rol = rol; }
}
