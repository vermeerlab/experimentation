@openapi-file=karate-01-openapi/src/test/java/examples/ee10_01_openapi/openapi.yml
Feature: ユーザー情報を更新します
	

Background:
* url baseUrl

@operationId=putUser
Scenario Outline: Test putUser for <status> status code

	* def args = read(<testDataFile>)
	* def result = call read('putUser.feature@operation') args
	* match result.responseStatus == <status>
		Examples:
		| status | testDataFile |
		| 204    | 'test-data/putUser_default.yml' |


@operationId=putUser
Scenario: explore putUser inline
	You can use this test to quickly explore your API.
	You can then copy this payload and paste it with Ctrl+Shift+V as an Example row above.
* def payload =
"""
{
  "statusCode": null,
  "headers": {},
  "params": {
    "id": 0
  },
  "body": {
    "gender": "OTHER",
    "name": "User Name"
  },
  "matchResponse": true
}
"""
* call read('putUser.feature@operation') payload


@ignore
@operation @operationId=putUser @openapi-file=karate-01-openapi/src/test/java/examples/ee10_01_openapi/openapi.yml
Scenario: operation UserControllerApi/putUser
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
Given path '/api/users/', args.params.id
And headers headers
And request args.body
When method PUT
# validate status code
* if (args.statusCode && responseStatus != args.statusCode) karate.fail(`status code was: ${responseStatus}, expected: ${args.statusCode}`)
# validate response body
* if (args.matchResponse === true) karate.call('putUser.feature@validate')

@ignore @validate
Scenario: validates putUser response

* def responseMatch =
"""
"##string"
"""
* match  response contains responseMatch

