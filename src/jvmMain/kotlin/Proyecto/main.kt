package Proyecto
import androidx.compose.material.MaterialTheme
import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.Image
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import java.awt.Image
import java.sql.DriverManager
import java.sql.Date
import java.text.ParseException
fun main()  = application {
    Window(title="Tienda del K" ,onCloseRequest = ::exitApplication) {
        App()
    }
}
@Composable
@Preview
fun App() {
    var paginaactual by remember { mutableStateOf("Inicio") }
    var user by remember { mutableStateOf("Nadie") }
    when(paginaactual){
        "Inicio"-> MaterialTheme {
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
                        visualTransformation = PasswordVisualTransformation(),
                        modifier = Modifier.padding(bottom = 16.dp)
                    )
                    Button(onClick = { ic = TiendaVideojuegosBD().iniciarsesion(usuario,contraseña) ;  contadorclicks++ ; registro=true }) {
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
                Button(onClick = { paginaactual="Actualizar" }) {
                    Text("Actualizar información")

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
                        visualTransformation = PasswordVisualTransformation(),
                        modifier = Modifier.padding(bottom = 16.dp)
                    )
                    OutlinedTextField(
                        value = repetir,
                        onValueChange = { repetir = it },
                        label = { Text("Repetir contraseñna") },
                        visualTransformation = PasswordVisualTransformation(),
                        modifier = Modifier.padding(bottom = 16.dp)
                    )
                    Button(onClick = { registrado = TiendaVideojuegosBD().crearUsuario(usuario,contraseña,repetir) ; registro = true }) {
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
                Button(onClick = { compra= TiendaVideojuegosBD().comprarjuego(user,titulo) ; confi = true }) {
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
                Button(onClick = { devolver= TiendaVideojuegosBD().devolverJuego(user,titulo) ; confi = true }) {
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
            val lista = remember { TiendaVideojuegosBD().listadejuegos() }
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
        "Actualizar"-> MaterialTheme {
            var titulo by remember { mutableStateOf("") }
            var desall by remember { mutableStateOf("") }
            var plat by remember { mutableStateOf("") }
            var precio by remember { mutableStateOf(0.0) }
            var fecha by remember { mutableStateOf("") }
            var actu by remember { mutableStateOf(false) }
            var confi by remember { mutableStateOf(false) }
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text("Actualiza un juego", modifier = Modifier.padding(bottom = 16.dp))
                OutlinedTextField(
                    value = titulo,
                    onValueChange = { titulo = it },
                    label = { Text("Titulo") },
                    modifier = Modifier.padding(bottom = 16.dp)
                )
                OutlinedTextField(
                    value = desall,
                    onValueChange = { desall = it },
                    label = { Text("Desarrollador") },
                    modifier = Modifier.padding(bottom = 16.dp)
                )
                OutlinedTextField(
                    value = plat,
                    onValueChange = { plat = it },
                    label = { Text("Plataforma") },
                    modifier = Modifier.padding(bottom = 16.dp)
                )
                OutlinedTextField(
                    value = precio.toString(),
                    onValueChange = { precio = it.toDoubleOrNull() ?:0.0 },
                    label = { Text("Precio") },
                    modifier = Modifier.padding(bottom = 16.dp)
                )
                OutlinedTextField(
                    value = fecha,
                    onValueChange = { fecha = it },
                    label = { Text("Fecha de lanzamiento") },
                    modifier = Modifier.padding(bottom = 16.dp)
                )
                Button(onClick = { actu= TiendaVideojuegosBD().actualizarVideojuego(titulo,desall,plat,fecha,precio) ; confi=true }) {
                    Text("Actualizar")
                }
                if (confi==true) {
                    if (actu == true) {
                        Text("Juego actualizado con éxito!!")
                    } else {
                        Text("ERROR EL JUEGO NO EXISTE , ESTA MAL ESCRITO O ALGUN PARAMETRO NO ES CORRECTO CON LA BASE DE DATOS")
                    }
                }
                Button(onClick = { paginaactual = "Registrado" }) {
                    Text("Volver atrás")
                }
            }
        }


    }
}


