# NoteItSimple

Overview:

This is a simple Note App built using MVVM (Model-View-ViewModel) architecture, Jetpack Compose for UI, and Room Database for local storage. The app allows users to create, edit, delete, and view notes efficiently.

## Features

📝 Add new notes

🖊️ Edit existing notes

🗑️ Delete notes

🔍 Search for notes

📂 Store notes locally using Room Database

🎨 Modern UI built with Jetpack Compose

🔄 State management using ViewModel

## Tech Stack

Kotlin - Programming language

Jetpack Compose - UI Toolkit

Room Database - Local data storage

MVVM Architecture - Ensures separation of concerns

Coroutines & Flow - For asynchronous data handling

Hilt - Dependency Injection

## Architecture

The app follows the MVVM (Model-View-ViewModel) architecture, which ensures a clear separation between UI and business logic:
(Jetpack Compose) ↔ ViewModel ↔ Use Case ↔ Repository ↔ Room Database
Presentation ↔ Domain ↔ Data