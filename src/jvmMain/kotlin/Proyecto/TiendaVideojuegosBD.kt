package Proyecto
import java.sql.*
class TiendaVideojuegosBD {
    val url = "jdbc:oracle:thin:@localhost:1521:xe"
    val usuario = "alumno"
    val contraseña = "alumno"
    lateinit var conn: Connection
    init {
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver")
            conn = DriverManager.getConnection(url, usuario, contraseña)
            println("Conexión establecida")
        } catch (e: SQLException) {
            e.printStackTrace()
        } catch (e: ClassNotFoundException) {
            e.printStackTrace()
        }
    }
    fun anyadirproducto(){
        var v = videojuego()

        return println("Ha sido añadido correctamente")
    }
}