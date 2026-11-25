# Watchlist

Watchlist is an Android app for keeping track of movies. Users can browse a
catalog, open movie details, mark films as "Want to watch" or "Watched",
save favorites and add their own movies.

Built as a group project for the Android course (Weeks 12–14) with
Kotlin and Jetpack Compose.

## Group members
- Imangali Kairatuly
- Ulas Kali
- Yertay Amangeldi

## Team responsibilities
| Member | Area |
|---|---|
| Imangali Kairatuly | Data and ViewModels: `Movie`, `MovieRepository`, `CatalogViewModel`, `DetailViewModel`, `ListsViewModel`, StateFlow |
| Ulas Kali | UI: screens (catalog, add movie, lists), reusable components (`MovieCard`, `GenreChip`, `RatingBar`), theme |
| Yertay Amankeldi | Navigation and details: `NavHost`, routes, `movieId` argument, details screen, back navigation, README |

## Main features
- **Movie catalog**: list of movies with title, year, genre and rating
- **Movie details**: description, genre and rating; opened by movie ID
- **My lists**: "Want to watch", "Watched" and "Favorites" tabs
- **Favorites**: heart button on every movie card
- **Add movie**: form with title, year, genre and description
- **Genre filter / search** (optional)

## Screens and navigation
| Screen | Route | Description |
|---|---|---|
| Catalog | `catalog` | All movies, favorite button, "Add" button |
| Movie details | `detail/{movieId}` | Full info about one movie |
| Add movie | `add` | Form for adding a new movie |
| My lists | `lists` | Want to watch / Watched / Favorites |

Navigation is implemented with **Navigation Compose**. The `movieId`
argument is passed from the catalog to the details screen, and back
navigation is supported on every screen.

## Architecture
The project follows the **MVVM** pattern:

- **Model** (`data/`): `Movie` data class and `MovieRepository`
- **ViewModel** (`viewmodel/`): holds UI state in `StateFlow` and handles
  UI logic
- **View** (`ui/`): Jetpack Compose screens and reusable components that
  observe the state with `collectAsState()`

```
com.example.watchlist
├── data/
├── viewmodel/
├── ui/
│   ├── screens/
│   ├── components/
│   └── theme/
├── navigation/
└── MainActivity.kt
```

## Tech stack
- Kotlin
- Jetpack Compose and Material Design 3
- MVVM (ViewModel, StateFlow)
- Navigation Compose

## How to run
1. Clone the repository:
   ```
   git clone https://github.com/imankai/watchlist-app.git
   ```
2. Open the project in Android Studio.
3. Wait for Gradle sync to finish.
4. Run the `app` configuration on an emulator or a physical device
   (minimum SDK: API 24).

## Roadmap
- [x] Project setup and repository
- [x] MVVM structure and sample data
- [x] Catalog, details, add and lists screens
- [x] Navigation with movieId argument

## Setup TMDB API Key
To build and run this project locally, you need a free API key from [The Movie Database (TMDB)](https://www.themoviedb.org/):
1. Create an account on TMDB and generate an API Key (v3 auth) in **Settings → API**.
2. Open or create the `local.properties` file in the root directory of the project.
3. Add your key to `local.properties` (this file is ignored by Git and should not be committed):
   ```properties
   TMDB_API_KEY=your_api_key_here