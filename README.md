# RTSP_Camera_player_java_Gstreamer
This app is an RTSP camera player using Java and Gstreamer. For running this app you need to install Gstreamer on your machine first.


# How to use
## Install Gstreamer

You can download Gstreamer for your machine with this link:
https://gstreamer.freedesktop.org/download/#windows

## Check installation
Check whether Gstreamer is installed on your machine with this command:
 ```
 gst-launch-1.0 --version
 ```

## Test Gstreamer
If GStreamer is installed properly, test it with this command. No video is required for this command:
 ```
 gst-launch-1.0 videotestsrc ! autovideosink   
 ```
You should see a black window.

## Test a RTSP link in Gstreamer
You can see an RTSP sream in with Gstreamer using this command:

a sample link:"rtsp://716f898c7b71.entrypoint.cloud.wowza.com:1935/app-8F9K44lJ/304679fe_stream2"

 ```
 gst-launch-1.0 rtspsrc location=rtsp://716f898c7b71.entrypoint.cloud.wowza.com:1935/app-8F9K44lJ/304679fe_stream2 latency=200 ! decodebin ! autovideosink
 ```
You will see a water dam in stream.

![image](Doc/GstreamerInCMD.png)

# How to run the project
## Prerequirments:
- JDK 21
- Gstreamer core 
## Install Maven
Install Maven and run the project. It’s normal if the app doesn’t work at this step

## Set JVM options
Now you should set JVM options. I developed this project with Spring Tool Suite (STS).In STS, you should set JVM argument like this::

 ```
 Right click on project > Run as > Run configurations > Java Application 
 > Click on RtspSwingPlayerApplication > Click on Arguments tab 
 > Set VM arguments like this : -Djava.library.path="[your path]\gstreamer\1.0\msvc_x86_64\bin"
 > Click Apply > Click Run
 ```

![image](Doc/run.png)

Now app should show you water dam in stream.

![image](Doc/java_Gstreamer.png)


Good luck!

Author: Mahdieh Eftekharian
I used this GitHub link to develop this project: https://github.com/gstreamer-java
