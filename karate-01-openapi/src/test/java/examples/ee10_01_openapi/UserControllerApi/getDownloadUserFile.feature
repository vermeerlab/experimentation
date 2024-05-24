@openapi-file=karate-01-openapi/src/test/java/examples/ee10_01_openapi/openapi.yml
Feature: ユーザー情報のファイルをダウンロードします
	

Background:
* url baseUrl

@operationId=getDownloadUserFile
Scenario Outline: Test getDownloadUserFile for <status> status code

	* def params = __row
	* def result = call read('getDownloadUserFile.feature@operation') { statusCode: #(+params.status), params: #(params), matchResponse: #(params.matchResponse) }
	* match result.responseStatus == <status>
		Examples:
		| status | id | matchResponse |
		| 200    | 0 | true          |


@operationId=getDownloadUserFile
Scenario: explore getDownloadUserFile inline
	You can use this test to quickly explore your API.
	You can then copy this payload and paste it with Ctrl+Shift+V as an Example row above.
* def statusCode = 200
* def params = {"id":0}
* def matchResponse = true
* call read('getDownloadUserFile.feature@operation') 


@ignore
@operation @operationId=getDownloadUserFile @openapi-file=karate-01-openapi/src/test/java/examples/ee10_01_openapi/openapi.yml
Scenario: operation UserControllerApi/getDownloadUserFile
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
Given path '/api/users/', args.params.id, '/file'
And headers headers
When method GET
# validate status code
* if (args.statusCode && responseStatus != args.statusCode) karate.fail(`status code was: ${responseStatus}, expected: ${args.statusCode}`)
