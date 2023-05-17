import androidx.compose.material.MaterialTheme
import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import java.sql.DriverManager
import java.sql.Date
fun main()  = application {
    Window(onCloseRequest = ::exitApplication) {
        App()
    }
}
@Composable
@Preview
fun App() {
    var paginaactual by remember { mutableStateOf("Inicio") }
    var user by remember { mutableStateOf("Nadie") }
    when(paginaactual){
        "Inicio"->MaterialTheme {
            var usuario by remember { mutableStateOf("") }
            var contraseña by remember { mutableStateOf("") }
            var ic by remember { mutableStateOf(false) }
            var contadorclicks=0
            var registro by remember { mutableStateOf(false) }
            MaterialTheme {
                Column(
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text("Tienda de videojuegos", modifier = Modifier.padding(bottom = 16.dp))
                    OutlinedTextField(
                        value = usuario,
                        onValueChange = { usuario = it },
                        label = { Text("Nombre de usuario") },
                        modifier = Modifier.padding(bottom = 16.dp)
                    )
                    OutlinedTextField(
                        value = contraseña,
                        onValueChange = { contraseña = it },
                        label = { Text("Contraseña") },
                        modifier = Modifier.padding(bottom = 16.dp)
                    )
                    Button(onClick = { ic = iniciarsesion(usuario,contraseña) ;  contadorclicks++ ; registro=true }) {
                        Text("Iniciar")

                    }
                    if (registro==true){
                        if (contadorclicks>0 && ic==false) {
                            Text("ERROR USUARIO Y/O CONTRASEÑA INCORRECTO/S")
                        }
                        else if(contadorclicks>0 && ic==true && usuario.isNotBlank() && contraseña.isNotBlank()){
                            paginaactual="Registrado"
                            user = usuario
                        }
                    }
                    Button(onClick = { paginaactual="Registrar" }) {
                        Text("Registrar")

                    }
                }

            }
        }
        "Registrado"->MaterialTheme{
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text("Elige lo que quieres hacer", modifier = Modifier.padding(bottom = 16.dp))
                Button(onClick = { paginaactual="Juegos" }) {
                    Text("Ver juegos a la venta")

                }
                Button(onClick = { paginaactual="Comprar" }) {
                    Text("Comprar un videojuego")

                }
                Button(onClick = { paginaactual="Devolver" }) {
                    Text("Devolver un videojuego")

                }
                Button(onClick = { paginaactual="Inicio" }) {
                    Text("Volver atrás")

                }
            }
        }
        "Registrar"-> MaterialTheme {
            var usuario by remember { mutableStateOf("") }
            var contraseña by remember { mutableStateOf("") }
            var repetir by remember { mutableStateOf("") }
            var registrado by remember { mutableStateOf(false) }
            var registro by remember { mutableStateOf(false) }
            MaterialTheme{
                Column(
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text("Crea tu usuario!!", modifier = Modifier.padding(bottom = 16.dp))
                    OutlinedTextField(
                        value = usuario,
                        onValueChange = { usuario = it },
                        label = { Text("Nombre de usuario") },
                        modifier = Modifier.padding(bottom = 16.dp)
                    )
                    OutlinedTextField(
                        value = contraseña,
                        onValueChange = { contraseña = it },
                        label = { Text("Contraseña") },
                        modifier = Modifier.padding(bottom = 16.dp)
                    )
                    OutlinedTextField(
                        value = repetir,
                        onValueChange = { repetir = it },
                        label = { Text("Repetir contraseñna") },
                        modifier = Modifier.padding(bottom = 16.dp)
                    )
                    Button(onClick = { registrado = crearUsuario(usuario,contraseña,repetir) ; registro = true }) {
                        Text("Crear usuario")

                    }
                    if (registro==true){
                        if ( registrado==false && usuario.isNotBlank()) {
                            Text("ERROR LAS CONTRASEÑAS NO COINCIDEN , HAY ALGÚN APARTADO EN BLANCO O EL NOMBRE DE USUARIO YA ESTA REGISTRADO")
                        }
                        else if(registrado==true && usuario.isNotBlank()){
                            paginaactual="Inicio"
                        }
                    }
                    Button(onClick = { paginaactual="Inicio" }) {
                        Text("Volver atrás")

                    }
                }
            }
        }
        "Comprar"-> MaterialTheme {
            var titulo by remember { mutableStateOf("") }
            var compra by remember { mutableStateOf(false) }
            var confi by remember { mutableStateOf(false) }
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ){
                OutlinedTextField(
                    value = titulo,
                    onValueChange = { titulo = it },
                    label = { Text("Título del juego") },
                    modifier = Modifier.padding(bottom = 16.dp)
                )
                Button(onClick = { compra=comprarjuego(user,titulo) ; confi = true }) {
                    Text("Comprar")

                }
                if(confi==true){
                    if (compra==true){
                        Text("Juego comprado con éxito!!")
                    }
                    else{
                        Text("ERROR JUEGO NO DISPONIBLE O ESCRITO ERRONEAMENTE")
                    }
                }

            }
            Button(onClick = { paginaactual="Registrado" }) {
                Text("Volver atrás")

            }
        }
        "Devolver"-> MaterialTheme {
            var titulo by remember { mutableStateOf("") }
            var devolver by remember { mutableStateOf(false) }
            var confi by remember { mutableStateOf(false) }
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ){
                OutlinedTextField(
                    value = titulo,
                    onValueChange = { titulo = it },
                    label = { Text("Título del juego") },
                    modifier = Modifier.padding(bottom = 16.dp)
                )
                Button(onClick = { devolver=devolverJuego(user,titulo) ; confi = true }) {
                    Text("Devolver")

                }
                if(confi==true){
                    if (devolver==true){
                        Text("Juego devuelto con éxito!!")
                    }
                    else{
                        Text("ERROR JUEGO NO DISPONIBLE O ESCRITO ERRONEAMENTE")
                    }
                }

            }
            Button(onClick = { paginaactual="Registrado" }) {
                Text("Volver atrás")

            }
        }
        "Juegos"-> MaterialTheme {
            val lista = remember { listadejuegos() }
            var i by remember { mutableStateOf(0) }

            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                val url = "jdbc:oracle:thin:@localhost:1521:XE"
                val username = "alumno"
                val password = "alumno"
                val connection = DriverManager.getConnection(url, username, password)
                if(i>lista.size - 1){
                    i= 0
                }
                if(i<0){
                    i= lista.size - 1
                }
                if (i in lista.indices) {
                    val query = "SELECT * FROM Videojuegos WHERE titulo = ?"
                    val preparedStatement = connection.prepareStatement(query)
                    preparedStatement.setString(1, lista[i])
                    val resultSet = preparedStatement.executeQuery()

                    while (resultSet.next()) {
                        val nombre = resultSet.getString("titulo")
                        val desall = resultSet.getString("desarrollador")
                        val plat = resultSet.getString("plataforma")
                        val fecha = resultSet.getDate("fecha_lanzamiento")
                        val precio = resultSet.getDouble("precio")
                        Text("Título: $nombre")
                        Text("Desarrollador: $desall")
                        Text("Plataforma: $plat")
                        Text("Fecha de lanzamiento: $fecha")
                        Text("Precio: $precio")
                    }

                    resultSet.close()
                    preparedStatement.close()
                }

                connection.close()
                Column(
                    modifier = Modifier.padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Bottom
                ) {
                    Button(onClick = {  i++ }) {
                        Text("Siguiente")
                    }
                    Button(onClick = {  i -- }) {
                        Text("Anterior")
                    }
                }
            }
            Button(onClick = { paginaactual = "Registrado" }) {
                Text("Volver atrás")
            }
        }

    }
}

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