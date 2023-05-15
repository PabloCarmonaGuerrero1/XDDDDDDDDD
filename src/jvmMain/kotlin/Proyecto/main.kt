package Proyecto
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import java.sql.DriverManager
import java.sql.SQLException

@Composable
@Preview
fun App() {
    var text by remember { mutableStateOf("Hello, World!") }
    var pagina by remember { mutableStateOf("Inicio") }
    val url = "jdbc:oracle:thin:@localhost:1521:xe"
    val user = "usuario"
    val password = "usuario"

    try {
        val connection = DriverManager.getConnection(url, user, password)
    }catch (e: SQLException) {
    }
    when(pagina){
        "Inicio"->
            MaterialTheme {
                var ic by remember { mutableStateOf("") } // variable de estado para almacenar el resultado de iniciarSesion
                var usuario by remember { mutableStateOf("") }
                var contrasena by remember { mutableStateOf("") }

                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    // campo de entrada de usuario
                    OutlinedTextField(
                        value = usuario,
                        onValueChange = { usuario = it },
                        label = { Text("Usuario") }
                    )

                    // campo de entrada de contraseña
                    OutlinedTextField(
                        value = contrasena,
                        onValueChange = { contrasena = it },
                        label = { Text("Contraseña") },
                        visualTransformation = PasswordVisualTransformation()
                    )

                    // botón de inicio de sesión
                    Button(
                        onClick = {
                            ic = if (iniciarSesion(usuario, contrasena)) "Inicio de sesión exitoso" else "Credenciales incorrectas"
                        }
                    ) {
                        Text("Iniciar")
                    }

                    // resultado del inicio de sesión
                    Text(ic)
                }
            }

    }

}
fun main() = application {
    Window(onCloseRequest = ::exitApplication) {
        App()
    }
}

fun iniciarSesion(nombre: String, contrasena: String): Boolean {
    val conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe")
    val query = "SELECT name, password FROM Usuarios WHERE name = ? and password = ?"

    val statement = conn.prepareStatement(query)
    statement.setString(1, nombre)
    statement.setString(2, contrasena)
    val resultSet = statement.executeQuery()
    val found = resultSet.next() // comprobar si hay algún resultado
    resultSet.close()
    statement.close()
    conn.close()
    return found
}

