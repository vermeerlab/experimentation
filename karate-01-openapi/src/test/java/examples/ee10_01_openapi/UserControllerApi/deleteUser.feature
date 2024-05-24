@openapi-file=karate-01-openapi/src/test/java/examples/ee10_01_openapi/openapi.yml
Feature: ユーザー情報を削除します
	

Background:
* url baseUrl

@operationId=deleteUser
Scenario Outline: Test deleteUser for <status> status code

	* def params = __row
	* def result = call read('deleteUser.feature@operation') { statusCode: #(+params.status), params: #(params), matchResponse: #(params.matchResponse) }
	* match result.responseStatus == <status>
		Examples:
		| status | id | matchResponse |
		| 204    | 0 | true          |


@operationId=deleteUser
Scenario: explore deleteUser inline
	You can use this test to quickly explore your API.
	You can then copy this payload and paste it with Ctrl+Shift+V as an Example row above.
* def statusCode = 204
* def params = {"id":0}
* def matchResponse = true
* call read('deleteUser.feature@operation') 


@ignore
@operation @operationId=deleteUser @openapi-file=karate-01-openapi/src/test/java/examples/ee10_01_openapi/openapi.yml
Scenario: operation UserControllerApi/deleteUser
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
When method DELETE
# validate status code
* if (args.statusCode && responseStatus != args.statusCode) karate.fail(`status code was: ${responseStatus}, expected: ${args.statusCode}`)
