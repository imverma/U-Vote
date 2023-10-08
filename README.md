# U-Vote #

## Introduction ##

In recent times there have been news of tampering of Voting machines, muscle power being used at the polling booths and a very low voting turnouts are one of the major drawbacks of our current voting system. Also the current voting system in India requires a lot of manpower and expenditure for its deployment. This has motivated us to develop this system. We also aim to aid private elections by developing a platform for voting for all types of elections.

### Features ###
* Application provides voter registration/login before starting voting process.
* It verifies voters details from the cloud database.
* It uses face detection for verifying identity through Aadhaar database.
* It generates Metric reports of current/live votes.
* It notifies the voters about Room Id and time slot if allocated.
* It stores encrypted voting details of user and sends it to cloud in encrypted form.
* User can see their profile in the profile menu.

### System Requirement ###

* Microsoft® Windows® 7/8/10 (or) Mac® OS X® 10.8.5 or higher
* 2 GB RAM minimum, 8 GB RAM recommended
* 2 GB of available disk space minimum,
* 4 GB Recommended (500 MB for IDE + 1.5 GB for Android SDK and emulator system image)
* Java Development Kit (JDK) 8
* Android Studio 2.0

### API Used : ###

* *Aadhaar API Service* - For Aadhaar database, which is used for Face verification.
* *FireBase Cloud Database* - for saving encrypted profile and voting information.
* *Firebase MLKit* - for face detection and recognition service.

### Run the project in Android Studio ###

* Import the project in Android Studio.
* Make sure internet is available, Android build will download artifacts from internet.
* Sync the project and enable the Developer option in mobile and attach a mobile , make sure you have to install USB drivers on your system.
* Run the project.
* If the mobile is not available then create a new Android virtual device from AVD manager and start the Virtual device.
