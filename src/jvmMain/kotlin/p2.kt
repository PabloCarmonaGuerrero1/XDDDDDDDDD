import Proyecto.usuario
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

fun main()  = application {
    Window(onCloseRequest = ::exitApplication) {
        App()
    }
}

@Composable
@Preview
fun App() {
    var text by remember { mutableStateOf("Hello, World!") }

    MaterialTheme {
        var usuario by remember { mutableStateOf("") }
        var contraseña by remember { mutableStateOf("") }
        var ic by remember { mutableStateOf("") }
        MaterialTheme {
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text("Gestion de Productos", modifier = Modifier.padding(bottom = 16.dp))
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

            }
            Button(onClick = { ic = iniciarsesion(usuario(usuario,contraseña)).toString() }) {
                Text("Iniciar")
            }
            if (ic.isNotBlank()) {

            }

        }
    }


}
fun iniciarsesion(usuario: usuario){
    var nombre=usuario.nombre
    var contraseña = usuario.contrasenya
    val conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe")
    val query = "SELECT name, password FROM Usuarios WHERE name = ? and password = ?"

    val statement = conn.prepareStatement(query)
    statement.setString(1, nombre)
    statement.setString(2, contraseña)

    val resultSet = statement.executeQuery()
    while (resultSet.next()) {
        val foundName = resultSet.getString("name")
        val password = resultSet.getString("password")
        println("Name: $foundName, Password: $password")
    }

    resultSet.close()
    statement.close()
    conn.close()
}


