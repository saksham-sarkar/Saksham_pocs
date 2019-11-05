Task is to update the literals present in original JSON file with the values present in the custom json file provided by client.

Server bl4ul29d

Example:
File: finbranchResource_INFENG.json
File location: /10219WMS/O10219/APPLN/FE/PROFILE/OWM10219PROFILE/installedApps/OWM10219Cell/MobyBankerClient.ear/MobyBankerClient.war

Say, it may contain some existing literals as given below:
 
{
"FLT047802":"Delta Upload Type",
"FLT047801":"Branch Information - Part 3",
"FLT051027":"Last Realization Amt.",
"FLT000001":"A/c. Opening Criteria",
"FLT000002":"Ref. No."
}

Now customer give a custom file to update or add any new literal in the original file, where key will remain the same but value may differ.
finbranchResource_PORTUGESE.json
File location: /10219WMS/O10219/APPLN/FE/PROFILE/OWM10219PROFILE/installedApps/OWM10219Cell/MobyBankerClient.ear/MobyBankerClient.war/

{
"FLT047802":"New Upload Type",
"FLT051027":"Last Realization Amt.",
"New_literal": "New Value"
}

In the file above as we can see that there are two keys which are existing in the original file, but one literal value is 
different(Original has "FLT047802":"Delta Upload Type",     Custom has "FLT047802":"New Upload Type").
And also there is a new custom literal added which is not there in original("New_literal": "New Value")


Hence these needs to be updated in the original file, and the requirement was to update the original file in sorted order by keys.

Hence for this have writen a script file that does the the required operation and uudates the original file.

For testing purpose I am not affecting the original json file, despite created a new original_INFENG.json file just to compare if we have not missed any literal
frpm the actual json file.

So finally third file original_INFENG.json will be updated as :

{
"FLT000001":"A/c. Opening Criteria",
"FLT000002":"Ref. No.",
"FLT047801":"Branch Information - Part 3",
"FLT047802":"New Upload Type",
"FLT051027":"Last Realization Amt.",
"New_literal": "New Value"
}

Script is also present in the above location as jsonUpdate.py

Added the python script command in the file restartWAS.sh present in /WAS/wasadm as follows:

/10219WMS/O10219/APPLN/FE/PROFILE/OWM10219PROFILE/bin/stopServer.sh OWM10219Server
/10219WMS/O10219/APPLN/FE/PROFILE/OWM10219PROFILE/bin/startServer.sh OWM10219Server
python /10219WMS/O10219/APPLN/FE/PROFILE/OWM10219PROFILE/installedApps/OWM10219Cell/MobyBankerClient.ear/MobyBankerClient.war/jsonUpdate.py

Run to execute.
./restartWAS.sh 






