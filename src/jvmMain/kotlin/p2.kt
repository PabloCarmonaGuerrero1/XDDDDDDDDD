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
    var paginaactual by remember { mutableStateOf("Inicio") }
    val url = "jdbc:oracle:thin:@localhost:1521:xe"
    val usuario = "alumno"
    val contraseña = "alumno"
    Class.forName("oracle.jdbc.driver.OracleDriver")
    val conexion = DriverManager.getConnection(url, usuario, contraseña)
    when(paginaactual){
        "Inicio"->MaterialTheme {
            var usuario by remember { mutableStateOf("") }
            var contraseña by remember { mutableStateOf("") }
            var ic by remember { mutableStateOf(false) }
            var contadorclicks=0
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
                    Button(onClick = { ic = iniciarsesion(usuario,contraseña) }) {
                        Text("Iniciar")
                        contadorclicks++

                    }
                    if (contadorclicks>0 && ic==false) {
                        Text("Error")
                    }
                }

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





