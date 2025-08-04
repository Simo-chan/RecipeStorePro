# Recipe Store Pro ([AppGallery link](https://appgallery.huawei.com/app/C112123737))
-is a an android app, where "Recipe Store" stands for storing recipes and "Pro" for sounding cool and professional. This app I have created for the restaraunt I used to work at, because I felt annoyed by a need to go trough a stack of paper inside a greasy folder to get a recipe 
from a few months ago (at the restaraunt the menu changed weekly, but of course we had to get back to some old items from time to time).

Aside from the android part, I also decided to dive into the backend developement a little to understand what happens "behind the scenes" as they say, so I have created the backend to for storing recipe data and user authentication. The backend's code can be found 
from my [github page](https://github.com/Simo-chan/RecipeStoreProAPI).

## Overview

At first you will be met by the Onboarding screen from which you can either go to the Sign Up screen or Login screen. To create an account you just need to provide a name, an email and a password. There are simple password requirements: it should be at least 6 digits long and 
the two passwords should match. Your data will be encrypted by my backend's logic and stored inside a PostgreSQL database deployed on Heroku and nobody can have access to it, even me, it means your Krabby Patty formula is safe :hugs:. 

<img width="320" height="675" alt="Onboarding screen" src="https://github.com/user-attachments/assets/d4d4ff84-55da-4557-ab17-9e76e073a0cb" />
<img width="320" height="675" alt="Signing up" src="https://github.com/user-attachments/assets/b5f8a830-dcd0-478e-9a94-6501bf4be460" />


Then on the Home screen you will find all of your recipes. To create a new one press the plus button on the bottom bar and click "Create new recipe". Next just write a title, list of ingredients and instructions, after that click on the tick button.

<img width="320" height="675" alt="Home page" src="https://github.com/user-attachments/assets/4a24a4ce-abfe-43e4-aa61-926fa5361727" />
<img width="320" height="675" alt="Create new recipe" src="https://github.com/user-attachments/assets/7d906095-6d7d-4820-83a9-50933e91d4d7" />
<img width="320" height="675" alt="Creating a recipe" src="https://github.com/user-attachments/assets/2aaafdf3-62d7-4058-8e4f-ce63887c6895" />
<img width="320" height="675" alt="Recipe saved" src="https://github.com/user-attachments/assets/c89c19c7-948e-4460-a87d-348774fe9325" />


Thus you will eventually have a long list like this one

<img width="320" height="675" alt="Long list of recipes" src="https://github.com/user-attachments/assets/335ba1d8-d54d-43a8-beae-90b0623f4528" />


You can also mark you recipes as favorite by just pressing the star button on the Details screen. If you want to see only the favorite recipes, choose from the drop down menu on the Home page.

<img width="320" height="675" alt="Choosing favorites" src="https://github.com/user-attachments/assets/a4543420-3d92-408b-8c53-8e10db4cac82" />
<img width="320" height="675" alt="Showing only favorites" src="https://github.com/user-attachments/assets/a4fa3030-ca50-4472-a096-bbb44a92c705" />


To delete a recipe simply swipe the according list item left. To restore the recipe press the "UNDO" button on the snackbar.

<img width="320" height="675" alt="Deleting recipe" src="https://github.com/user-attachments/assets/f5515954-8005-4007-9d27-2bd256c05fb9" />
<img width="320" height="675" alt="Recipe deleted" src="https://github.com/user-attachments/assets/8e1653f8-6ab2-4c7d-8191-333771688461" />


As the list grows you might need the desired recipe quickly, to do that use the search bar.

<img width="320" height="675" alt="Searching" src="https://github.com/user-attachments/assets/fabf4b0a-1d10-451a-a46f-d0bfc2146cb6" />


Sometimes there may be no Internet connection, if it's the case your newly created recipes will be stored locally and the red indicator will tell you that the specific resipe is not uploaded to the cloud database yet. When the Internet connection is restored, swipe down to refresh 
the page and all of your "offline" recipes should be uploaded to the cloud data base. After that their indicator will turn green. Same goes for the deleted recipes.

<img width="320" height="675" alt="Offline recipe" src="https://github.com/user-attachments/assets/bd91d4a6-df9e-4280-8cab-8470e30188d9" />
<img width="320" height="675" alt="Refreshing" src="https://github.com/user-attachments/assets/83e9c19d-fcee-4898-9463-9f6ecff73b2d" />
<img width="320" height="675" alt="Refreshed" src="https://github.com/user-attachments/assets/38981fcc-c2fa-4fc3-896a-6febd500b2e0" />

On the Settings screen you can see you name and email that you logged in with. Here you can also change your avatar, your user preferences will be saved in Data Store. Additionally it is possible to send a feedback directly to me. Just press the feedback button and from there you can 
contact me via Telegram or Email. Intents will take you straight to the apps. 

<img width="320" height="675" alt="Settings page" src="https://github.com/user-attachments/assets/f305b45b-9c67-4a78-9cee-510dcb7b073d" />
<img width="320" height="675" alt="Changed avatar" src="https://github.com/user-attachments/assets/d7d11f05-1acb-400f-a83a-dd90f8e56149" />
<img width="320" height="675" alt="Feedback" src="https://github.com/user-attachments/assets/936292f9-204e-4fc5-98ec-510de491071c" />


On the same screen you can logout, this will take you to the Onboarding screen. If you log in again with the same or different device you will find all of your previously saved recipes in place of course.

<img width="320" height="675" alt="Logging out" src="https://github.com/user-attachments/assets/7b0901a7-3317-443d-85c2-85b31cce5c3d" />


Aand that's about it.

**Full list of libraries/technologies used in this project:**

*Android app:* 
- Kotlin
- Coroutines
- Coroutines Flow
- Retrofit
- OkHttp
- Navigation Component
- Room
- MVVM
- Dagger-Hilt
- Single Activity Architechture
- Fragments
- ViewBinding

 *Backend:*
- Kotlin
- Ktor
- Exposed Library
- PostgreSQL
- Heroku

