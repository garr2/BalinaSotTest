# Test app to create a register activity
A test application based on clean architecture for creating registration activity with a shared design. It also performs user registration 
on the specified API and returns the registration result. Used librares you can see [here](https://github.com/garr2/BalinaSotTest/new/master?readme=1#used-liblrares)
, how to download this project you can see [here](https://github.com/garr2/BalinaSotTest/new/master?readme=1#download).

# Domain module
Independent layer, it contains only business logic written on kotlin - no dependencies from Android SDK, other modules or libs
, except RxJava. Here we have contracts(interfaces) that should be implemented in other modules.

# Data module
It contains functions for registering a user on the specified API. This module implements the interfaces located in the domain module 
and does not contain business logic. For working out it uses such libraries as: RxJava, Retrofit, OkHttp.

# Presentation module
This module contains the logic to configure the application user interface and all business logic. This module uses the MVVM 
pattern for organizing work with the user interface.

# Used liblrares
[RxJava](https://github.com/amitshekhariitbhu/RxJava2-Android-Samples)
[Retrofit](https://github.com/square/retrofit)
[OkHttp](https://github.com/square/okhttp)

# Download
To download this project click "Cone or download" button, then click "downloadZip" or copy the project link. If you have
installed GitHub client you may click "Open in Desctop".
