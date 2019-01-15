# About the app

The app fetches feed of aestroids a.k.a Near Earth Objects, from Nasa's neo api and shows them in a list
(https://api.nasa.gov/api.html#NeoWS). The app facilitates fetching more feed as user scrolls through the list (infinite scrolling).


## The app architecture

The app is based on MVVM architecture. The reason behind choosing MVVM is its native support, see https://developer.android.com/topic/libraries/architecture/viewmodel
Following is the description of application structure.

#### The View layer (com.le.aestroider.feature)
The View layer comprises of UI (Activity/Fragments) and the viewmodels. The view layer is structured
per feature names, such as feature/home , feature/neodetails

#### The Model a.k.a Data layer (com.le.aestroider.data)
The data layer handles network operations, see com.le.aestroider.data package. The data layer implements repository pattern, it exposes
a single point of communication i.e. a repository class for the viewmodel(s), while encapsulates the implementation details inside the layer.

#### Domain objects (com.le.aestroider.domain)
The domain package contains data carrying objects, POJOs, to carry data between different layers of the app and holds no dependency on any layer's internal
objects.

#### Dependency Injection (com.le.aestroider.di)
Contains dependency injection

## A Consideration
Infinite scrolling is implemented in a simplistic fashion. It would be be nice to implement it using paging library (https://developer.android.com/topic/libraries/architecture/paging/)

## Libraries used by the app

##### Dagger2
Used for dependency Injection

##### Retrofit2
Used for communicating and fetching data from the network

##### Retrofit2 to Rx2 Adapter (com.squareup.retrofit2:adapter-rxjava2:x.x.x)
Used for receiving Retrofit2's serialized response objects wrapped in Rx2 Observable

##### Lifecycle dependency
Used for ViewModel and LiveData

##### Junit & Mockito
Used for mocking & unit testing

## Screenshots

![Alt text](docs/neo_screen.png?raw=true "Neo Feed")

![Alt text](docs/neo_details.png?raw=true "Neo Details")

![Alt text](docs/share.png?raw=true "Share")

![Alt text](docs/error.png?raw=true "Error")





