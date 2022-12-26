# Nemad Application
An application for registering complaints and giving suggestions with ratings for different private and public organizations in the city of Yazd.

## About The Project
I was responsible for the implementation of an Android application for the website nemad.ostanyazd.ir. Nemad is a smart management system for the city of Yazd that allows people to send their complaints, suggestions, experience and rating about managers and services in different organizations. 
This application is developed as a native Android application with Android Studio as IDE, Kotlin programming language with mysql as database and Swagger Api.

![Nemad](https://user-images.githubusercontent.com/72992585/208296191-487df2fb-b466-4c03-b0cd-25e364050c44.png)

## Features 

- User registration with phone number verification. (OTP Verification)
- Forgot Password with phone number verification.
- User login via username/phone number and password 
- Splash Screen 
- Smart search for different categories and organization names that support nested queries.
- Two repositories are used for fetching dynamic data
  - Repository for user-related data
  - Repository for requests
- Bottom dialog for giving ratings and suggestions

## Tech stack & Open-source libraries
This project is based on MVVM architecture, using the following tech stacks:
- Jetpack
  - Lifecycle
  - View Binding
  - Navigation Component
  - Hilt 
  - Retrofit 
- Glide
- Coroutines
