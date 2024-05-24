@openapi-file=karate-01-openapi/src/test/java/examples/ee10_01_openapi/openapi.yml
Feature: ユーザー情報を登録します
	

Background:
* url baseUrl

@operationId=postUser
Scenario Outline: Test postUser for <status> status code

	* def args = read(<testDataFile>)
	* def result = call read('postUser.feature@operation') args
	* match result.responseStatus == <status>
		Examples:
		| status | testDataFile |
		| 201    | 'test-data/postUser_201.yml' |


@operationId=postUser
Scenario: explore postUser inline
	You can use this test to quickly explore your API.
	You can then copy this payload and paste it with Ctrl+Shift+V as an Example row above.
* def payload =
"""
{
  "statusCode": 201,
  "headers": {},
  "params": {},
  "body": {
    "gender": "OTHER",
    "name": "User Name"
  },
  "matchResponse": true
}
"""
* call read('postUser.feature@operation') payload


@ignore
@operation @operationId=postUser @openapi-file=karate-01-openapi/src/test/java/examples/ee10_01_openapi/openapi.yml
Scenario: operation UserControllerApi/postUser
* def args = 
"""
{
    auth: #(karate.get('auth')), 
    headers: #(karate.get('headers')), 
    params: #(karate.get('params')), 
    body: #(karate.get('body')), 
    statusCode: #(karate.get('statusCode')), 
    matchResponse: #(karate.get('matchResponse'))
}
"""
* def authHeader = call read('classpath:karate-auth.js') args.auth
* def headers = karate.merge(args.headers || {}, authHeader || {})
Given path '/api/users'
And headers headers
And request args.body
When method POST
# validate status code
* if (args.statusCode && responseStatus != args.statusCode) karate.fail(`status code was: ${responseStatus}, expected: ${args.statusCode}`)
# validate response body
* if (args.matchResponse === true) karate.call('postUser.feature@validate')

@ignore @validate
Scenario: validates postUser response

* def responseMatch =
"""
{
  "body": {
    "id": "##string"
  },
  "ok": "##boolean"
}
"""
* match  response contains responseMatch

