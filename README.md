# TigerCard

# Connecting to H2
http://localhost:8080/h2-console

#To service
http://localhost:8080/secureJourney 
#Input Data
{
    "userId" : 1,
    "fromZone" : 1,
    "toZone" : 2
}

#Output Data:
#Current commutation Fare is updated.
{
    "userId": 1,
    "fromZone": 1,
    "toZone": 2,
    "fare": 30
}
