# 📱 Personal Event Planner App

## 📌 Overview
This project was developed as part of SIT305 Task 4.1.

The Personal Event Planner is an Android application that allows users to create, view, update, and delete events using a simple interface.

---

## 🎯 Features

- Add new events
- View all upcoming events
- Update existing events
- Delete events
- Prevent past date selection
- Input validation for required fields
- User feedback using Toast messages

---

## 🛠️ Technologies Used

- Java
- Android Studio
- Room Database
- RecyclerView
- Fragments
- Jetpack Navigation
- Material UI Components

---

## 📱 App Structure

- MainActivity → Navigation controller
- AddEvent → Add new events
- EventList → Display events
- EventAdapter → RecyclerView adapter
- Event → Data model
- EventDao → Database operations
- AppDatabase → Room database

---

## 🧠 How It Works

1. User enters event details
2. Data is validated
3. Event is stored in Room database
4. RecyclerView displays events
5. User can edit or delete events

---

## 📅 Date Format

Supported formats:
- yyyy-MM-dd HH:mm

---

## 🚀 How to Run

1. Open project in Android Studio
2. Sync Gradle
3. Run on emulator or device

---

## 👨‍🎓 Author

- Name: Patrick Acha
- Unit: SIT305
- Task: 4.1

---

## 📌 Notes

- Room database used for local storage
- allowMainThreadQueries used for simplicity
- Designed using beginner-friendly structure
