## Developer Notes

- This project follow Clean Architecture with three layers (Domain, Posts, App)
- The repository pattern is used as the communication method between the domain and feature modules.
- Dagger2 is used for Dependency Injection across all modules.
- Kotlin Coroutines are used for multithreading
- AAC Room is used for caching.
- Paging3 is used for pagination.
- Kotlinx Serialization is used for decoding the Json file.
- AAC ViewModels, Fragments and Navigation Components are used in the UI layer.

All the above is an overkill for the purposes of such a simple task, however, in any sort of real project they are essential to ensure maintainability and scalability.


## Haraj Challenge
Welcome to Haraj challenge!

In this repository you will find a sample app ready for implementation. 

You will find icons and a json file containing the data you'll need to complete this challenge.


## Your Mission

Create a functional app with two screens to display information about Posts from Haraj website.

First, a screen showing a list of Posts from the json file you'll find in `assets` folder.

<img src="image1.png" height="450" alt="First Screen">

Second, a screen that displays the information of a particular Post upon click events on the previous screen's list.

<img src="image2.png" height="450" alt="Second Screen">

The share button should open up Android's native share sheet to share the title of the Post with other apps.

## Evaluation

Your work will be evaluated upon the following:
- Following common design patterns (preferably MVVM).
- Clean and testable code.
- Lightweight UI (avoid nested views)

## Submission
Fork this repository and share us the link to your fork after pushing the changes.
