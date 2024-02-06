# api-spotify-wrapper

REST application wrapping few Spotify's developers API

## Installation

To run the project you will need to setup application properties

Create *application.properties* file in *src/main/resources/*

add this code inside the file and replace the placeholder by your spotify application's value

```
env.clientid = YourClientId
env.clientsecret = YouClientSecret
```

## Routes

This application is covering the following spotify's routes:

### 🌐 /search ###

**spotify-wrapper route's name**

```
/search
```

**query params:**

q: search value</br>
type: [artists, tracks, albums]

### 🌐 /recommendations ###

**spotify-wrapper route's name**

```
/recommendations
```

**query params:**

seedType: [seed_artists, seed_genres, seed_tracks]</br>
genres: selected genres (max 5) 

### 🌐 /albums/{id} ###

**spotify-wrapper route's name**

```
/albums/{id}
```

**url params:**

id: album's id 

### 🌐 /artists/{id} ###

**spotify-wrapper route's name**

```
/artists/{id}
```

**url params:**

id: artist's id

### 🌐 /artists/{id}/albums ###

**spotify-wrapper route's name**

```
/artists/{id}/albums
```

**url params:**

id: artist's id

### 🌐 /artists/{id}/top-tracks ###

**spotify-wrapper route's name**

```
/artists/{id}/top-tracks
```

**query params:**

id: artist's id
