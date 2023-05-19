package Proyecto
import java.sql.*
import java.text.ParseException
import java.text.SimpleDateFormat

class TiendaVideojuegosBD() {
    fun iniciarsesion(nombre: String, contrasena: String): Boolean {
        val url = "jdbc:oracle:thin:@localhost:1521:xe"
        val usuario = "alumno"
        val contraseña = "alumno"
        Class.forName("oracle.jdbc.driver.OracleDriver")
        var resultado = false
        val conn = DriverManager.getConnection(url,usuario,contraseña)
        val query = "SELECT * FROM Usuarios"
        val statement = conn.prepareStatement(query)

        val resultSet = statement.executeQuery()
        while (resultSet.next()) {
            val nom = resultSet.getString("name")
            val cont= resultSet.getString("password")
            if (nom==nombre && cont== contrasena) {
                resultado = true
            }
        }
        resultSet.close()
        statement.close()
        conn.close()
        return resultado
    }
    fun crearUsuario(nombre: String, contrasena: String, repetir: String): Boolean {
        var resultado = false
        val url = "jdbc:oracle:thin:@localhost:1521:xe"
        val usuario = "alumno"
        val contraseña = "alumno"
        Class.forName("oracle.jdbc.driver.OracleDriver")
        val conexion = DriverManager.getConnection(url, usuario, contraseña)

        val verificarStmt = conexion.prepareStatement("SELECT name FROM Usuarios WHERE name = ?")
        verificarStmt.setString(1, nombre)
        val resultSet = verificarStmt.executeQuery()

        if (!resultSet.next() && contrasena.isNotBlank() && contrasena == repetir) {
            val insertStmt = conexion.prepareStatement("INSERT INTO Usuarios (name, password) VALUES (?, ?)")
            insertStmt.setString(1, nombre)
            insertStmt.setString(2, contrasena)
            insertStmt.executeUpdate()
            resultado = true
        }
        conexion.close()
        return resultado
    }
    fun comprarjuego(name:String,juego:String):Boolean{
        val url = "jdbc:oracle:thin:@localhost:1521:xe"
        val usuario = "alumno"
        val contraseña = "alumno"
        Class.forName("oracle.jdbc.driver.OracleDriver")
        val conexion = DriverManager.getConnection(url, usuario, contraseña)
        val verificarStmt = conexion.prepareStatement("SELECT COUNT(*) FROM Videojuegos WHERE titulo = ?")
        verificarStmt.setString(1, juego)
        val resultado = verificarStmt.executeQuery()
        resultado.next()
        val existeJuego = resultado.getInt(1) > 0

        if (!existeJuego) {
            conexion.close()
            return false
        }
        val stmt = conexion.prepareStatement("INSERT INTO Compras  (name,titulo) VALUES (?,?)")
        stmt.setString(1, name)
        stmt.setString(2, juego)
        stmt.executeUpdate()
        val sql = conexion.prepareStatement("DELETE FROM Videojuegos WHERE titulo = ? ")
        sql.setString(1, juego)
        sql.executeUpdate()
        conexion.close()
        return true
    }
    fun devolverJuego(name: String, juego: String): Boolean {
        val url = "jdbc:oracle:thin:@localhost:1521:xe"
        val usuario = "alumno"
        val contraseña = "alumno"
        Class.forName("oracle.jdbc.driver.OracleDriver")
        val conexion = DriverManager.getConnection(url, usuario, contraseña)

        val query = "SELECT * FROM Compras"
        val statement = conexion.prepareStatement(query)

        val resultSet = statement.executeQuery()
        while (resultSet.next()) {
            val nom = resultSet.getString("name")
            val cont= resultSet.getString("titulo")
            if (nom==name && cont== juego) {
                val juegos = conexion.prepareStatement("SELECT titulo, desarrollador, plataforma, fecha_lanzamiento, precio FROM Videojuegos2 WHERE titulo = ?")
                juegos.setString(1, juego)
                val anyadir = juegos.executeQuery()

                if (anyadir.next()) {
                    val desall = anyadir.getString("desarrollador")
                    val plat = anyadir.getString("plataforma")
                    val fecha = anyadir.getDate("fecha_lanzamiento")
                    val precio = anyadir.getDouble("precio")

                    val stmt = conexion.prepareStatement("INSERT INTO Videojuegos (titulo, desarrollador, plataforma, fecha_lanzamiento, precio) VALUES (?, ?, ?, ?, ?)")
                    stmt.setString(1, juego)
                    stmt.setString(2, desall)
                    stmt.setString(3, plat)
                    stmt.setDate(4, fecha)
                    stmt.setDouble(5, precio)
                    stmt.executeUpdate()

                    val sql = conexion.prepareStatement("DELETE FROM Compras WHERE titulo = ?")
                    sql.setString(1, juego)
                    sql.executeUpdate()
                }
                conexion.close()
                return true
            }
        }
        conexion.close()
        return false
    }
    fun listadejuegos():MutableList<String>{
        var lista = mutableListOf<String>()
        val url = "jdbc:oracle:thin:@localhost:1521:xe"
        val usuario = "alumno"
        val contraseña = "alumno"
        Class.forName("oracle.jdbc.driver.OracleDriver")
        val conexion = DriverManager.getConnection(url, usuario, contraseña)
        val statement = conexion.prepareStatement("Select titulo from Videojuegos")
        val resultSet = statement.executeQuery()
        while (resultSet.next()) {
            var titulo = resultSet.getString("titulo")
            lista.add(titulo)
        }
        return  lista
    }
    fun actualizarVideojuego(titulo: String, desarrollador: String, plataforma: String, fechaLanzamiento: String, precio: Double): Boolean {
        val url = "jdbc:oracle:thin:@localhost:1521:xe"
        val usuario = "alumno"
        val contraseña = "alumno"
        Class.forName("oracle.jdbc.driver.OracleDriver")
        val conexion = DriverManager.getConnection(url, usuario, contraseña)
        val verificarStmt = conexion.prepareStatement("SELECT COUNT(*) FROM Videojuegos WHERE titulo = ?")
        verificarStmt.setString(1, titulo)
        val resultado = verificarStmt.executeQuery()
        resultado.next()
        val existeJuego = resultado.getInt(1) > 0

        if (!existeJuego) {
            conexion.close()
            return false
        }

        try {
            val formatoFecha = SimpleDateFormat("yyyy-MM-dd")
            val fecha = Date(formatoFecha.parse(fechaLanzamiento).time)

            val consulta = "UPDATE videojuegos SET desarrollador = ?, plataforma = ?, fecha_lanzamiento = ?, precio = ? WHERE titulo = ?"
            val declaracion = conexion.prepareStatement(consulta)
            declaracion.setString(1, desarrollador)
            declaracion.setString(2, plataforma)
            declaracion.setDate(3, fecha)
            declaracion.setDouble(4, precio)
            declaracion.setString(5, titulo)
            declaracion.executeUpdate()
        } catch (e: ParseException) {
            conexion.close()
            return false
        } catch (e: IllegalArgumentException) {
            conexion.close()
            return false
        }

        conexion.close()
        return true
    }
}