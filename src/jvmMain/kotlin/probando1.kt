

import java.sql.*
fun main() {
    //val url = "jdbc:mysql://localhost:3306/prueba"
//jdbc:oracle:thin:@//localhost:1521:xe
//   val url = "jdbc:oracle:thin:@localhost:1521:xe"
    /*
    val usuario = "SYSTEM"


    val contraseña = "MANAGER"


     */
    val url = "jdbc:oracle:thin:@localhost:1521:xe"
    val usuario = "alumno"
    val contraseña = "alumno"
    try {
        Class.forName("oracle.jdbc.driver.OracleDriver")
        val conexion = DriverManager.getConnection(url, usuario, contraseña)
        println("Conexión exitosa")


        val stmt = conexion.prepareStatement("INSERT INTO LIBRO  (book_id , title , year ) VALUES (?, ?, ?)")


        // val stmt = conn.prepareStatement("INSERT INTO LIBRO  (book_id , title , `year` ) VALUES ("+1+",el señor de las moscas,+ "+1950+"))"


        stmt.setInt(1, 1)
        stmt.setString(2, "el señor de las moscas")
        stmt.setInt(3, 1950)
        stmt.executeUpdate()
        println("Inserción exitosa")
        conexion.close()
    } catch (e: SQLException) {
        println("Error en la conexión: ${e.message}")
    } catch (e: ClassNotFoundException) {
        println("No se encontró el driver JDBC: ${e.message}")
    }

}
