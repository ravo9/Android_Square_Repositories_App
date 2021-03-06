# Android_Square_Repositories_App

A demo app presenting architecture based on MVVM, Repository and Interactor patterns - including Dependency Injection implementation.

1. Architecture of the app has been based on the MVVM design pattern.

2. Each view consists of Activity/ Fragment (View layer) and its appropriate ViewModel.

3. Model layer (data) has been organized using Repository design pattern. It's the only gate for data access from viewmodels' perspective.

4. Repository uses Interactors to communicate with API (Network Interactor) and with internal database (DataBase Interactor).

5. Communication with the Back-End has been constructed using Retrofit library (to connect with the external API), and RxJava (to provide correct data flow).

6. Internal database (caching) has been based on Room library.

7. To make the app maintainable and testable I have decided to use Dagger2 library, providing one-way direction of injecting dependencies. In this situation we can be sure that Activity contains ViewModel, but ViewModel doesn't know about Activity. Similarly ViewModel contains Repository, Repository contains Interactors. All of these dependencies are injected dynamically.

8. Code of the app has been organized into 3 main directories - Injection (dependency injection files), Data (model layer files), and Features (having each view and its related files stored as separate sub-direction).

9. Testing part of the app consists of Unit and UI Testing.
Unit Tests has been written for each architectural layer (viewmodels, repository, interactors) using JUnit and Mockito libraries.
UI Tests are created using Espresso framework.


Please do not hesitate to ask in case of any further questions. I will be happy to clarify each uncertainty.

I hope you like my app.

                                                                                                    Rafal Ozog
