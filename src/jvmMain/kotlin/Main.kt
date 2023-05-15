import java.sql.*

fun main() {

    val url = "jdbc:oracle:thin:@localhost:1521:xe"
    val usuario = "prog"
    val contraseña = "prog"

    try {
        Class.forName("oracle.jdbc.driver.OracleDriver")
        val conexion = DriverManager.getConnection(url, usuario, contraseña)
        println("Conexión exitosa")
        val query = "SELECT * FROM libro WHERE BOOK_ID=11  ORDER BY BOOK_ID"


        val statement = conexion.createStatement()


        val resultSet = statement.executeQuery(query)

        while (resultSet.next()) {
            val id = resultSet.getInt("BOOK_ID")
            val titulo = resultSet.getString("TITLE")
            val anio = resultSet.getInt("YEAR")

            println("ID: $id, Título: $titulo, Año: $anio")
        }

        statement.close()
        conexion.close()

    }
    catch (e: SQLException) {
        println("Error en la conexión: ${e.message}")
    } catch (e: ClassNotFoundException) {
        println("No se encontró el driver JDBC: ${e.message}")
    }
}
