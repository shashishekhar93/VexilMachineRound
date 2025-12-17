# Vexil Machine Round - Android Application

This repository contains the source code for the Vexil Machine Round Android application, a native app designed to display details related to a loan application process.

## Overview

The application is a single-screen app that fetches data from a local JSON file and presents it to the user in a well-structured and user-friendly interface. The main screen uses a `ViewPager2` to display different facets of the loan application across several fragments, including applicant details, documents, co-applicants, guarantors, and an audit trail timeline.

## Features

- **Tabbed Interface**: Easily navigate between different sections of the loan application using a `TabLayout` and `ViewPager2`.
- **Dynamic Data Loading**: Fetches and displays complex nested data from a local JSON source.
- **Loan Details**: View comprehensive details about the loan, vehicle, and member.
- **Document Viewer**: Displays a list of documents with image loading capabilities.
- **Detailed Lists**: Shows lists for co-applicants, guarantors, EMIs, reference contacts, and inspection details.
- **Audit Trail**: Visualizes the history of the application in a vertical timeline format.

## Architecture

The app is built using modern Android architecture principles and follows the **Model-View-ViewModel (MVVM)** pattern.

- **UI Layer (`presentation`, `fragments`, `adapter`)**: Built with Fragments, Views, and `RecyclerView` adapters. The UI observes state changes from the ViewModel.
- **ViewModel (`viewmodel`)**: `JsonViewModel` holds the business logic, preparing data for the UI and handling user interactions. It exposes data via `StateFlow`.
- **Data Layer (`repository`, `model`)**: The `JsonRepository` is responsible for fetching the data from the local JSON asset and providing it to the ViewModel. The `model` package contains the data classes (POJOs) that represent the JSON structure.
- **Dependency Injection (`di`)**: **Hilt** is used to manage dependencies throughout the app, promoting a modular and testable architecture.

## Tech Stack & Key Libraries

- **Language**: [Kotlin](https://kotlinlang.org/) (100%)
- **Asynchronous Programming**: [Kotlin Coroutines](https://kotlinlang.org/docs/coroutines-guide.html) and [Flow](https://kotlinlang.org/docs/flow.html) for managing background threads and data streams.
- **Architecture**: MVVM, Repository Pattern
- **Dependency Injection**: [Hilt](https://dagger.dev/hilt/)
- **UI**: 
    - [Android View System](https://developer.android.com/guide/topics/ui)
    - [Material Components for Android](https://material.io/develop/android)
    - [ViewBinding](https://developer.android.com/topic/libraries/view-binding)
    - [ViewPager2 & RecyclerView](https://developer.android.com/guide/topics/ui/layout/viewpager2)
- **Image Loading**: [Glide](https://github.com/bumptech/glide)
- **JSON Parsing**: [Gson](https://github.com/google/gson)

## Code Quality Highlights & Suggestions

This codebase demonstrates a strong understanding of modern Android development practices.

### What's Done Well:

- **Clear Architecture**: The separation of concerns (UI, ViewModel, Repository) is well-defined.
- **Modern State Management**: The use of `StateFlow` in the `JsonViewModel` combined with the `ApiResult` sealed class provides a robust and reactive way to manage UI state.
- **Effective Dependency Injection**: Hilt is correctly implemented for providing the `JsonRepository` and managing the `ViewModel`'s lifecycle.
- **Robust Permission Handling**: `PermissionManager` is a well-designed, reusable utility for handling runtime permissions.

## Videos:


## Images:
<img width="1080" height="2400" alt="Screenshot_20251215_120245" src="https://github.com/user-attachments/assets/6212be72-0f6c-47f2-b5b7-4019942a1f98" />
<img width="1080" height="2400" alt="Screenshot_20251215_120154" src="https://github.com/user-attachments/assets/615d3bcd-4918-4f00-93a6-44ae335dc2cc" />


1.  Clone the repository.
2.  Open the project in Android Studio.
3.  Let Gradle sync the dependencies.
4.  Build and run the app on an emulator or a physical device.

