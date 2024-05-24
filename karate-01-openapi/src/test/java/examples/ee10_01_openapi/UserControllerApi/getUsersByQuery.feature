@openapi-file=karate-01-openapi/src/test/java/examples/ee10_01_openapi/openapi.yml
Feature: ユーザー情報を検索します
	クエリーで指定した条件を絞り込み条件として使用します。&lt;br&gt;条件を指定しない場合は全レコードが取得対象となります

Background:
* url baseUrl

@operationId=getUsersByQuery
Scenario Outline: Test getUsersByQuery for <status> status code

	* def params = __row
	* def result = call read('getUsersByQuery.feature@operation') { statusCode: #(+params.status), params: #(params), matchResponse: #(params.matchResponse) }
	* match result.responseStatus == <status>
		Examples:
		| status | gender | name | matchResponse |
		| 200    | MALE | fill some value | true          |


@operationId=getUsersByQuery
Scenario: explore getUsersByQuery inline
	You can use this test to quickly explore your API.
	You can then copy this payload and paste it with Ctrl+Shift+V as an Example row above.
* def statusCode = 200
* def params = {"gender":"FEMALE","name":""}
* def matchResponse = true
* call read('getUsersByQuery.feature@operation') 


@ignore
@operation @operationId=getUsersByQuery @openapi-file=karate-01-openapi/src/test/java/examples/ee10_01_openapi/openapi.yml
Scenario: operation UserControllerApi/getUsersByQuery
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
And param gender = args.params.gender
# And param name = args.params.name
And headers headers
When method GET
# validate status code
* if (args.statusCode && responseStatus != args.statusCode) karate.fail(`status code was: ${responseStatus}, expected: ${args.statusCode}`)
# validate response body
* if (args.matchResponse === true) karate.call('getUsersByQuery.feature@validate')

@ignore @validate
Scenario: validates getUsersByQuery response

* def responseMatch =
"""
{
  "ok": true,
  "body": [
    {
      "gender": "FEMALE",
      "name": "Name:name-2",
      "id": "2"
    }
  ]
}
"""
* match  response contains responseMatch

