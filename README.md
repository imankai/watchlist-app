# Watchlist

Watchlist is an Android app for keeping track of movies. Users can browse a
catalog of popular movies loaded from the TMDB API, open movie details, mark
films as "Watched", save favorites and add their own movies. Saved data and
settings stay on the device after the app is closed.

Built as a group project for the Android course (Weeks 12–14) with
Kotlin and Jetpack Compose.

## Group members
- Imangali Kairatuly
- Ulas Kali
- Yertay Amangeldi

## Team responsibilities
| Member | Area |
|---|---|
| Imangali Kairatuly | Data layer and ViewModels: `Movie`, `StateFlow`, Room database (`SavedMovieEntity`, `MovieDao`, `AppDatabase`, `LocalMovieRepository`), DataStore (`SettingsRepository`, `SettingsViewModel`), `CatalogViewModel`, `DetailViewModel`, `ListsViewModel`, `AddMovieViewModel` |
| Ulas Kali | UI: screens (catalog, add movie, lists, settings), reusable components (`MovieCard`, `GenreChip`, `RatingBar`, loading/error/empty views), poster loading with Coil |
| Yertay Amangeldi | Navigation and networking: `NavHost`, routes, `movieId` argument, details screen, Retrofit, TMDB API client, DTO models, `RemoteMovieRepository`, error handling, README |

## Main features
- **Movie catalog** loaded from the TMDB API with posters, title, year, genre and rating
- **Loading, error and empty states** with a Retry button when a request fails
- **Movie details**: description, genre, rating and poster; opened by movie ID
- **My lists**: "Want to watch", "Watched" and "Favorites" tabs
- **Favorites** and **Watched** marks saved in a local Room database
- **Add movie**: form with title, year, genre and description, saved locally
- **Delete** a saved movie from the details screen
- **Dark theme** preference saved with DataStore

## Screens and navigation
| Screen | Route | Description |
|---|---|---|
| Catalog | `catalog` | Popular movies from TMDB, favorite button, "Add" and "My Lists" buttons |
| Movie details | `detail/{movieId}` | Full info about one movie |
| Add movie | `add` | Form for adding a new movie |
| My lists | `lists` | Want to watch / Watched / Favorites |
| Settings | `settings` | Dark theme switch |

Navigation is implemented with **Navigation Compose**. The `movieId`
argument is passed from the catalog to the details screen, and back
navigation is supported on every screen.

## Architecture
The project follows the **MVVM** pattern:

- **Model** (`data/`): `Movie`, remote data (Retrofit, TMDB) and local data
  (Room, DataStore) behind repositories
- **ViewModel** (`viewmodel/`): holds UI state in `StateFlow` and handles
  UI logic
- **View** (`ui/`): Jetpack Compose screens and reusable components that
  observe the state with `collectAsState()`

Data flow: Retrofit / Room → Repository → ViewModel (`StateFlow`) → Compose screen.

```
com.example.watchlist
├── data/
│   ├── local/      Room (entity, DAO, database), DataStore settings
│   └── remote/     Retrofit API, DTO models, remote repository
├── viewmodel/
├── ui/
│   ├── screens/
│   ├── components/
│   └── theme/
├── navigation/
├── WatchlistApp.kt
└── MainActivity.kt
```

## Tech stack
- Kotlin
- Jetpack Compose and Material Design 3
- MVVM (ViewModel, StateFlow)
- Navigation Compose
- Retrofit, OkHttp and Gson for the TMDB REST API
- Kotlin Coroutines
- Coil for loading posters
- Room (with KSP) for local storage
- DataStore Preferences for user settings

## How to run
1. Clone the repository:
   ```
   git clone https://github.com/imankai/watchlist-app.git
   ```
2. Set up your TMDB API key (see below).
3. Open the project in Android Studio.
4. Wait for Gradle sync to finish.
5. Run the `app` configuration on an emulator or a physical device
   (minimum SDK: API 24).

## Setup TMDB API Key
To build and run this project locally, you need a free API key from
[The Movie Database (TMDB)](https://www.themoviedb.org/):
1. Create an account on TMDB and generate an API Key (v3 auth) in **Settings → API**.
2. Open or create the `local.properties` file in the root directory of the project.
3. Add your key to `local.properties` (this file is ignored by Git and should not be committed):
   ```properties
   TMDB_API_KEY=your_api_key_here
   ```
4. Sync the project with Gradle and run the app.

## Roadmap
- [x] Project setup and repository
- [x] MVVM structure and sample data
- [x] Catalog, details, add and lists screens
- [x] Navigation with movieId argument
- [x] TMDB API with Retrofit, loading / error / empty states and Retry
- [x] Movie posters with Coil
- [x] Room database with CRUD operations
- [x] DataStore preference (dark theme)

## Attribution
This product uses the TMDB API but is not endorsed or certified by TMDB.