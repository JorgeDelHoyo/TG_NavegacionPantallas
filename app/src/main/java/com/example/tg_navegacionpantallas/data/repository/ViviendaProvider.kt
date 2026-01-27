import android.content.Context
import com.example.tg_navegacionpantallas.data.model.Vivienda
import kotlinx.serialization.json.Json

object ViviendaProvider {
    var listaViviendas: List<Vivienda> = emptyList()

    // Configuración para que no falle por errores tontos de formato
    private val jsonConfig = Json {
        ignoreUnknownKeys = true
        coerceInputValues = true
    }

    fun cargarDesdeJson(context: Context) {
        try {
            // Leemos el archivo
            val jsonString = context.assets.open("viviendas.json")
                .bufferedReader()
                .use { it.readText() }

            // Convertimos a lista de objetos
            listaViviendas = jsonConfig.decodeFromString<List<Vivienda>>(jsonString)

            // Log para que veas en la consola si ha funcionado (Logcat)
            println("DEBUG: Se han cargado ${listaViviendas.size} viviendas")

        } catch (e: Exception) {
            // Si hay un error, lo veremos en el Logcat de Android Studio
            e.printStackTrace()
            listaViviendas = emptyList()
        }
    }
}