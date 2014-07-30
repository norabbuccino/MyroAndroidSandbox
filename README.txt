Welcome to the Sandbox Android Application! 
Date: Summer 2014

---------<PREFACE>---------
1.INTRO
2.CREDITS
3.DEVELOPMENT ENVIRONMENT
4.BASE CODE NEEDED
5.HIERARCHY EXPLAINED
---------</PREFACE>---------


---------<INTRO>---------
	This application was designed with the intent to bring about open source knowledge of integrating Android with MyroJava Scribblers. 
Much of the base code, primarily the connection via Bluetooth and decoding the byte array commands between the Scribbler and Android 
(Base classes marked in comments at the top of each respective page), are accredited to this github repository :

	https://github.com/sahhhm/Scribdroid
---------</INTRO>---------

	
	
---------<CREDITS>--------- 	
 	In the summer of 2014, under the direction of professor Douglas Harms (DePauw University), Alexander Miller (DePauw University, 
class of 2016) and Leonora Bresette-Buccino (Grinnell College, class 2016) extracted much of the base code from the above repository 
and created a custom application which offers more insight to the capabilities between MyroScribblers and Android.

	All of the work provided in the summer of 2014 is thanks to a generous grant from the National Science Foundation


	If you have any questions, please feel free to contact us at:
	Contact:
		Douglas Harms: 				dharms@depauw.edu
		Leonora Bresette-Buccino:	bresette@grinnell.edu
		Alexander Miller:           alexandermiller_2016@depauw.edu , alexander.miller110@gmail.com
		    
---------</CREDITS>---------


---------<DEVELOPMENT ENVIRONMENT>---------
	This application was developed on both Windows and Mac communicating through an on-campus repository.
	
	Windows:
		HP Pavilion dv7-6b32us
		Windows 7 64-bit Home Premium
		Eclipse: ADT Bundle from Google, name=Eclipse Platform, id=org.eclipse.platform, version=4.3.0
		Repository application: RapidSVN-0.12.1
		JDK: jdk1.7.0_51
		
	Mac:
		MacBook Pro: Late 2011
		Mac OSX Version 10.9.2
		Eclipse: Eclipse IDE for Android Developers	version=23.0.2.1259578	
		Repository application: Versions, Cornerstone
		JDK: jdk1.7.0_51
---------</DEVELOPMENT ENVIRONMENT>---------


---------<BASE NEEDED CODE>---------
	If you are interested in utilizing any of our code, there are a few key components you might need to transfer before you can start
building your project.
	
	---JAVA---
	For the base of your program, we recommend using all of the classes within the dpeauw.edu.myro.android package:
	
	GetCommands.java
	ReadWrite.java
	Scribbler.java
	SetCommands.java
	
	And within the depauw.edu.myro.android.sandbox package:
	
	Sandbox.java
	MainActivity.java
	DeviceListActivity.java
	
	---XML---
	You can use just about any layout you want. Our main just uses a ListView.
	
	---MANIFEST---
	<manifest xmlns:android="http://schemas.android.com/apk/res/android"
   		xmlns:tools="http://schemas.android.com/tools"
    	package="depauw.edu.myro.android.sandbox"
    	android:versionCode="1"
    	android:versionName="1.0" >

    <uses-sdk
       	android:minSdkVersion="11"
       	android:targetSdkVersion="19" />
    	
    <uses-permission android:name="android.permission.BLUETOOTH_ADMIN" />
    <uses-permission android:name="android.permission.BLUETOOTH" />

    <application
       	android:name=".Sandbox"
       	android:allowBackup="true"
       	android:icon="@drawable/ic_launcher"
       	android:label="@string/app_name"
       	android:theme="@style/AppTheme" >

        	
	<!-- Main -->
    <activity
        android:name="depauw.edu.myro.android.sandbox.MainActivity"
        android:configChanges="orientation"
        android:label="@string/app_name"
        android:screenOrientation="portrait" >
         <intent-filter>
          <action android:name="android.intent.action.MAIN" />

           <category android:name="android.intent.category.LAUNCHER" />
          </intent-filter>
      </activity>
     </application>
    </manifest>	
---------</BASE NEEDED CODE>---------



---------<HIERARCHY EXPLAINED>---------
	When considering the expansive possibilities of this project, Alexander Miller and Leonora Bresette-Buccino considered the current
package style to be the most intuitive. 

	depauw.edu.myro.android
		Within this package, this is all the original code from the project found at the repository in the INTRO section. The overwhelming
		majority of this code deals with the connection protocols required to instantiate commands between Android and a Scribbler. For 
		example, causing the Scribbler to move on its own or to take pictures requires higher level commands to be translate into byte arrays
		so that the firmware of the Scribbler can understand.
	
	depauw.edu.myro.android.hierarchy
		Within this package, this is a lot of the code which does nothing more than help the user navigate across pages. We placed all of these 
		pages within here because they do not perform any "substantially unique" code such as taking pictures. They are merely middle-man pages
		to help the user navigate.
		
	depauw.edu.myro.android.sandbox
		Within this package, this contains a lot of the "substantially unique" code which commands the Scribbler to move, create graphics, and 
		much more.
		
	depauw.edu.myro.original
		This code is mainly meant to hold the MyroUtils.java file from the original MyroJava project at DePauw University.
---------</HIERARCHY EXPLAINED>---------
