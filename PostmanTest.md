#get all meals
Method: GET
http://localhost:28087/topjava/rest/meals

#get meal by id
Method: GET
http://localhost:28087/topjava/rest/meals/100009

#delete meal by id
Method: DELETE
http://localhost:28087/topjava/rest/meals/100009

#save meal
Method:Post
http://localhost:28087/topjava/rest/meals
Body: (raw JSON)
{
"dateTime": "2020-01-30T10:12:00",
"description": "Обновленный обед",
"calories": 600
}

#get meal between date and time
Method: GET
http://localhost:28087/topjava/rest/meals/between
Params:
startDate: 2020-01-30
endDate: 2020-01-30
startTime: 11-00

