import Friendly.composeApp.BuildConfig
import android.app.Application
import android.content.Context
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.friendly.IPlacesClientProvider
import com.friendly.fetchAddress
import com.friendly.getLastLocation
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.net.FindAutocompletePredictionsRequest
import com.google.android.libraries.places.api.net.PlacesClient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class SearchViewModel() : ViewModel(), KoinComponent {

    val placesClientProvider: IPlacesClientProvider by inject()

    val coordinates = mutableStateOf(LatLng(0.0, 0.0))

    // Query wpisywane przez użytkownika
    private val _query = MutableStateFlow("")
    val query: StateFlow<String> = _query

    // Lista sugestii autouzupełniania
    private val _suggestions = MutableStateFlow<List<String>>(emptyList())
    val suggestions: StateFlow<List<String>> = _suggestions

    // Obsługa zmiany zapytania
    fun onQueryChanged(newQuery: String, context: Context) {
        _query.value = newQuery
        fetchSuggestions(newQuery, context)
    }

    // Pobieranie sugestii na podstawie zapytania
    private fun fetchSuggestions(query: String, context: Context) {
        if (query.isBlank()) {
            _suggestions.value = emptyList()
            return
        }

        // Wykorzystanie Google Places API do autouzupełniania
        viewModelScope.launch {
            val suggestions = fetchPlacesFromGoogle(query, context)
            _suggestions.value = suggestions
        }
    }

    // Funkcja do pobierania miejsc z Google Places API
    private suspend fun fetchPlacesFromGoogle(query: String, context: Context): List<String> {
        // Inicjalizacja Places API

        val placesClient = placesClientProvider.getPlacesClient(context)

        getLastLocation(context, onPermissionDenied = { }) { newCoordinates ->
            coordinates.value = newCoordinates
        }

        // Restriction: Obszar wokół określonego punktu
        val center = coordinates.value // Domyślna lokalizacja: San Francisco
        val circularBounds =
            com.google.android.libraries.places.api.model.RectangularBounds.newInstance(
                LatLng(center.latitude - 0.1, center.longitude - 0.1), // SouthWest
                LatLng(center.latitude + 0.1, center.longitude + 0.1)  // NorthEast
            )

        // Utwórz żądanie autocomplete
        val request = FindAutocompletePredictionsRequest.builder()
            .setQuery(query)
            .setLocationRestriction(circularBounds)
            .build()

        return try {
            // Wykonaj żądanie
            val response = placesClient.findAutocompletePredictions(request).await()

            // Pobierz listę predykcji i zwróć nazwy miejsc jako String
            response.autocompletePredictions.map { prediction ->
                prediction.getFullText(null).toString() // Konwersja SpannableString na String
            }
        } catch (e: Exception) {
            e.printStackTrace()
            emptyList() // W przypadku błędu zwróć pustą listę
        }
    }
}
