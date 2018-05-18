# Battery-RAM-Junk-Cleaner-App
App name is BRJ cleaner which stands for Battery, RAM and Junk Cleaner. The app optimizes phone’s battery, cleans junk (cache) files and kills apps running in background. It also has a App Manager which is used to uninstall user installed apps and Device Information which is used to display device internal information.  

## Functionalities
### Junk Clean
This module checks if the installed apps folder has a dedicated cache folder in it. If the cache folder is present, then the specified app gets listed and the user is given the option to select the apps for which it wants to delete the cache.

### RAM Clean
This module makes the list of all the applications that are running and checks add then to list only if they are not System Applications. The user then has the option to select which running applications it wants to stop.

### Battery Optimizer
This module checks if the Bluetooth, Wifi and Mobile Data is On and the Brightness is greater than 35% as these settings increase the rate at which the battery is being consumed. In this case the app list the factors affecting the battery consumption and the user is given the option to change these factors. This module is also responsible for displaying the information of when the device was connected for charging, disconnected from charger and the Battery percentage before connecting and after disconnecting. 

### App Manager
This module makes a list of all the applications that are installed on the device and are not System Apps. An option is given to the user to uninstall any of the listed apps individually.

### Device Information
This module gets the device information and displays then in a list.
